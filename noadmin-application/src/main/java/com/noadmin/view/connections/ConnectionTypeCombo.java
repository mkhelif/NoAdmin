package com.noadmin.view.connections;

import javax.swing.JComboBox;

/**
 * Combobox for modules connection type.
 *
 * @author mkhelif
 */
public final class ConnectionTypeCombo extends JComboBox {

	public ConnectionTypeCombo() {
		super();
	}

	/**
	 * @return the selected connection type.
	 */
	@Override
	public String getSelectedItem() {
		return (String) super.getSelectedItem();
	}

	/**
	 * @return the selected connection type reference.
	 */
	public Object getSelected() {
		return null;
	}
}