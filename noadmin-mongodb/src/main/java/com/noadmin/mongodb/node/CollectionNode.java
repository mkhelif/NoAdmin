package com.noadmin.mongodb.node;

import javax.swing.Icon;
import javax.swing.JPopupMenu;

import com.noadmin.mongodb.actions.RefreshCollectionAction;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.view.connections.node.AbstractNode;

/**
 * Node mapped with a Mongo {@link Collection}.
 *
 * @author mkhelif
 */
public final class CollectionNode extends AbstractNode<Collection> {

	/**
	 * Create a new collection node for the collection.
	 * @param collection the {@link Collection} to display.
	 */
	public CollectionNode(final Collection collection) {
		super(collection);
	}

	/**
	 * Add the following actions to the popup menu:
	 *   - {@link RefreshCollectionAction} to refresh the selected collection.
	 * @see fr.mkhelif.noadmin.view.tree.node.AbstractNode#addMenuItems(javax.swing.JPopupMenu)
	 */
	@Override
	public void addMenuItems(final JPopupMenu menu) {
		super.addMenuItems(menu);
		menu.add(RefreshCollectionAction.getInstance());
	}

	/**
	 * The label of the object to display as node's text.
	 * @return the label of the node.
	 */
	@Override
	public String getLabel() {
		return getUserObject().getName();
	}

	/**
	 * @return the icon to display for this node.
	 */
	@Override
	public Icon getIcon() {
		return loadIcon("collection");
	}
}