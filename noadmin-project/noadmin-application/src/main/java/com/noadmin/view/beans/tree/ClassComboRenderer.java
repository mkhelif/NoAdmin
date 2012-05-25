package com.noadmin.view.beans.tree;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * Renderer used for Class {@link JComboBox}.
 *
 * @author mkhelif
 */
public final class ClassComboRenderer extends DefaultListCellRenderer {

	/**
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(final JList<? extends Object> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
		final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value != null) {
			label.setText(this.getName((Class<?>) value));
		}

		return label;
	}

	/**
	 * Get the name of the class.
	 * For "java.lang" package, only the name of the class is used.
	 * For other packages, the full qualified name of the class is used.
	 * @param c the class to get the name.
	 * @return the name of the class.
	 */
	private String getName(final Class<?> c) {
		if (c.getName().startsWith("java.lang")) {
			return c.getSimpleName();
		}
		return c.getName();
	}
}