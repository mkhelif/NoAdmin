package com.noadmin.view;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.view.beans.IconManager;

/**
 * Panel used to display information to the end-user.
 *
 * @author mkhelif
 */
@Component
public final class InformationsPanel extends JPanel {

	/**
	 * The manager of icons.
	 */
	private final IconManager icon;

	/**
	 * The Thread used to timeout the informations.
	 */
	private Thread thread;

	/**
	 * The label that displays the message.
	 */
	private final JLabel label;

	@Autowired
	public InformationsPanel(final IconManager icon) {
		this.label = new JLabel(" ");
		this.icon = icon;

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(label);
	}

	/**
	 * Display an informations message.
	 * @param label the message to display.
	 */
	public void setInformation(final String label) {
		this.label.setText(label);
		this.label.setIcon(icon.getIcon("tick/info"));
		this.timeout();
	}

	/**
	 * Display a warning message.
	 * @param label the message to display.
	 */
	public void setWarning(final String label) {
		this.label.setText(label);
		this.label.setIcon(icon.getIcon("tick/warning"));
		this.timeout();
	}

	/**
	 * Clear the informations labels.
	 */
	public void clear() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				label.setText(" ");
				label.setIcon(null);
			}
		});
	}

	/**
	 * Clear the message labels after 5 seconds.
	 */
	private void timeout() {
		if (thread != null) {
			thread.interrupt();
		}
		thread = new Thread("InformationTimeout") {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (final InterruptedException e) {
					return;
				}

				// Hide the label :
				clear();
			}
		};
		thread.start();
	}
}