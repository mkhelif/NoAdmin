package com.noadmin.view.connections;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.noadmin.controller.Controller;
import com.noadmin.controller.configuration.ConfigurationManager;
import com.noadmin.model.connection.Connection;
import com.noadmin.model.connection.ConnectionFactory;
import com.noadmin.view.Workbench;
import com.noadmin.view.beans.SimpleDialog;

/**
 * Dialog used to create a new connection.
 *
 * @author mkhelif
 */
public final class ConnectionDialog extends SimpleDialog {

	/**
	 * The factory of connections.
	 */
	private final ConnectionFactory factory;

	/**
	 * The type of connection to create.
	 */
	private JComboBox<String> type;

	/**
	 * The display name of the databases server.
	 */
	private JTextField name;

	/**
	 * The address or host name of the database server.
	 */
	private JTextField hostname;

	/**
	 * The port of the database server.
	 */
	private JTextField port;

	/**
	 * Create a new connection dialog.
	 */
	public ConnectionDialog(final Workbench workbench, final ConnectionFactory factory) {
		super(workbench);
		this.factory = factory;

		// Fill the connection types
		for (final String availableType : factory.getConnectionTypes()) {
			type.addItem(availableType);
		}

		// Dialog settings
		this.setInfosTitle(i18n("connection.add.header.title"));
		this.setInfo(i18n("connection.add.header.info"));
		this.setTitle(i18n("connection.add.title"));
		this.setModal(true);
		this.pack();
		this.setLocationRelativeTo(workbench);
	}

	/**
	 * Create the content panel of this dialog.
	 * @return the main content panel.
	 */
	@Override
	protected JPanel getContentPanel() {
		// Create components :
		type = new JComboBox<>();
		name = new JTextField();
		hostname = new JTextField();
		port = new JTextField();

		// Layout components :
		final JPanel content = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 5, 5, 5);
		content.add(new JLabel(i18n("connection.type")), c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 5, 5);
		content.add(type, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 5, 5, 5);
		content.add(new JLabel(i18n("connection.name")), c);

		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 5, 5);
		content.add(name, c);

		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 5, 5, 5);
		content.add(new JLabel(i18n("connection.hostname")), c);

		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 5, 5);
		content.add(hostname, c);

		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 5, 5, 5);
		content.add(new JLabel(i18n("connection.port")), c);

		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 5, 5);
		content.add(port, c);

		return content;
	}

	/**
	 * The "Ok" button has been pressed, save the dialog content.
	 * @return TRUE if the content is valid and if the dialog can be closed,
	 *         FALSE to keep the dialog opened.
	 */
	@Override
	protected boolean save() {
		final Controller controller = Controller.getInstance();

		// Create the new connection :
		final Connection connection = factory.newConnection((String) type.getSelectedItem());
		connection.setName(name.getText());
		connection.setHostname(hostname.getText());
		try {
			connection.setPort(Integer.parseInt(port.getText()));
		} catch (final NumberFormatException e) {
			// TODO display error
			return false;
		}

		// Add the connection :
		controller.getBean(ConfigurationManager.class).addConnection(connection);

		return true;
	}
}