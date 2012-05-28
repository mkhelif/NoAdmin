package com.noadmin.model.connection;

import org.apache.log4j.Logger;

import com.noadmin.controller.LoggingManager;
import com.noadmin.model.ConfigurationElement;

/**
 * Connection to a database.
 *
 * @author mkhelif
 */
public interface Connection extends ConfigurationElement {

	/**
	 * Logger for connection.
	 */
	static final Logger LOG = Logger.getLogger(LoggingManager.CONNECTION);

	/**
	 * Establish the connection to the database.
	 */
	void connect();

	/**
	 * Close opened connection.
	 */
	void disconnect();

	/**
	 * @return TRUE if the connection is established with the server,
	 *         FALSE otherwise.
	 */
	boolean isConnected();

	/**
	 * @return the host name of this connection.
	 */
	String getHostname();

	/**
	 * Update the host name of the connection.
	 * @param hostname the new host name of the connection.
	 */
	void setHostname(final String hostname);

	/**
	 * @return the port of this connection.
	 */
	int getPort();

	/**
	 * Update the port of the connection.
	 * @param port the new port of the connection.
	 */
	void setPort(final int port);

	/**
	 * Add a listener for connection state.
	 * @param listener the new listener.
	 */
	void addConnectionListener(final ConnectionListener listener);

	/**
	 * Remove a connection listener.
	 * @param listener the listener to remove.
	 */
	void removeConnectionListener(final ConnectionListener listener);
}