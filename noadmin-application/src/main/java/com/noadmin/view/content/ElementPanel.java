package com.noadmin.view.content;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * All the content panel must extends this class.
 *
 * @param <T> the type of object this panel displays.
 * @author mkhelif
 */
public abstract class ElementPanel<T> extends JPanel {

	/**
	 * Create a new plugin panel with a specific layout manager.
	 * @param layout the layout manager.
	 */
	public ElementPanel(final LayoutManager layout) {
		super(layout);
	}

	/**
	 * Initialize the panel.
	 * @return this instance of the plugin panel.
	 */
	protected abstract ElementPanel<T> initialize();

	/**
	 * Update the content of the panel.
	 * @param content the new object to display in the panel.
	 */
	public abstract void setContent(final T content);
}