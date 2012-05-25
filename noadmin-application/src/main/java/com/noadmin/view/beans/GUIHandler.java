package com.noadmin.view.beans;

import java.awt.Cursor;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.view.InformationsPanel;
import com.noadmin.view.Workbench;

/**
 * GUI handler that handles  coming from components or services.
 *
 * @author mkhelif
 */
@Component
public final class GUIHandler {

	/**
	 * The reference to the Workbench.
	 */
	private final Workbench workbench;

	/**
	 * The informations panel used to display messages.
	 */
	private final InformationsPanel informations;

	/**
	 * The localization messages manager.
	 */
	private final DefaultMessageSource i18n;

	@Autowired
	public GUIHandler(final Workbench workbench, final InformationsPanel informations, final DefaultMessageSource i18n) {
		this.workbench = workbench;
		this.informations = informations;
		this.i18n = i18n;
	}

	/**
	 * Display an alert message.
	 */
	public void alert(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				informations.setWarning(message);
				JOptionPane.showConfirmDialog(workbench, message, i18n.getMessage("error"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		});
	}

	/**
	 * Display a warning message.
	 */
	public void warn(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				informations.setWarning(message);
			}
		});
	}

	/**
	 * Display an information message.
	 */
	public void information(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				informations.setInformation(i18n.getMessage(message));
			}
		});
	}

	/**
	 * Ask a confirmation.
	 */
	public boolean confirm(final String title, final String message) {
		return JOptionPane.showConfirmDialog(workbench, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
	}

	/**
	 * Update the cursor.
	 */
	public void cursor(final int cursor) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				workbench.setCursor(new Cursor(cursor));
			}
		});
	}
}