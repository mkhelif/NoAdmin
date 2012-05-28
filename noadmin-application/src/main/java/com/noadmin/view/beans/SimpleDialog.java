package com.noadmin.view.beans;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import com.noadmin.controller.Controller;

/**
 * Abstract class for all the simple dialog (OK / Cancel).
 * It provides the Enter (validation) and Escape (close) key strokes.
 *
 * @author mkhelif
 */
public abstract class SimpleDialog extends JDialog {

	/**
	 * The internationalization messages manager.
	 */
	protected final DefaultMessageSource i18n;

	/**
	 * Top informations panel.
	 */
	private TopInfosPanel infos;

	/**
	 * The Ok button attached to the Enter key.
	 */
	private JButton ok;

	/**
	 * The Cancel button attached to the Escape key.
	 */
	private JButton cancel;

	/**
	 * Create a new simple dialog with the parent window.
	 * @param window the parent window of this dialog.
	 */
	public SimpleDialog(final Window window) {
		super(window);
		i18n = Controller.getInstance().getBean(DefaultMessageSource.class);
		this.initialize();
	}

	/**
	 * Initialize the dialog.
	 */
	private void initialize() {
		// Initialize components:
		infos = new TopInfosPanel();

		// Layout components:
		this.setLayout(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;

		c.gridy = 0;
		c.weighty = 0;
		this.add(infos, c);

		c.gridy = 1;
		c.weighty = 1;
		this.add(this.getContentPanel(), c);

		c.gridy = 2;
		c.weighty = 0;
		this.add(new JPanel(), c);

		c.gridy = 3;
		c.weighty = 0;
		this.add(new JSeparator(), c);

		c.gridy = 4;
		c.weighty = 0;
		final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttons.add(this.getOk());
		buttons.add(this.getCancel());
		this.add(buttons, c);
	}

	/**
	 * Create the content panel of this dialog.
	 * @return the main content panel.
	 */
	protected abstract JPanel getContentPanel();

	/**
	 * The "Ok" button has been pressed, save the dialog content.
	 * @return TRUE if the content is valid and if the dialog can be closed,
	 *         FALSE to keep the dialog opened.
	 */
	protected abstract boolean save();

	/**
	 * Update the informations panel with the given title.
	 * @param key the title of the top informations panel.
	 */
	protected final void setInfosTitle(final String key) {
		infos.setTitle(key);
	}

	/**
	 * Sets an information message in the informations panel.
	 * @param key the information message of the top panel.
	 */
	protected final void setInfo(final String key) {
		infos.setInfo(key);
	}

	/**
	 * Instanciate and get the Ok button of the dialog.
	 * @return the Ok button.
	 */
	private JButton getOk() {
		if (ok == null) {
			ok = new JButton(i18n.getMessage("ok"));
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent evt) {
					if (SimpleDialog.this.save()) {
						SimpleDialog.this.dispose();
					}
				}
			});
			this.getRootPane().setDefaultButton(ok);
		}
		return ok;
	}

	/**
	 * Instantiate and get the Cancel button of the dialog.
	 * @return the Cancel button.
	 */
	private JButton getCancel() {
		if (cancel == null) {
			final Action action = new AbstractAction() {
				@Override
				public void actionPerformed(final ActionEvent evt) {
					SimpleDialog.this.dispose();
				}
			};

			cancel = new JButton(action);
			cancel.setText(i18n.getMessage("cancel"));

			// Register as default escape button :
			final KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
			this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(escapeKey, "CANCEL_ACTION_KEY");
			this.getRootPane().getActionMap().put("CANCEL_ACTION_KEY", action);
		}
		return cancel;
	}
}