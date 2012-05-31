package com.noadmin.model.connection;

/**
 * Listener for connection state.
 *
 * @author mkhelif
 */
public interface ConnectionListener {

	/**
	 * The connection has failed to connect to the database server.
	 * @param e the received exception.
	 */
	void connectionFailed(final Exception e);

	/**
	 * The connection is trying to establish.
	 */
	void connectionEstablishing();

	/**
	 * The connection has been established.
	 */
	void connectionEstablished();

	/**
	 * The connection is closing.
	 */
	void connectionClosing();

	/**
	 * The connection has been closed.
	 */
	void connectionClosed();
}