package com.noadmin.mongodb.content.collection;

import java.util.List;

import javax.swing.tree.TreePath;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBRef;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.mongodb.model.DBObjectList;
import com.noadmin.mongodb.model.DBReference;
import com.noadmin.mongodb.model.MongoDBElement;
import com.noadmin.mongodb.util.ValueConverter;
import com.noadmin.util.CollectionUtil;
import com.noadmin.view.beans.DefaultMessageSource;

/**
 * Tree model for a {@link Collection}.
 *
 * @author mkhelif
 */
public final class CollectionTreeModel extends DefaultTreeTableModel {

	/**
	 * The internationalization messages manager.
	 */
	private final DefaultMessageSource i18n;

	/**
	 * The displayed {@link Collection}.
	 */
	private Collection collection;

	/**
	 * The listener of the {@link Collection} and documents events.
	 */
	private CollectionTreeListener listener;

	public CollectionTreeModel() {
		super();
		i18n = new DefaultMessageSource("mongodb");
	}

	/**
	 * @see org.jdesktop.swingx.treetable.TreeTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 3;
	}

	/**
	 * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(final int column) {
		switch (column) {
			case 0: return MongoDBElement.class;
			case 1: return Object.class;
			case 2: return Class.class;
		}
		return null;
	}

	/**
	 * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int column) {
		switch (column) {
			case 0: return i18n.getMessage("mongodb.document.name");
			case 1: return i18n.getMessage("mongodb.document.value");
			case 2: return i18n.getMessage("mongodb.document.type");
		}
		return null;
	}

	/**
	 * @see org.jdesktop.swingx.treetable.TreeTableModel#getValueAt(java.lang.Object, int)
	 */
	@Override
	public Object getValueAt(final Object object, final int column) {
		final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) object;
		final Object value = this.getValue(node);
		switch(column) {
			case 0: return node.getUserObject();
			case 1: return value;
			case 2:
				if (value == null) {
					return null;
				}
				return value.getClass();
		}
		return null;
	}

	/**
	 * Retrieve the value of the object.
	 * @param node the node to get value for.
	 * @return the value of the node;
	 */
	private Object getValue(final DefaultMutableTreeTableNode node) {
		if (node.getUserObject() instanceof String) {
			final DefaultMutableTreeTableNode parent = (DefaultMutableTreeTableNode) node.getParent();
			return ((DBObject) parent.getUserObject()).get((String) node.getUserObject());
		}
		return node.getUserObject();
	}

	/**
	 * @see org.jdesktop.swingx.treetable.DefaultTreeTableModel#setValueAt(java.lang.Object, java.lang.Object, int)
	 */
	@Override
	public void setValueAt(final Object object, final Object edited, final int column) {
		final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) edited;
		final DefaultMutableTreeTableNode parent = (DefaultMutableTreeTableNode) node.getParent();
		final DBObject document = (DBObject) parent.getUserObject();
		final String key = (String) node.getUserObject();
		final Object value = document.get(key);

		switch (column) {
			case 0:
				// Remove old field and add new field
				if (!key.equals(object)) {
					document.removeField(key);
					document.addField((String) object, value);
				}
				break;

			case 1:
				// Update field value
				document.put(key, object);
				break;

			case 2:
				// Convert field type
				final Class<?> type = (Class<?>) object;
				document.put(key, ValueConverter.convertTo(key, value, type));
				break;
		}
	}

	/**
	 * Retrieve the {@link TreePath} to the given object.
	 * @param object the model object to get path to.
	 * @return the matching tree path.
	 */
	public TreePath getPathTo(final Object object) {
		return getPathTo(new TreePath(getRoot()), object);
	}

	/**
	 * Retrieve the {@link TreePath} to the given object.
	 * @param path the parent path to starts from.
	 * @param object the model object to get path to.
	 * @return the matching tree path.
	 */
	private TreePath getPathTo(final TreePath path, final Object object) {
		final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) path.getLastPathComponent();
		for (int i = 0 ; i < node.getChildCount() ; i++) {
			final DefaultMutableTreeTableNode child = (DefaultMutableTreeTableNode) node.getChildAt(i);
			final TreePath objectPath = getPathTo(path.pathByAddingChild(child), object);
			if (objectPath != null) {
				return objectPath;
			}
		}
		if (node.getUserObject() == object) {
			return path;
		}
		return null;
	}

	/**
	 * @see org.jdesktop.swingx.treetable.DefaultTreeTableModel#getChild(java.lang.Object, int)
	 */
	@Override
	public Object getChild(final Object parent, final int index) {
		return ((DefaultMutableTreeTableNode) parent).getChildAt(index);
	}

	/**
	 * @see org.jdesktop.swingx.treetable.DefaultTreeTableModel#getChildCount(java.lang.Object)
	 */
	@Override
	public int getChildCount(final Object parent) {
		return ((DefaultMutableTreeTableNode) parent).getChildCount();
	}

	/**
	 * @see org.jdesktop.swingx.treetable.DefaultTreeTableModel#isCellEditable(java.lang.Object, int)
	 */
	@Override
	public boolean isCellEditable(final Object nodeObject, final int column) {
		final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) nodeObject;
		final Object value = node.getUserObject();

		if (value instanceof DBObject) {
			if (((DBObject) value).getRoot() == value) {
				return false;
			}
			if (column == 1) {
				return false;
			}
		}
		return !"_id".equals(value);
	}

	/**
	 * Update the displayed collection.
	 * @param collection the collection to show in the tree.
	 */
	public void setCollection(final Collection collection) {
		if (this.collection != null) {
			this.collection.removeCollectionListener(listener);
		}
		this.collection = collection;
		if (this.collection != null) {
			this.collection.addCollectionListener(listener);
		}

		final DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode();

		if (this.collection != null) {
			final DBCursor cursor = collection.find();
			while (cursor.hasNext()) {
				root.add(createNode(new DBObject(collection, null, cursor.next())));
			}
		}
		this.setRoot(root);
	}

	/**
	 * Create a tree node from a {@link DBObject}.
	 * @param object the {@link DBObject} to represent.
	 * @return the tree node.
	 */
	DefaultMutableTreeTableNode createNode(final DBObject object) {
		object.addObjectListener(listener);

		final DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode(object);

		final List<String> fields = CollectionUtil.asSortedList(object.keySet());
		for (final String key : fields) {
			node.add(createNode(object, key, object.get(key)));
		}
		return node;
	}

	/**
	 * Create the node for the DB object list.
	 * @param list the list to create a node for.
	 * @return the node for the list including all its children.
	 */
	private MutableTreeTableNode createNode(final DBObjectList list) {
		final DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode(list);
		for (final Object object : list) {
			node.add(createNode(null, null, object));
		}
		return node;
	}

	/**
	 * Create a node for the DB reference.
	 * @param reference the reference to create a node for.
	 * @return the node for the reference.
	 */
	private MutableTreeTableNode createNode(final DBReference reference) {
		return new DefaultMutableTreeTableNode(reference);
	}

	private MutableTreeTableNode createNode(final DBObject parent, final String key, final Object object) {
		if (object instanceof BasicDBList) {
			return createNode(new DBObjectList(collection, (BasicDBList) object, key));
		} else if (object instanceof DBRef) {
			return createNode(new DBReference(collection, (DBRef) object));
		} else if (object instanceof DBObject || object instanceof BasicDBObject) {
			return createNode(new DBObject(collection, parent, (com.mongodb.DBObject) object, key));
		} else {
			return new DefaultMutableTreeTableNode(key);
		}
	}

	/**
	 * @see org.jdesktop.swingx.tree.TreeModelSupport#fireChildAdded(TreePath, int, Object)
	 */
    public void fireChildAdded(final TreePath parentPath, final int index, final Object child) {
        modelSupport.fireChildAdded(parentPath, index, child);
    }

    /**
	 * @see org.jdesktop.swingx.tree.TreeModelSupport#fireChildRemoved(TreePath, int, Object)
     */
    public void fireChildRemoved(final TreePath parentPath, final int index, final Object child) {
    	modelSupport.fireChildRemoved(parentPath, index, child);
    }

    /**
	 * @see org.jdesktop.swingx.tree.TreeModelSupport#fireChildChanged(TreePath, int, Object)
     */
    public void fireChildChanged(final TreePath parentPath, final int index, final Object child) {
    	modelSupport.fireChildChanged(parentPath, index, child);
    }

    /**
	 * @see org.jdesktop.swingx.tree.TreeModelSupport#fireTreeStructureChanged(TreePath, int, Object)
     */
    public void fireTreeStructureChanged(final TreePath subTreePath) {
    	modelSupport.fireTreeStructureChanged(subTreePath);
    }

    public void setListener(final CollectionTreeListener listener) {
		this.listener = listener;
	}
}