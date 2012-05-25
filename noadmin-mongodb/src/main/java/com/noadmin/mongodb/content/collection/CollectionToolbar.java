package com.noadmin.mongodb.content.collection;

import javax.swing.JToolBar;

import com.noadmin.mongodb.actions.AddDocumentAction;
import com.noadmin.mongodb.actions.AddFieldAction;
import com.noadmin.mongodb.actions.RefreshCollectionAction;
import com.noadmin.mongodb.actions.RemoveDocumentAction;
import com.noadmin.mongodb.actions.RemoveFieldAction;

/**
 * Toolbar displayed in the {@link CollectionPanel}.
 *
 * @author mkhelif
 */
public final class CollectionToolbar extends JToolBar {

	public CollectionToolbar() {
		this.add(RefreshCollectionAction.getInstance().newButton());
		this.addSeparator();
		this.add(AddDocumentAction.getInstance().newButton());
		this.add(RemoveDocumentAction.getInstance().newButton());
		this.addSeparator();
		this.add(AddFieldAction.getInstance().newButton());
		this.add(RemoveFieldAction.getInstance().newButton());

		this.setFloatable(false);
	}
}