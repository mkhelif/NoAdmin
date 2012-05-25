package com.noadmin.mongodb.content.collection;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import com.noadmin.mongodb.model.CollectionListener;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.mongodb.model.DBObjectListener;
import com.noadmin.util.CollectionUtil;

/**
 * Listener that handle model events and updates the tree model in consequence.
 *
 * @author mkhelif
 */
final class CollectionTreeListener implements CollectionListener, DBObjectListener {

	/**
	 * The handled tree.
	 */
	private final CollectionTree tree;

	/**
	 * The handled tree model.
	 */
	private final CollectionTreeModel model;

	public CollectionTreeListener(final CollectionTree tree, final CollectionTreeModel model) {
		this.tree = tree;
		this.model = model;
	}

	/**
	 * @see fr.mkhelif.noadmin.plugins.mongodb.model.DBObjectListener#fieldUpdated(fr.mkhelif.noadmin.plugins.mongodb.model.DBObject, java.lang.String)
	 */
	@Override
	public void fieldUpdated(final DBObject object, final String field) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final TreePath parentPath = model.getPathTo(object);
				final TreePath path = model.getPathTo(field);
				final DefaultMutableTreeTableNode parentNode = (DefaultMutableTreeTableNode) parentPath.getLastPathComponent();

				DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) path.getLastPathComponent();
				final int index = parentNode.getIndex(node);

				if (object.get(field) instanceof com.mongodb.DBObject) {
					node = model.createNode(new DBObject(object.getCollection(), object.getRoot(), (com.mongodb.DBObject) object.get(field), field));
					parentNode.remove(index);
					parentNode.insert(node, index);
				}

				model.fireChildChanged(parentPath, index, node);
			}
		});
	}

	/**
	 * @see fr.mkhelif.noadmin.plugins.mongodb.model.DBObjectListener#fieldAdded(fr.mkhelif.noadmin.plugins.mongodb.model.DBObject, java.lang.String)
	 */
	@Override
	public void fieldAdded(final DBObject object, final String field) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final List<String> fields = CollectionUtil.asSortedList(object.keySet());

				final TreePath parentPath = model.getPathTo(object);
				final DefaultMutableTreeTableNode parentNode = (DefaultMutableTreeTableNode) parentPath.getLastPathComponent();
				final DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode(field);
				final int index = fields.indexOf(field);

				parentNode.insert(node, index);
				model.fireChildAdded(parentPath, index, node);
			}
		});
	}

	/**
	 * @see fr.mkhelif.noadmin.plugins.mongodb.model.DBObjectListener#fieldRemoved(fr.mkhelif.noadmin.plugins.mongodb.model.DBObject, java.lang.String)
	 */
	@Override
	public void fieldRemoved(final DBObject object, final String field) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final TreePath parentPath = model.getPathTo(object);
				final TreePath path = model.getPathTo(field);
				final DefaultMutableTreeTableNode parentNode = (DefaultMutableTreeTableNode) parentPath.getLastPathComponent();
				final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) path.getLastPathComponent();
				final int index = parentNode.getIndex(node);

				parentNode.remove(index);
				model.fireChildRemoved(parentPath, index, node);
				tree.clearSelection();
			}
		});
	}

	/**
	 * @see fr.mkhelif.noadmin.plugins.mongodb.model.CollectionListener#collectionUpdated()
	 */
	@Override
	public void collectionUpdated() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.fireTreeStructureChanged(getRootPath());
			}
		});
	}

	/**
	 * @see fr.mkhelif.noadmin.plugins.mongodb.model.CollectionListener#documentAdded(fr.mkhelif.noadmin.plugins.mongodb.model.DBObject)
	 */
	@Override
	public void documentAdded(final DBObject object) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final MutableTreeTableNode child = model.createNode(object);
				final int index = model.getRoot().getChildCount();

				getRoot().add(child);
				model.fireChildAdded(getRootPath(), index, child);
			}
		});
	}

	/**
	 * @see fr.mkhelif.noadmin.plugins.mongodb.model.CollectionListener#documentRemoved(fr.mkhelif.noadmin.plugins.mongodb.model.DBObject)
	 */
	@Override
	public void documentRemoved(final DBObject object) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final TreePath path = model.getPathTo(object);
				final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) path.getLastPathComponent();
				final int index = model.getRoot().getIndex(node);

				getRoot().remove(index);
				model.fireChildRemoved(getRootPath(), index, node);
				tree.clearSelection();
			}
		});
	}

	/**
	 * @return the tree root node as {@link DefaultMutableTreeTableNode}.
	 */
	public DefaultMutableTreeTableNode getRoot() {
		return (DefaultMutableTreeTableNode) model.getRoot();
	}

	/**
	 * @return the path to the root node.
	 */
	public TreePath getRootPath() {
		return new TreePath(getRoot());
	}
}