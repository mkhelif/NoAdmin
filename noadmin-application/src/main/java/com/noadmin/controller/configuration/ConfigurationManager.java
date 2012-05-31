package com.noadmin.controller.configuration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.noadmin.controller.LoggingManager;
import com.noadmin.controller.Service;
import com.noadmin.model.connection.Connection;

/**
 * Service in charge of dealing with the application configuration.
 *
 * @author mkhelif
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class ConfigurationManager implements Service {

	/**
	 * File that contains the configuration.
	 */
	private static final File CONFIGURATION_FILE = new File("connections.xml");

	/**
	 * URL of the configuration rules (from the JAR file).
	 */
	private static final URL CONFIGURATION_RULES = ConfigurationManager.class.getResource("configuration-rules.xml");

	/**
	 * Configuration logger.
	 */
	private static final Logger LOG = Logger.getLogger(LoggingManager.CONFIGURATION);

	/**
	 * List of configuration listeners.
	 */
	private final List<ConfigurationListener> listeners = new ArrayList<ConfigurationListener>();

	/**
	 * List of defined connections.
	 */
	private List<Connection> connections = new ArrayList<>();

	/**
	 * Initialize the service and starts it.
	 */
	@SuppressWarnings("unchecked")
	public void startService() {
		// Load the configuration :
		if (CONFIGURATION_FILE.exists()) {
			final Digester digester = DigesterLoader.createDigester(CONFIGURATION_RULES);
			try {
				final List<Connection> loadedConnections = (List<Connection>) digester.parse(CONFIGURATION_FILE);
				for (final Connection connection : loadedConnections) {
					this.addConnection(connection);
				}
			} catch (final Exception e) {
				LOG.error("Error while loading configuration file.", e);
				connections = new ArrayList<>();
			}
		}
	}

	/**
	 * Stop the service and free all used resources.
	 */
	public void stopService() {
		// Store configuration
		try {
			final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			// Store all connections
			final Element root = document.createElement("connections");
			for (final Connection connection : connections) {
				connection.writeXML(document, root);
			}
			document.appendChild(root);

			// Store to file
			final Transformer transform = TransformerFactory.newInstance().newTransformer();
			transform.transform(new DOMSource(document), new StreamResult(CONFIGURATION_FILE));
		} catch (final Exception e) {
			LOG.error("Error while storing configuration.", e);
		}

		// Close all the connections
		for (final Connection connection : connections) {
			if (connection.isConnected()) {
				connection.disconnect();
			}
		}
	}

	/**
	 * @return the connections list.
	 */
	public List<Connection> getConnections() {
		return connections;
	}

	/**
	 * Add a new connection to the configuration.
	 * @param connection the new configuration.
	 */
	public void addConnection(final Connection connection) {
		if (connections.add(connection)) {
			this.fireConnectionAdded(connection);
		}
	}

	/**
	 * Remove a connection from the configuration.
	 * @param connection the connection to remove.
	 */
	public void removeConnection(final Connection connection) {
		if (connections.remove(connection)) {
			this.fireConnectionRemoved(connection);
		}
	}

	/**
	 * Add a new configuration listener.
	 * @param listener the new listener.
	 */
	public void addConfigurationListener(final ConfigurationListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a configuration listener.
	 * @param listener the listener to remove.
	 */
	public void removeConfigurationListener(final ConfigurationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * A new connection has been added, notify listeners.
	 * @param connection the added connection.
	 */
	private void fireConnectionAdded(final Connection connection) {
		for (final ConfigurationListener listener : listeners) {
			listener.connectionAdded(connection);
		}
	}

	/**
	 * A connection has been removed, notify listeners.
	 * @param connection the removed connection.
	 */
	private void fireConnectionRemoved(final Connection connection) {
		for (final ConfigurationListener listener : listeners) {
			listener.connectionRemoved(connection);
		}
	}
}