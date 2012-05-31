package com.noadmin.model.connection;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import com.noadmin.model.AbstractConfigurationElement;

/**
 * Abstract class for all the connections.
 *
 * @author mkhelif
 */
public abstract class AbstractConnection extends AbstractConfigurationElement implements Connection {

	/**
	 * Listeners for connection state.
	 */
	private final transient List<ConnectionListener> listeners;

	/**
	 * IP address or host name of the database server.
	 */
	private String hostname;

	/**
	 * TCP port of the database server.
	 */
	private int port;

	/**
	 * State of the state of this connection.
	 */
	private boolean connected = false;

	protected AbstractConnection() {
		super();
		listeners = new ArrayList<ConnectionListener>();
	}

	/**
	 * Establish the connection to the database.
	 */
	public final void connect() {
		this.fireConnectionEstablishing();
		try {
			this.doConnect();
			this.connected = true;
			this.fireConnectionEstablished();
		} catch (final Exception e) {
			this.connected = false;
			this.fireConnectionFailed(e);
		}
	}

	/**
	 * Establish the connection to the server.
	 * @throws Exception any exception that can occurs.
	 */
	protected abstract void doConnect() throws Exception;

	/**
	 * Close opened connection.
	 */
	public final void disconnect() {
		this.fireConnectionClosing();
		try {
			this.doDisconnect();
		} catch (final Exception e) {
			LOG.error("Failed to close connection to the database server '" + hostname + "': " + e.getMessage());
		} finally {
			this.connected = false;
			this.fireConnectionClosed();
		}
	}

	/**
	 * Close the connection to the server.
	 * @throws Exception any exception that can occurs.
	 */
	protected abstract void doDisconnect() throws Exception;

	/**
	 * @return the host name of the server.
	 */
	public final String getHostname() {
		return hostname;
	}

	/**
	 * Update the address or host name of the server.
	 * @param hostname the new host name.
	 */
	public final void setHostname(final String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the connection port.
	 */
	public final int getPort() {
		return port;
	}

	/**
	 * Update the connection port.
	 * @param port the new port
	 */
	public final void setPort(final int port) {
		if (this.port != port) {
			this.port = port;
			this.fireElementUpdated();
		}
	}

	/**
	 * @return TRUE if the connection is established with the server,
	 *         FALSE otherwise.
	 */
	public final boolean isConnected() {
		return connected;
	}

	/**
	 * Add a listener for connection state.
	 * @param listener the new listener.
	 */
	public final void addConnectionListener(final ConnectionListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a connection listener.
	 * @param listener the listener to remove.
	 */
	public final void removeConnectionListener(final ConnectionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Fire the "connectionFailed" event to all the listeners.
	 * @param e the exception that issues this event.
	 */
	private void fireConnectionFailed(final Exception e) {
		for (final ConnectionListener listener : listeners) {
			listener.connectionFailed(e);
		}
	}

	/**
	 * Fire the "connectionEstablishing" event to all the listeners.
	 */
	private void fireConnectionEstablishing() {
		for (final ConnectionListener listener : listeners) {
			listener.connectionEstablishing();
		}
	}

	/**
	 * Fire the "connectionEstablished" event to all the listeners.
	 */
	private void fireConnectionEstablished() {
		for (final ConnectionListener listener : listeners) {
			listener.connectionEstablished();
		}
	}

	/**
	 * Fire the "connectionClosing" event to all the listeners.
	 */
	private void fireConnectionClosing() {
		for (final ConnectionListener listener : listeners) {
			listener.connectionClosing();
		}
	}

	/**
	 * Fire the "connectionClosed" event to all the listeners.
	 */
	private void fireConnectionClosed() {
		for (final ConnectionListener listener : listeners) {
			listener.connectionClosed();
		}
	}

	/**
	 * Write the connection as XML in order to save it.
	 * @param document the XML document that will be stored.
	 * @param parent the parent node of the connection.
	 */
	public final void writeXML(final Document document, final Element parent) {
		parent.appendChild(writeAttributes(document.createElement("connection")));
	}

	/**
	 * Write the XML attributes of the connection.
	 * @param element the element to add attributes on.
	 * @return the element.
	 */
	@Override
	protected Element writeAttributes(final Element element) {
		super.writeAttributes(element);
		element.setAttribute("hostname", hostname);
		element.setAttribute("port", Integer.toString(port));
		element.setAttribute("type", this.getConnectionType());
		return element;
	}

	/**
	 * Load the connection settings from the XML attributes.
	 * @param attributes the XML attributes.
	 */
	@Override
	public void loadXML(final Attributes attributes) {
		super.loadXML(attributes);
		this.hostname = attributes.getValue("hostname");
		this.port = Integer.parseInt(attributes.getValue("port"));
	}

	/**
	 * @return the type of connection.
	 */
	protected abstract String getConnectionType();
}