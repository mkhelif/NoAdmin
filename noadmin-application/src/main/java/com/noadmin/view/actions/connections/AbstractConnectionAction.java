package com.noadmin.view.actions.connections;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.noadmin.controller.Controller;
import com.noadmin.model.ConfigurationElementListener;
import com.noadmin.model.connection.Connection;
import com.noadmin.model.connection.ConnectionListener;
import com.noadmin.view.actions.DefaultAction;
import com.noadmin.view.beans.GUIHandler;
import com.noadmin.view.connections.ConnectionsTree;
;

/**
 * Abstract class for any actions dealing with connection.
 *
 * @author mkhelif
 */
public abstract class AbstractConnectionAction extends DefaultAction implements TreeSelectionListener, ConnectionListener, ConfigurationElementListener {

	/**
	 * The GUI handler.
	 */
	protected final GUIHandler handler;

	/**
	 * The selected connection in the tree.
	 */
	private Connection connection = null;

    /**
     * Create a new connection action.
     * @param label the i18n key of the action label.
     * @param icon the path to the icon.
     */
	AbstractConnectionAction(final String label, final String icon) {
		super(label, icon);
		handler = Controller.getInstance().getBean(GUIHandler.class);

		final JTree tree = Controller.getInstance().getBean(ConnectionsTree.class);
		tree.addTreeSelectionListener(this);
	}

    /**
     * Called whenever the value of the selection changes.
     * @param evt the event that characterizes the change.
     */
	@Override
	public final void valueChanged(final TreeSelectionEvent evt) {
		final ConnectionsTree tree = (ConnectionsTree) evt.getSource();
		final Connection contextConnection = tree.getSelected(Connection.class);
		if (this.connection == contextConnection) {
			return;
		}

		if (connection != null) {
			connection.removeConnectionListener(this);
			connection.removeConfigurationElementListener(this);
		}
		connection = contextConnection;
		if (connection != null) {
			connection.addConnectionListener(this);
			connection.addConfigurationElementListener(this);
		}
		if (connection == null) {
			this.setEnabled(false);
		} else {
			this.setEnabled(this.isEnabled(connection));
		}
	}

	/**
	 * @return the selected connection.
	 */
	protected final Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the selected connection.
	 * @return TRUE if the action is enabled for the connection,
	 *         FALSE otherwise.
	 */
	protected abstract boolean isEnabled(final Connection connection);

	/**
	 * The element has been updated.
	 */
	public void elementUpdated() {}

	/**
	 * The connection has failed to connect to the database server.
	 * @param t the received exception.
	 */
	public void connectionFailed(final Throwable t) {}

	/**
	 * The connection is trying to establish.
	 */
	public void connectionEstablishing() {}

	/**
	 * The connection has been established.
	 */
	public void connectionEstablished() {}

	/**
	 * The connection is closing.
	 */
	public void connectionClosing() {}

	/**
	 * The connection has been closed.
	 */
	public void connectionClosed() {}
}