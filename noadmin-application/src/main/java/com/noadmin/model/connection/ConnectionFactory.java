package com.noadmin.model.connection;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory used to build new connection.
 *
 * @author mkhelif
 */
@Component
public final class ConnectionFactory {

	/**
	 * Map between the type of connection and the builder of connections.
	 */
	private final Map<String, ConnectionBuilder<Connection>> builders;

	@Autowired
	public ConnectionFactory(final Map<String, ConnectionBuilder<Connection>> builders) {
		this.builders = builders;
	}

	/**
	 * @return all the available types of {@link Connection} that can be created.
	 */
	public String[] getConnectionTypes() {
		return builders.keySet().toArray(new String[builders.size()]);
	}

	/**
	 * Create a new {@link Connection} of the given type.
	 * @param type the type of connection to create.
	 * @return the new connection instance.
	 */
	public Connection newConnection(final String type) {
		return builders.get(type).newConnection();
	}
}