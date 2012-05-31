package com.noadmin.view.actions.connections;

import java.awt.event.ActionEvent;

import com.noadmin.controller.Controller;
import com.noadmin.model.connection.ConnectionFactory;
import com.noadmin.view.Workbench;
import com.noadmin.view.actions.DefaultAction;
import com.noadmin.view.connections.ConnectionDialog;

/**
 * Create a new connection.
 *
 * @author mkhelif
 */
public final class AddConnectionAction extends DefaultAction {

	/**
	 * Singleton pattern.
	 */
	private static final AddConnectionAction INSTANCE = new AddConnectionAction();
	public static AddConnectionAction getInstance() { return INSTANCE; }

	private AddConnectionAction() {
		super("action.connection.add", "connection;add");
		this.setEnabled(true);
	}

    /**
     * Invoked when an action occurs.
     * @param evt the issued event.
     */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		final Controller controller = Controller.getInstance();
		new ConnectionDialog(controller.getBean(Workbench.class), controller.getBean(ConnectionFactory.class)).setVisible(true);
	}
}