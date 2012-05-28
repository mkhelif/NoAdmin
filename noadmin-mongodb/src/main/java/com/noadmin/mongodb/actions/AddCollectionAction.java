package com.noadmin.mongodb.actions;

import java.awt.event.ActionEvent;

import com.mongodb.DB;
import com.noadmin.mongodb.model.Collection;

/**
 * Add a new {@link Collection} to the {@link DB}.
 *
 * @author mkhelif
 */
public final class AddCollectionAction extends AbstractMongoDBAction {

	/**
	 * The base name of the new collection.
	 */
	private static final String BASE_NAME = "collection_";

	private static final AddCollectionAction INSTANCE = new AddCollectionAction();
	public static AddCollectionAction getInstance() { return INSTANCE; }

	/**
	 * The {@link DB} that will receive a new {@link Collection}.
	 */
	private DB db;

	private AddCollectionAction() {
		super("mongodb.collection.add", "collection;add");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final AddCollectionDialog dialog = new AddCollectionDialog(db);
		dialog.setDefaultName(this.getCollectionName());
		dialog.setVisible(true);
	}

	private String getCollectionName() {
		int index = 1;
		while (db.collectionExists(BASE_NAME + index)) {
			index++;
		}
		return BASE_NAME + index;
	}


	public void setDB(final DB db) {
		this.db = db;
		this.update();
	}

	private void update() {
		this.setEnabled(db != null);
	}
}