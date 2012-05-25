package com.noadmin.mongodb.content.connection;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.noadmin.mongodb.model.MongoDBConnection;
import com.noadmin.view.content.AbstractConnectionPanel;

/**
 * MongoDB connection content panel.
 *
 * @author mkhelif
 */
@Component("com.noadmin.mongodb.node.ConnectionNode")
public final class ConnectionPanel extends AbstractConnectionPanel<MongoDBConnection> {

	/**
	 * The version of the MongoDB server.
	 */
	private final JLabel version = newValueLabel();

	/**
	 * The maximum BSON objects size.
	 */
	private final JLabel maxBsonObjectSize = newValueLabel();

	/**
	 * The name of the replica set status.
	 */
	private final JLabel replicaSetStatus = newValueLabel();

	/**
	 * The MongoDB configuration table model.
	 */
	private final ConnectionConfigurationTableModel model = new ConnectionConfigurationTableModel();

	public ConnectionPanel() {
		super("mongodb");
	}

	/**
	 * @see fr.mkhelif.noadmin.view.content.PluginPanel#initialize()
	 */
	@Override
	protected ConnectionPanel initialize() {
		// Database informations:
		this.addInformation("mongodb.connection.version", version, 0);
		this.addInformation("mongodb.connection.maxBsonObjectSize", maxBsonObjectSize, 1);
		this.addInformation("mongodb.connection.replicaSetStatus", replicaSetStatus, 2);

		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 100;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(new JScrollPane(new JTable(model)), c);

		// Buttons:
		super.initialize();

		return this;
	}

	/**
	 * Update the informations displayed in the panel.
	 */
	@Override
	protected void update() {
		version.setText(connection.getVersion());
		maxBsonObjectSize.setText(Integer.toString(connection.getMaxBsonObjectSize()));
		replicaSetStatus.setText(connection.getReplicaSetStatus());
		model.setContent(connection.getMongoOptions());
	}
}