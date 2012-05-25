package com.noadmin.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.view.actions.ExitAction;
import com.noadmin.view.actions.connections.AddConnectionAction;
import com.noadmin.view.actions.connections.ConnectAction;
import com.noadmin.view.actions.connections.DisconnectAction;
import com.noadmin.view.beans.DefaultMessageSource;

/**
 * Menu bar of the application.
 *
 * @author mkhelif
 */
@Component
public final class Menu extends JMenuBar {

	/**
	 * The translation messages manager.
	 */
	private final DefaultMessageSource i18n;

	@Autowired
	public Menu(final DefaultMessageSource i18n) {
		this.i18n = i18n;
	}

	public void initialize() {
		// File :
		final JMenu file = new JMenu(i18n.getMessage("menu.file"));
		file.add(ExitAction.getInstance().newMenu());
		this.add(file);

		// Edit :
		final JMenu edit = new JMenu(i18n.getMessage("menu.edit"));
		edit.add(AddConnectionAction.getInstance().newMenu());
		edit.addSeparator();
		edit.add(ConnectAction.getInstance().newMenu());
		edit.add(DisconnectAction.getInstance().newMenu());
		/*edit.addSeparator();
		edit.add(InsertKeyAction.getInstance().newMenu());
		edit.add(RemoveKeyAction.getInstance().newMenu());
		edit.add(InsertSuperKeyAction.getInstance().newMenu());
		edit.add(RemoveSuperKeyAction.getInstance().newMenu());
		edit.add(InsertValueAction.getInstance().newMenu());
		edit.add(RemoveValueAction.getInstance().newMenu());
		edit.addSeparator();
		edit.add(RefreshAction.getInstance().newMenu());*/
		this.add(edit);
	}
}