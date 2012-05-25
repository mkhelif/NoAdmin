package com.noadmin.view;

import javax.swing.JToolBar;

import com.noadmin.view.actions.connections.AddConnectionAction;
import com.noadmin.view.actions.connections.ConnectAction;
import com.noadmin.view.actions.connections.DisconnectAction;

/**
 * Main toolbar of the application.
 *
 * @author mkhelif
 */
public final class ToolBar extends JToolBar {

	public ToolBar() {
		// Components
		this.add(AddConnectionAction.getInstance().newButton());
		this.addSeparator();
		this.add(ConnectAction.getInstance().newButton());
		this.add(DisconnectAction.getInstance().newButton());
		this.addSeparator();
		/*this.add(RefreshAction.getInstance());
		this.addSeparator();
		this.add(InsertKeyAction.getInstance());
		this.add(RemoveKeyAction.getInstance());
		this.add(InsertSuperKeyAction.getInstance());
		this.add(RemoveSuperKeyAction.getInstance());
		this.add(InsertValueAction.getInstance());
		this.add(RemoveValueAction.getInstance());*/

		// Settings
		this.setFloatable(false);
	}
}