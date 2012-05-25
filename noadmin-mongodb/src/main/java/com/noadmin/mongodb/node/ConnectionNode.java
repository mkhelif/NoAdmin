package com.noadmin.mongodb.node;

import com.mongodb.DB;
import com.noadmin.mongodb.model.MongoDBConnection;
import com.noadmin.view.connections.node.AbstractConnectionNode;

/**
 * Node mapped to a {@link MongoDBConnection}.
 *
 * @author mkhelif
 */
public final class ConnectionNode extends AbstractConnectionNode<MongoDBConnection> {

	/**
	 * Create a new connection node for the connection.
	 * @param connection the connection to display.
	 */
	public ConnectionNode(final MongoDBConnection connection) {
		super(connection);
	}

	/**
	 * The connection has been established.
	 * @see fr.mkhelif.noadmin.view.tree.node.AbstractConnectionNode#connectionEstablished()
	 */
	@Override
	public void connectionEstablished() {
		super.connectionEstablished();

		// Display server databases:
		for (final DB db : this.getUserObject().getDatabases()) {
			this.add(new DBNode(db));
		}
		this.expand();
	}
}