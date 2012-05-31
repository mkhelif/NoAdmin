package com.noadmin.mongodb.model;

/**
 * Listener for {@link Collection} events.
 *
 * @author mkhelif
 */
public interface CollectionListener {

	/**
	 * The {@link Collection} has been updated.
	 */
	void collectionUpdated();

	/**
	 * A new document has been added to the {@link Collection}.
	 */
	void documentAdded(final DBObject object);

	/**
	 * The document has been removed.
	 */
	void documentRemoved(final DBObject object);
}