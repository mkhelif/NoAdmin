package com.noadmin.view.content;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import com.noadmin.view.beans.DefaultMessageSource;

/**
 * Abstract class for a element panel.
 *
 * @param <T> the type of element this panel displays.
 * @author mkhelif
 */
public abstract class AbstractElementPanel<T> extends ElementPanel<T> {

	/**
	 * The internationalization messages manager.
	 */
	private final DefaultMessageSource i18n;

	/**
	 * Create a new element panel with a GridBagLayout manager.
	 */
	public AbstractElementPanel(final String i18n) {
		super(new GridBagLayout());
		this.i18n = new DefaultMessageSource(i18n);
	}

	/**
	 * Add an information label.
	 * @param key the i18n key of the information.
	 * @param label the label that will display the value.
	 * @param gridy the position in the layout.
	 */
	protected final void addInformation(final String key, final JLabel label, final int gridy) {
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = gridy;
		c.weightx = 0;
		c.insets = new Insets(5, 5, 0, 5);
		this.add(newKeyLabel(key), c);
		c.gridx = 1;
		c.gridy = gridy;
		c.weightx = 1;
		c.insets = new Insets(5, 0, 0, 5);
		this.add(label, c);
	}

	/**
	 * Create a new label used to display the name of an information.
	 * @param key the i18n key of the label text.
	 * @return the label.
	 */
	protected final JLabel newKeyLabel(final String key) {
		return new JLabel(i18n(key), JLabel.RIGHT);
	}

	/**
	 * Create a new label used to display the value of an information.
	 * @return the label.
	 */
	protected final JLabel newValueLabel() {
		final JLabel label = new JLabel();
		label.setFont(label.getFont().deriveFont(Font.ITALIC));
		return label;
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