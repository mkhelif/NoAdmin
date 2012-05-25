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
	public void collectionUpdated();

	/**
	 * A new document has been added to the {@link Collection}.
	 */
	public void documentAdded(final DBObject object);

	/**
	 * The document has been removed.
	 */
	public void documentRemoved(final DBObject object);
}