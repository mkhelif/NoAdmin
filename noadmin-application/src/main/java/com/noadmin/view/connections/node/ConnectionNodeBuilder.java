package com.noadmin.view.connections.node;

import com.noadmin.model.connection.Connection;

/**
 * Builder of Connection node.
 *
 * @author mkhelif
 */
public interface ConnectionNodeBuilder<C extends Connection, N extends AbstractConnectionNode<C>> {

	/**
	 * Build a new node for the connection.
	 * @param connection the connection to build a node for.
	 * @return the new node.
	 */
	N build(final C connection);
}