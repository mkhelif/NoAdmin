package com.noadmin.view.actions.connections;

import java.awt.event.ActionEvent;

import com.noadmin.controller.Controller;
import com.noadmin.controller.configuration.ConfigurationManager;
import com.noadmin.model.connection.Connection;

/**
 * Remove a connection.
 *
 * @author mkhelif
 */
public final class RemoveConnectionAction extends AbstractConnectionAction {

	/**
	 * Singleton pattern.
	 */
	private static final RemoveConnectionAction action = new RemoveConnectionAction();
	public static RemoveConnectionAction getInstance() { return action; }

	private RemoveConnectionAction() {
		super("action.remove", "connection;remove");
		this.setEnabled(false);
	}

	@Override
	protected boolean isEnabled(final Connection connection) {
		return true;
	}

    /**
     * Invoked when an action occurs.
     * @param evt the issued event.
     */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		final Connection connection = this.getConnection();

		// Delete the connection:
		if (handler.confirm(i18n("action.remove"), i18n("action.connection.remove", connection.getName()))) {
			if (connection.isConnected()) {
				connection.disconnect();
			}
			Controller.getInstance().getBean(ConfigurationManager.class).removeConnection(connection);
		}
	}
}