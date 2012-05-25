package com.noadmin.mongodb.content.collection;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.noadmin.mongodb.model.DBObject;

/**
 * Editor of document name. This is used to edit only names of property documents.
 *
 * @author mkhelif
 */
public final class DocumentEditor extends DefaultCellEditor {

	public DocumentEditor() {
		super(new JTextField());
	}

	/**
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
		final JTextField field = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

		if (value instanceof DBObject) {
			field.setText(getName(((DBObject) value)));
		}

		return field;
	}

	/**
	 * Retrieve the name of a {@link DBObject}.
	 * @param object the DB object.
	 * @return the name to display for the object.
	 */
	private String getName(final DBObject object) {
		final String name = object.getName();
		if (StringUtils.isNotEmpty(name)) {
			return name;
		}
		if (object.containsField("name")) {
			if (object.containsField("ns")) {
				return object.get("ns") + "." + object.get("name");
			} else {
				return String.valueOf(object.get("name"));
			}
		}
		if (object.containsField("_id")) {
			return String.valueOf(object.get("_id"));
		}
		return String.valueOf(object);
	}
}