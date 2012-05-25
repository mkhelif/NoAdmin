package com.noadmin.view.connections.node;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.model.connection.Connection;

/**
 * Factory for connection node.
 *
 * @author mkhelif
 */
@Component
public final class ConnectionNodeFactory {

	/**
	 * The map between the class name of the connection and the node builder.
	 */
	private final Map<String, ConnectionNodeBuilder<Connection, AbstractConnectionNode<Connection>>> builders;

	@Autowired
	public ConnectionNodeFactory(final Map<String, ConnectionNodeBuilder<Connection, AbstractConnectionNode<Connection>>> builders) {
		this.builders = builders;
	}

	/**
	 * Create a new connection node.
	 * @param connection the connection to display.
	 * @return a node to display in the tree.
	 */
	public AbstractConnectionNode<?> newConnectionNode(final Connection connection) {
		return builders.get("builder_" + connection.getClass().getName()).build(connection);
	}
}