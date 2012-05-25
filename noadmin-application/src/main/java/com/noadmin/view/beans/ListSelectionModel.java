package com.noadmin.view.beans;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;

/**
 * Simple implementation of {@link DefaultListSelectionModel} which holds a reference
 * to the GUI component.
 *
 * @author mkhelif
 */
public final class ListSelectionModel extends DefaultListSelectionModel {

	private final JComponent component;

	public ListSelectionModel(final JComponent component) {
		this.component = component;
	}

	public JComponent getComponent() {
		return component;
	}
}