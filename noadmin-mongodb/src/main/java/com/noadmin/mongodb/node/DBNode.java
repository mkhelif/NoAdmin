package com.noadmin.mongodb.node;

import javax.swing.Icon;
import javax.swing.JPopupMenu;

import com.mongodb.DB;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.view.connections.node.AbstractNode;

/**
 * Node mapped with a Mongo {@link DB}.
 *
 * @author mkhelif
 */
public final class DBNode extends AbstractNode<DB> {

	/**
	 * Create a new connection node for the connection.
	 * @param db the {@link DB} to display.
	 */
	public DBNode(final DB db) {
		super(db);

		// Add Collections nodes:
		for (final String name : db.getCollectionNames()) {
			final Collection collection = new Collection(db.getCollection(name));
			if (!collection.isSystem()) {
				this.add(new CollectionNode(collection));
			}
		}
	}

	/**
	 * Add the following actions:
	 *     - {@link AddCollectionAction}: add a new collection to the {@link DB}.
	 * @see fr.mkhelif.noadmin.view.tree.node.AbstractNode#addMenuItems(javax.swing.JPopupMenu)
	 */
	@Override
	public void addMenuItems(final JPopupMenu menu) {
		//menu.add(AddCollectionAction.getInstance());
		menu.addSeparator();

		super.addMenuItems(menu);
	}

	/**
	 * The label of the object to display as node's text.
	 * @return the label of the node.
	 */
	@Override
	public final String getLabel() {
		return getUserObject().getName();
	}

	/**
	 * @return the icon to display for this node.
	 */
	@Override
	public final Icon getIcon() {
		return manager.getIcon("database");
	}
}