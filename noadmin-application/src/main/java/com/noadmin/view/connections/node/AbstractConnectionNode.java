package com.noadmin.view.connections.node;

import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.noadmin.model.ConfigurationElementListener;
import com.noadmin.model.connection.Connection;
import com.noadmin.model.connection.ConnectionListener;
import com.noadmin.view.beans.CompoundIcon;

/**
 * Abstract node for all the connection nodes.
 *
 * @param <T> the type of connection mapped by the node.
 * @author mkhelif
 */
public abstract class AbstractConnectionNode<T extends Connection> extends AbstractNode<T> implements ConfigurationElementListener, ConnectionListener {

	/**
	 * The compound icon for this node.
	 */
	private final CompoundIcon icon;

	/**
	 * Create a new connection node for the connection.
	 * @param connection the connection to display.
	 */
	public AbstractConnectionNode(final T connection) {
		super(connection);
		icon = new CompoundIcon(loadIcon("connection"));

		// Register events listener :
		connection.addConnectionListener(this);
		connection.addConfigurationElementListener(this);
	}

	/**
	 * The label of the object to display as node's text.
	 * @return the label of the node.
	 */
	@Override
	public final String getLabel() {
		return getUserObject().getName();
	}

	/**
	 * @return the icon to display for this node.
	 */
	@Override
	public final Icon getIcon() {
		return icon;
	}

	/**
	 * The connection has been updated.
	 */
	public final void elementUpdated() {
		getModel().nodeChanged(this);
	}

	/**
	 * The connection has failed to connect to the database server.
	 * @param e the received exception.
	 */
	public final void connectionFailed(final Exception e) {
		icon.setTick(loadIcon("tick.warning"));
		this.elementUpdated();
	}

	/**
	 * The connection is trying to establish.
	 */
	public final void connectionEstablishing() {
		icon.setTick(loadIcon("tick.refresh"));
		this.elementUpdated();
	}

	/**
	 * The connection has been established.
	 */
	public void connectionEstablished() {
		icon.setTick(loadIcon("tick.green"));
		this.elementUpdated();
	}

	/**
	 * The connection is closing.
	 */
	public final void connectionClosing() {
		icon.setTick(loadIcon("tick.refresh"));
		this.elementUpdated();
	}

	/**
	 * The connection has been closed.
	 */
	public final void connectionClosed() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				icon.setTick(null);
				removeAllChildren();
				getTree().setSelectionPath(new TreePath(getPath()));
			}
		});
	}
}