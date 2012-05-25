package com.noadmin.mongodb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Wrapper of a {@link DBObject} used to notifies the GUI when updated.
 *
 * @author mkhelif
 */
public final class DBObject implements MongoDBElement {

	private final List<DBObjectListener> listeners = new ArrayList<DBObjectListener>();

	private final Collection collection;

	private final DBObject root;

	private final com.mongodb.DBObject object;

	private final String name;

	public DBObject(final Collection collection, final DBObject root, final com.mongodb.DBObject object) {
		this(collection, root, object, null);
	}

	public DBObject(final Collection collection, final DBObject root, final com.mongodb.DBObject object, final String name) {
		this.collection = collection;
		this.root = (root == null ? this : root);
		this.object = object;
		this.name = name;
	}

	public Collection getCollection() {
		return collection;
	}

	public DBObject getRoot() {
		return root == null ? this : root;
	}

	protected com.mongodb.DBObject getObject() {
		return object;
	}

	public Set<String> keySet() {
		return object.keySet();
	}

	public Object get(final String key) {
		return object.get(key);
	}

	public Object addField(final String field, final Object value) {
		final Object result = object.put(field, value);
		collection.save(root);
		fireFieldAdded(field);

		return result;
	}

	public Object put(final String field, final Object value) {
		final Object result = object.put(field, value);
		collection.save(root);
		fireFieldUpdated(field);

		return result;
	}

	public Object removeField(final String field) {
		final Object result = object.removeField(field);
		collection.save(root);
		fireFieldRemoved(field);

		return result;
	}

	public boolean containsField(final String field) {
		return object.containsField(field);
	}

	public String getName() {
		return name;
	}

	public void addObjectListener(final DBObjectListener listener) {
		listeners.add(listener);
	}

	public void removeObjectListener(final DBObjectListener listener) {
		listeners.add(listener);
	}

	private void fireFieldUpdated(final String field) {
		for (final DBObjectListener listener : listeners) {
			listener.fieldUpdated(this, field);
		}
	}

	private void fireFieldAdded(final String field) {
		for (final DBObjectListener listener : listeners) {
			listener.fieldAdded(this, field);
		}
	}

	private void fireFieldRemoved(final String field) {
		for (final DBObjectListener listener : listeners) {
			listener.fieldRemoved(this, field);
		}
	}

	@Override
	public String toString() {
		return String.valueOf(object);
	}
}