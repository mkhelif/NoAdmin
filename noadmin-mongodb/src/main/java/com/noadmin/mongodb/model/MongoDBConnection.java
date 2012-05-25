package com.noadmin.mongodb.model;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.noadmin.model.connection.AbstractConnection;

/**
 * Connection for MongoDB database.
 *
 * @author mkhelif
 */
public final class MongoDBConnection extends AbstractConnection {

	/**
	 * The type of connection for Mongo DB server.
	 */
	static final String TYPE = "MongoDB";

	/**
	 * Native connection to the MongoDB server.
	 */
	private Mongo mongo;

	/**
	 * List of databases deployed on the server.
	 */
	private transient List<DB> databases;

	/**
	 * @see fr.mkhelif.noadmin.model.connection.AbstractConnection#doConnect()
	 */
	@Override
	protected void doConnect() throws Exception {
		mongo = new Mongo(getHostname(), getPort());
	}

	/**
	 * @see fr.mkhelif.noadmin.model.connection.AbstractConnection#doDisconnect()
	 */
	@Override
	protected void doDisconnect() throws Exception {
		mongo.close();
		mongo = null;
		databases = null;
	}

	/**
	 * @return the list of databases deployed on the server.
	 */
	public List<DB> getDatabases() {
		if (databases == null) {
			databases = new ArrayList<>();
			for (final String database : mongo.getDatabaseNames()) {
				databases.add(mongo.getDB(database));
			}
		}
		return databases;
	}

	public String getVersion() {
		return mongo.getVersion();
	}

	public int getMaxBsonObjectSize() {
		return mongo.getMaxBsonObjectSize();
	}

	public String getReplicaSetStatus() {
		if (mongo.getReplicaSetStatus() == null) {
			return "";
		}
		return mongo.getReplicaSetStatus().getName();
	}

	public MongoOptions getMongoOptions() {
		return mongo.getMongoOptions();
	}

	/**
	 * @see com.noadmin.model.connection.AbstractConnection#getConnectionType()
	 */
	@Override
	protected String getConnectionType() {
		return TYPE;
	}
}