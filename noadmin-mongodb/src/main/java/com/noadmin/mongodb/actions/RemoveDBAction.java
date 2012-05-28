package com.noadmin.mongodb.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import com.mongodb.DBCollection;
import com.noadmin.controller.Controller;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.view.beans.GUIHandler;

/**
 * Remove the selected documents from the {@link DBCollection}.
 *
 * @author mkhelif
 */
public final class RemoveDBAction extends AbstractMongoDBAction {

	private static final RemoveDBAction INSTANCE = new RemoveDBAction();
	public static RemoveDBAction getInstance() { return INSTANCE; }

	/**
	 * The {@link Collection} to remove the {@link DBObject} from.
	 */
	private Collection collection;

	/**
	 * The {@link DBObject} document to remove;
	 */
	private List<DBObject> documents;

	private RemoveDBAction() {
		super("mongodb.document.remove", "document;remove");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// Ask confirmation:
		final boolean choice = Controller.getInstance().getBean(GUIHandler.class).confirm(
			i18n("mongodb.document.remove"),
			i18n("mongodb.document.remove.confirm")
		);
		if (!choice) {
			return;
		}

		// Delete the documents:
		for (final DBObject document : documents) {
			collection.remove(document);
		}
	}

	public void setCollection(final Collection collection) {
		this.collection = collection;
		this.update();
	}

	public void setDocuments(final List<DBObject> documents) {
		this.documents = documents;
		this.update();
	}

	private void update() {
		this.setEnabled(collection != null && documents != null && !documents.isEmpty());
	}
}