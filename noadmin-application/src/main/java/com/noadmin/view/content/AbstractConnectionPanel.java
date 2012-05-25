package com.noadmin.view.content;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.noadmin.model.connection.Connection;
import com.noadmin.model.connection.ConnectionListener;

/**
 * Abstract connection definition panel.
 *
 * @author mkhelif
 */
public abstract class AbstractConnectionPanel<C extends Connection> extends AbstractElementPanel<C> implements ConnectionListener {

	/**
	 * The displayed connection.
	 */
	protected C connection;

	public AbstractConnectionPanel(final String i18n) {
		super(i18n);
	}

	/**
	 * @see fr.mkhelif.noadmin.view.content.PluginPanel#initialize()
	 */
	@Override
	protected AbstractConnectionPanel<C> initialize() {
		// Buttons:
		final JPanel buttons = new JPanel(new FlowLayout());
		//buttons.add(new JButton(ConnectAction.getInstance()));
		//buttons.add(new JButton(DisconnectAction.getInstance()));

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 1000;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1000;
		c.insets = new Insets(10, 0, 0, 0);
		this.add(buttons, c);

		return this;
	}

	/**
	 * @see fr.mkhelif.noadmin.view.content.PluginPanel#setContent(java.lang.Object)
	 */
	@Override
	public void setContent(final C connection) {
		if (this.connection != null) {
			this.connection.removeConnectionListener(this);
		}
		this.connection = connection;
		if (this.connection != null) {
			this.connection.addConnectionListener(this);
		}
		if (connection != null && connection.isConnected()) {
			this.update();
		}
	}

	/**
	 * Update the informations displayed in the panel.
	 */
	protected abstract void update();

	/**
	 * The connection has failed to connect to the database server.
	 * @param e the received exception.
	 */
	public void connectionFailed(final Throwable t) {
		// TODO display the error message.
	}

	/**
	 * The connection is trying to establish.
	 */
	public void connectionEstablishing() {
		// Nothing to do
	}

	/**
	 * The connection has been established.
	 */
	public void connectionEstablished() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				update();
			}
		});
	}

	/**
	 * The connection is closing.
	 */
	public void connectionClosing() {
		// Nothing to do
	}

	/**
	 * The connection has been closed.
	 */
	public void connectionClosed() {
		// Nothing to do
	}
}