package com.noadmin.mongodb.model;

import org.springframework.stereotype.Component;

import com.noadmin.model.connection.ConnectionBuilder;

/**
 * Builder of MongoDB connection.
 *
 * @author mkhelif
 */
@Component(MongoDBConnection.TYPE)
public final class MongoDBConnectionBuilder implements ConnectionBuilder<MongoDBConnection> {

	/**
	 * @see com.noadmin.model.connection.ConnectionBuilder#newConnection()
	 */
	@Override
	public MongoDBConnection newConnection() {
		return new MongoDBConnection();
	}
}