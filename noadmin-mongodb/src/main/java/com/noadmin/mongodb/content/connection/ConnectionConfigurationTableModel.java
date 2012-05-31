package com.noadmin.mongodb.content.connection;

import javax.swing.table.DefaultTableModel;

import com.mongodb.MongoOptions;
import com.noadmin.view.beans.DefaultMessageSource;

/**
 * Table model that displays the connection configuration.
 *
 * @author mkhelif
 */
public final class ConnectionConfigurationTableModel extends DefaultTableModel {

	/**
	 * The headers of the columns.
	 */
	private final String[] headers = new String[2];

	/**
	 * The name of the options displayed in the table.
	 */
	private final String[] infos = new String[13];

	/**
	 * The MongoDB options to display.
	 */
	private MongoOptions options;

	public ConnectionConfigurationTableModel() {
		final DefaultMessageSource i18n = new DefaultMessageSource("mongodb");

		// Load columns headers
		headers[0] = i18n.getMessage("mongodb.connection.options.label");
		headers[1] = i18n.getMessage("mongodb.connection.options.value");

		// Load options names
		infos[0] = "Auto-connect retry";
		infos[1] = "Max auto-connect retry time";
		infos[2] = "Connections per host";
		infos[3] = "Connect Timeout";
		infos[4] = "Description";
		infos[5] = "FSync";
		infos[6] = "Max wait time";
		infos[7] = "Safe";
		infos[8] = "Socket Keep-Alive";
		infos[9] = "Socket timeout";
		infos[10] = "Threads allowed to block";
		infos[11] = "Slaves write behavior";
		infos[12] = "Slaves write timeout";
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return (options == null ? 0 : infos.length);
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return headers.length;
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int column) {
		return headers[column];
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(final int row, final int column) {
		switch (column) {
			case 0: return infos[row];
			case 1: return this.getOptionValue(row);
			default: return null;
		}
	}

	/**
	 * Retrieve the value of an option.
	 * @param row the row of the option.
	 * @return the value of the MongoDB option.
	 */
	private Object getOptionValue(final int row) {
		switch (row) {
			case 0: return options.autoConnectRetry;
			case 1: return options.maxAutoConnectRetryTime;
			case 2: return options.connectionsPerHost;
			case 3: return options.connectTimeout;
			case 4: return options.description;
			case 5: return options.fsync;
			case 6: return options.maxWaitTime;
			case 7: return options.safe;
			case 8: return options.socketKeepAlive;
			case 9: return options.socketTimeout;
			case 10: return options.threadsAllowedToBlockForConnectionMultiplier;
			case 11: return options.w;
			case 12: return options.wtimeout;
			default: return null;
		}
	}

	/**
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

	/**
	 * Update the informations displayed in the table.
	 * @param options the MongoDB options to display.
	 */
	public void setContent(final MongoOptions options) {
		this.options = options;
		this.fireTableDataChanged();
	}
}