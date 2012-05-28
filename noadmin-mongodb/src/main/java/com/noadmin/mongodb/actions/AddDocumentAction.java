package com.noadmin.mongodb.actions;

import java.awt.event.ActionEvent;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.mongodb.model.DBObject;

/**
 * Add a new document to the {@link DBCollection}.
 *
 * @author mkhelif
 */
public final class AddDocumentAction extends AbstractMongoDBAction {

	private static final AddDocumentAction INSTANCE = new AddDocumentAction();
	public static AddDocumentAction getInstance() { return INSTANCE; }

	/**
	 * The {@link Collection} to remove the {@link DBObject} from.
	 */
	private Collection collection;

	private AddDocumentAction() {
		super("mongodb.document.add", "document;add");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		collection.insert(new DBObject(collection, null, new BasicDBObject()));
	}

	public void setCollection(final Collection collection) {
		this.collection = collection;
		this.update();
	}

	private void update() {
		this.setEnabled(collection != null);
	}
}