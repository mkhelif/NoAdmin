package com.noadmin.model.connection;

/**
 * Builder of connection.
 *
 * @author mkhelif
 * @param <C> the type of Connection built.
 */
public interface ConnectionBuilder<T extends Connection> {

	/**
	 * @return a new connection instance.
	 */
	T newConnection();
}