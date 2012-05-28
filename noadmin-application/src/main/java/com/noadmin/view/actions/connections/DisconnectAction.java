package com.noadmin.view.actions.connections;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import com.noadmin.model.connection.Connection;

/**
 * Disconnect the selected connection.
 *
 * @author mkhelif
 */
public final class DisconnectAction extends AbstractConnectionAction {

	/**
	 * Singleton pattern.
	 */
	private static final DisconnectAction action = new DisconnectAction();
	public static DisconnectAction getInstance() { return action; }

	private DisconnectAction() {
		super("action.connection.disconnect", "connection;disconnect");
	}

    /**
     * Invoked when an action occurs.
     * @param evt the issued event.
     */
	@Override
	public final void actionPerformed(final ActionEvent evt) {
		new Thread() {
			@Override
			public void run() {
				getConnection().disconnect();
			}
		}.start();
	}

	/**
	 * @param connection the selected connection.
	 * @return TRUE if the connection is establish, FALSE otherwise.
	 */
	@Override
	protected boolean isEnabled(final Connection connection) {
		return connection.isConnected();
	}

	/**
	 * The connection has been established.
	 */
	@Override
	public void connectionEstablished() {
		this.setEnabled(true);
	}

	/**
	 * The connection is closing.
	 */
	@Override
	public void connectionClosing() {
		handler.cursor(Cursor.WAIT_CURSOR);
		handler.information("action.connection.disconnect.start");
	}

	/**
	 * The connection has been closed.
	 */
	@Override
	public void connectionClosed() {
		handler.information("action.connection.disconnect.done");
		handler.cursor(Cursor.DEFAULT_CURSOR);
		this.setEnabled(false);
	}
}