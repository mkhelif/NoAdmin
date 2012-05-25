package com.noadmin.mongodb.node;

import org.springframework.stereotype.Component;

import com.noadmin.mongodb.model.MongoDBConnection;
import com.noadmin.view.connections.node.ConnectionNodeBuilder;

/**
 * Builder of {@link ConnectionNode} for a given {@link MongoDBConnection}.
 *
 * @author mkhelif
 */
@Component("builder_com.noadmin.mongodb.model.MongoDBConnection")
public final class MongoDBConnectionNodeBuilder implements ConnectionNodeBuilder<MongoDBConnection, ConnectionNode> {

	/**
	 * @see com.noadmin.view.connections.node.ConnectionNodeBuilder#build(com.noadmin.model.connection.Connection)
	 */
	@Override
	public ConnectionNode build(final MongoDBConnection connection) {
		return new ConnectionNode(connection);
	}
}