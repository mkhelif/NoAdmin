package com.noadmin.mongodb.model;

import com.mongodb.DBRef;

/**
 * Wrapper of a {@link DBRef} used to notifies the GUI when updated.
 *
 * @author mkhelif
 */
public final class DBReference implements MongoDBElement {

	/**
	 * The source collection.
	 */
	private final Collection collection;

	/**
	 * The DB reference.
	 */
	private final DBRef reference;

	public DBReference(final Collection collection, final com.mongodb.DBRef reference) {
		this.collection = collection;
		this.reference = reference;
	}

	public Collection getCollection() {
		return collection;
	}

	/**
	 * @return the DB reference.
	 */
	public DBRef getReference() {
		return reference;
	}

	/**
	 * @return the reference target {@link Collection}.
	 */
	public String getTargetCollection() {
		return reference.getRef();
	}

	/**
	 * @return the reference target object ID.
	 */
	public String getTargetObject() {
		return String.valueOf(reference.getId());
	}
}