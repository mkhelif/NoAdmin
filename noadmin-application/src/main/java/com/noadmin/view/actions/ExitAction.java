package com.noadmin.view.actions;

import java.awt.event.ActionEvent;

import com.noadmin.controller.Controller;

/**
 * Action used to exit the application.
 *
 * @author mkhelif
 */
public final class ExitAction extends DefaultAction {

	/**
	 * Singleton pattern.
	 */
	private static final ExitAction action = new ExitAction();
	public static ExitAction getInstance() { return action; }

	private ExitAction() {
		super("action.exit");
		this.setEnabled(true);
	}

    /**
     * Invoked when an action occurs.
     * @param evt the issued event.
     */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		Controller.getInstance().stopService();
	}
}