package com.noadmin.controller.configuration;

import com.noadmin.model.connection.Connection;

/**
 * Listener for ConfigurationManager events.
 *
 * @author mkhelif
 */
public interface ConfigurationListener {

	/**
	 * A new connection has been added to the configuration.
	 * @param connection the new added connection.
	 */
	void connectionAdded(final Connection connection);

	/**
	 * A connection has been removed from the configuration.
	 * @param connection the removed connection.
	 */
	void connectionRemoved(final Connection connection);
}