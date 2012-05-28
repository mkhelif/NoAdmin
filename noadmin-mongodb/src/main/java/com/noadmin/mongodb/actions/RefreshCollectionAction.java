package com.noadmin.mongodb.actions;

import java.awt.event.ActionEvent;

import com.noadmin.mongodb.model.Collection;

/**
 * Refresh the currently displayed collection.
 *
 * @author mkhelif
 */
public final class RefreshCollectionAction extends AbstractMongoDBAction {

	private static final RefreshCollectionAction INSTANCE = new RefreshCollectionAction();
	public static RefreshCollectionAction getInstance() { return INSTANCE; }

	/**
	 * The currently selected {@link Collection}.
	 */
	private Collection collection;

	private RefreshCollectionAction() {
		super("mongodb.collection.refresh", "refresh");
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		collection.refresh();
	}

	public void setCollection(final Collection collection) {
		this.collection = collection;
		this.setEnabled(collection != null);
	}
}