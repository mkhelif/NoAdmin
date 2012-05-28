package com.noadmin.view.beans.tree;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;

/**
 * Table editor for Class type selection.
 *
 * @author mkhelif
 */
public final class ClassTableEditor extends DefaultCellEditor {

	public ClassTableEditor() {
		super(new ClassComboBox());
	}

	public ClassTableEditor(final Class<?>[] classes) {
		super(new ClassComboBox(classes));
	}

	/**
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
		final ClassComboBox combo = (ClassComboBox) super.getTableCellEditorComponent(table, value, isSelected, row, column);
		combo.setSelectedItem(value);

		return combo;
	}
}