package com.noadmin.mongodb.content.collection;

import javax.swing.JPopupMenu;

import com.noadmin.mongodb.actions.AddDocumentAction;
import com.noadmin.mongodb.actions.AddFieldAction;
import com.noadmin.mongodb.actions.RefreshCollectionAction;
import com.noadmin.mongodb.actions.RemoveDocumentAction;
import com.noadmin.mongodb.actions.RemoveFieldAction;
import com.noadmin.view.beans.tree.CollapseTreeAction;
import com.noadmin.view.beans.tree.ExpandTreeAction;

/**
 * Popup menu displayed in the {@link CollectionPanel} table.
 *
 * @author mkhelif
 */
public final class CollectionTreePopupMenu extends JPopupMenu {

	public CollectionTreePopupMenu(final CollectionTree tree) {
		this.add(RefreshCollectionAction.getInstance());
		this.addSeparator();
		this.add(ExpandTreeAction.getInstance(tree));
		this.add(CollapseTreeAction.getInstance(tree));
		this.addSeparator();
		this.add(AddDocumentAction.getInstance());
		this.add(RemoveDocumentAction.getInstance());
		this.addSeparator();
		this.add(AddFieldAction.getInstance());
		this.add(RemoveFieldAction.getInstance());
	}
}