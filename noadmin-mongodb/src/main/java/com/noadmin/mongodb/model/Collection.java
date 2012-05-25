package com.noadmin.mongodb.model;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

/**
 * Wrapper for a {@link DBCollection} in MongoDB.
 *
 * @author mkhelif
 */
public final class Collection implements MongoDBElement {

	private final List<CollectionListener> listeners = new ArrayList<CollectionListener>();

	private final DBCollection collection;

	public Collection(final DBCollection collection) {
		this.collection = collection;
	}

	public String getName() {
		return collection.getName();
	}

	public boolean isSystem() {
		return collection.getName().equals("system.indexes");
	}

	public int count() {
		return (int) collection.count();
	}

	public DBCursor find() {
		return collection.find();
	}

	public void refresh() {
		this.fireCollectionUpdated();
	}

	public void save(final DBObject object) {
		collection.save(object.getObject());
	}

	public void insert(final DBObject object) {
		collection.insert(object.getObject());
		this.fireDocumentAdded(object);
	}

	public void remove(final DBObject object) {
		collection.remove(object.getObject());
		this.fireDocumentRemoved(object);
	}

	public CommandResult getStats() {
		return collection.getStats();
	}

	public void addCollectionListener(final CollectionListener listener) {
		listeners.add(listener);
	}

	public void removeCollectionListener(final CollectionListener listener) {
		listeners.remove(listener);
	}

	private void fireCollectionUpdated() {
		for (final CollectionListener listener : listeners) {
			listener.collectionUpdated();
		}
	}

	private void fireDocumentAdded(final DBObject object) {
		for (final CollectionListener listener : listeners) {
			listener.documentAdded(object);
		}
	}

	private void fireDocumentRemoved(final DBObject object) {
		for (final CollectionListener listener : listeners) {
			listener.documentRemoved(object);
		}
	}
}