package com.noadmin.mongodb.model;

/**
 * Listener for {@link DBObject} events.
 *
 * @author mkhelif
 */
public interface DBObjectListener {

	/**
	 * A field of the document has been updated (value changed).
	 * @param object the parent document.
	 * @param field the updated field.
	 */
	public void fieldUpdated(final DBObject object, final String field);

	/**
	 * A new field has been added to the document.
	 * @param object the parent document.
	 * @param field the new field name.
	 */
	public void fieldAdded(final DBObject object, final String field);

	/**
	 * A field has been removed from the document.
	 * @param object the parent document.
	 * @param field the removed field.
	 */
	public void fieldRemoved(final DBObject object, final String field);
}