package com.noadmin.mongodb.content.collection;

import com.noadmin.mongodb.model.Collection;
import com.noadmin.mongodb.model.MongoDBElement;
import com.noadmin.view.beans.ListSelectionModel;
import com.noadmin.view.beans.tree.ClassTableEditor;
import com.noadmin.view.beans.tree.JTreeTable;

/**
 * The {@link JTreeTable} that displays collections data.
 *
 * @author mkhelif
 */
public final class CollectionTree extends JTreeTable {

	/**
	 * The specific MongoDB classes available for values.
	 */
	private static final Class<?>[] CLASSES = new Class<?>[] {
		com.mongodb.DBObject.class
	};

	/**
	 * The model of the Collection tree.
	 */
	private final CollectionTreeModel model;

	public CollectionTree() {
		super(new CollectionTreeModel());
		this.model = (CollectionTreeModel) this.getTreeTableModel();
		this.model.setListener(new CollectionTreeListener(this, model));

		this.setTreeCellRenderer(new CollectionTableObjectRenderer());
		this.setDefaultRenderer(Class.class, new CollectionTableClassRenderer());
		this.setSelectionModel(new ListSelectionModel(this));
		this.setComponentPopupMenu(new CollectionTreePopupMenu(this));
		this.setAutoStartEditOnKeyStroke(false);

		// Configure editors
		this.setDefaultEditor(Class.class, new ClassTableEditor(CLASSES));
		this.setDefaultEditor(MongoDBElement.class, new DocumentEditor());

		// Display settings:
		this.setExpandsSelectedPaths(true);
		this.setShowsRootHandles(true);
		this.setShowGrid(false);
		this.setAutoCreateRowSorter(true);
		this.setRootVisible(false);
		this.getTableHeader().setReorderingAllowed(false);

		// Default width
		this.getColumn(0).setMaxWidth(100);
		this.getColumn(1).setMaxWidth(300);
		this.getColumn(2).setMaxWidth(70);
		this.getColumn(0).setPreferredWidth(100);
		this.getColumn(1).setPreferredWidth(300);
		this.getColumn(2).setPreferredWidth(70);
	}

	/**
	 * Update the {@link Collection} displayed in the tree.
	 * @param collection the new {@link Collection} to display.
	 */
	public void setCollection(final Collection collection) {
		model.setCollection(collection);
	}
}