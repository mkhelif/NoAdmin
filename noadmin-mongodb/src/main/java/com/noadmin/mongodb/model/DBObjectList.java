package com.noadmin.mongodb.model;

import java.util.Iterator;

import com.mongodb.BasicDBList;

/**
 * Wrapper of a {@link BasicDBList} used to notifies the GUI when updated.
 *
 * @author mkhelif
 */
public final class DBObjectList implements Iterable<Object>, MongoDBElement  {

	private final Collection collection;

	private final com.mongodb.BasicDBList list;

	private final String name;

	public DBObjectList(final Collection collection, final com.mongodb.BasicDBList list, final String name) {
		this.collection = collection;
		this.list = list;
		this.name = name;
	}

	public Collection getCollection() {
		return collection;
	}

	@Override
	public Iterator<Object> iterator() {
		return list.iterator();
	}

	public String getName() {
		return name;
	}
}