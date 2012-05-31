package com.noadmin.mongodb.content.collection;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer of the document type in a collection.
 *
 * @author mkhelif
 */
public final class CollectionTableClassRenderer extends DefaultTableCellRenderer {

	/**
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		final Class<?> type = (Class<?>) value;
		if (type == null) {
			this.setText("");
		} else {
			this.setText(type.getSimpleName());
		}

		return this;
	}
}