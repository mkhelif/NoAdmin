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

	private final DefaultMessageSource i18n;

	private MongoOptions options;

	public ConnectionConfigurationTableModel() {
		this.i18n = new DefaultMessageSource("mongodb");
	}

	@Override
	public int getRowCount() {
		return options == null ? 0 : 13;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(final int column) {
		switch (column) {
			case 0: return i18n("mongodb.connection.options.label");
			case 1: return i18n("mongodb.connection.options.value");
			default: return null;
		}
	}

	@Override
	public Object getValueAt(final int row, final int column) {
		switch (column) {
			case 0: return this.getOptionKey(row);
			case 1: return this.getOptionValue(row);
			default: return null;
		}
	}

	private String getOptionKey(final int row) {
		switch (row) {
			case 0: return "Auto-connect retry";
			case 1: return "Max auto-connect retry time";
			case 2: return "Connections per host";
			case 3: return "Connect Timeout";
			case 4: return "Description";
			case 5: return "FSync";
			case 6: return "Max wait time";
			case 7: return "Safe";
			case 8: return "Socket Keep-Alive";
			case 9: return "Socket timeout";
			case 10: return "Threads allowed to block";
			case 11: return "Slaves write behavior";
			case 12: return "Slaves write timeout";
			default: return null;
		}
	}

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

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

	public void setContent(final MongoOptions options) {
		this.options = options;
		this.fireTableDataChanged();
	}

	/**
	 * Get the translation value of the key.
	 * @param key the key of the translation.
	 * @return the translation string.
	 */
	protected final String i18n(final String key) {
		return i18n.getMessage(key);
	}
}