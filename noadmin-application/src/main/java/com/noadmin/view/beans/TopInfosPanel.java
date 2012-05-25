package com.noadmin.view.beans;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.noadmin.controller.Controller;

/**
 * This panel is used as the top panel to display informations.
 * It provide the possibility to display a main title and a message with an icon for information.
 *
 * @author mkhelif
 */
public final class TopInfosPanel extends JPanel {

	/**
	 * The icons manager.
	 */
	private final IconManager icon;

	/**
	 * Title label of the informations panel.
	 */
	private final JLabel title;

	/**
	 * The status label which holds the icon and message.
	 */
	private final JLabel status;

	public TopInfosPanel() {
		super(new GridBagLayout());
		icon = Controller.getInstance().getBean(IconManager.class);

		// Initialize components
		title = new JLabel();
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		status = new JLabel();

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 5, 10);
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;

        c.gridy = 0;
		this.add(title, c);

        c.gridy = 1;
		this.add(status, c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(new JSeparator(), c);

		// Panel settings
		this.setBackground(Color.WHITE);
	}

	/**
	 * Display an error message in this informations panel.
	 */
	public void setError(final String text) {
		status.setIcon(icon.getIcon("tick.error"));
		status.setText(text);
	}

	/**
	 * Display an information message in this informations panel.
	 */
	public void setInfo(final String text) {
		status.setIcon(icon.getIcon("tick.info"));
		status.setText(text);
	}

	/**
	 * Display a warning message in this informations panel.
	 */
	public void setWarning(final String text) {
		status.setIcon(icon.getIcon("tick.warning"));
		status.setText(text);
	}

	/**
	 * Update the title of this informations panel.
	 */
	public void setTitle(final String text) {
		title.setText(text);
	}
}