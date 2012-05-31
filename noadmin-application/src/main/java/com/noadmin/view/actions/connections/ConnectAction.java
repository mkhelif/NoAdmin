package com.noadmin.view.actions.connections;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionListener;

import com.noadmin.model.ConfigurationElementListener;
import com.noadmin.model.connection.Connection;
import com.noadmin.model.connection.ConnectionListener;

/**
 * Create a new Cassandra connection.
 *
 * @author mkhelif
 */
public final class ConnectAction extends AbstractConnectionAction implements TreeSelectionListener, ConnectionListener, ConfigurationElementListener {

	/**
	 * Singleton pattern.
	 */
	private static final ConnectAction INSTANCE = new ConnectAction();
	public static ConnectAction getInstance() { return INSTANCE; }

	private ConnectAction() {
		super("action.connection.connect", "connection;connect");
	}

    /**
     * Invoked when an action occurs.
     * @param evt the issued event.
     */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		new Thread("EstablishConnection") {
			@Override
			public void run() {
				getConnection().connect();
			}
		}.start();
	}

	/**
	 * @param connection the selected connection.
	 * @return TRUE if the connection is not establish, FALSE otherwise.
	 */
	@Override
	protected boolean isEnabled(final Connection connection) {
		return !connection.isConnected();
	}

	@Override
	public void connectionEstablishing() {
		getGUIHandler().cursor(Cursor.WAIT_CURSOR);
		getGUIHandler().information("action.connection.connect.start");
	}

	@Override
	public void connectionEstablished() {
		getGUIHandler().cursor(Cursor.DEFAULT_CURSOR);
		getGUIHandler().information("action.connection.connect.done");
		this.setEnabled(false);
	}

	@Override
	public void connectionClosed() {
		this.setEnabled(true);
	}

	@Override
	public void connectionFailed(final Exception e) {
		getGUIHandler().cursor(Cursor.DEFAULT_CURSOR);
		getGUIHandler().alert(e.getMessage());
		this.setEnabled(true);
	}
}