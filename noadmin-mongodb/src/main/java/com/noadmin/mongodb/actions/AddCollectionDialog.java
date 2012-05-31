package com.noadmin.mongodb.actions;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mongodb.DB;
import com.noadmin.controller.Controller;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.view.Workbench;
import com.noadmin.view.beans.DefaultMessageSource;
import com.noadmin.view.beans.SimpleDialog;

/**
 * Simple dialog used to add a {@link Collection}.
 *
 * @author mkhelif
 */
public final class AddCollectionDialog extends SimpleDialog {

	/**
	 * The internationalization messages manager.
	 */
	private final DefaultMessageSource i18n;

	/**
	 * The name of the new collection.
	 */
	private JTextField name;

	/**
	 * Is the new collection capped or not?
	 */
	private JCheckBox capped;

	/**
	 * If the collection is capped, the max size.
	 */
	private JTextField size;

	/**
	 * Is the new collection capped or not?
	 */
	private JCheckBox limitObjects;

	/**
	 * The maximum number of objects in the collection.
	 */
	private JTextField maxObjects;

	public AddCollectionDialog(final DB db) {
		super(Controller.getInstance().getBean(Workbench.class));
		this.i18n = new DefaultMessageSource("mongodb");
		this.setTitle(i18n.getMessage("mongodb.collection.add"));
		this.setInfosTitle(i18n.getMessage("mongodb.collection.add"));
		this.setInfo(i18n.getMessage("mongodb.collection.add.info"));
		this.setModal(true);
		this.pack();
		this.setLocationRelativeTo(Controller.getInstance().getBean(Workbench.class));
	}

	/**
	 * Build the internal content panel.
	 * @see fr.mkhelif.noadmin.view.bean.SimpleDialog#getContentPanel()
	 */
	@Override
	protected JPanel getContentPanel() {
		// Create components:
		name = new JTextField();
		capped = new JCheckBox(i18n.getMessage("mongodb.collection.add.capped"));
		size = new JTextField();
		size.setEnabled(false);
		limitObjects = new JCheckBox(i18n.getMessage("mongodb.collection.add.objects"));
		limitObjects.setEnabled(false);
		maxObjects = new JTextField();
		maxObjects.setEnabled(false);

		// Events handling:
		capped.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				size.setEnabled(capped.isSelected());
				limitObjects.setEnabled(capped.isSelected());
				maxObjects.setEnabled(limitObjects.isSelected());
			}
		});
		limitObjects.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				maxObjects.setEnabled(limitObjects.isSelected());
			}
		});

		// Layout components:
		final JPanel content = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;

		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 0);
		content.add(getNamePanel(), c);

		c.gridy = 1;
		c.insets = new Insets(5, 0, 0, 0);
		content.add(getCappedPanel(), c);

		c.gridy = 2;
		c.insets = new Insets(5, 0, 0, 0);
		content.add(getObjectsPanel(), c);

		c.gridy = 3;
		c.weighty = 1;
		content.add(new JPanel(), c);

		return content;
	}

	private JPanel getNamePanel() {
		final JPanel namePanel = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.weightx = 0;
		c.insets = new Insets(0, 10, 0, 5);
		namePanel.add(new JLabel(i18n.getMessage("mongodb.collection.add.name")), c);

		c.gridx = 1;
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 10);
		namePanel.add(name, c);

		return namePanel;
	}

	private JPanel getCappedPanel() {
		final JPanel cappedPanel = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.weightx = 0;
		c.insets = new Insets(0, 10, 0, 5);
		cappedPanel.add(capped, c);

		c.gridx = 1;
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 10);
		cappedPanel.add(size, c);

		return cappedPanel;
	}

	private JPanel getObjectsPanel() {
		final JPanel objectsPanel = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.weightx = 0;
		c.insets = new Insets(0, 10, 0, 5);
		objectsPanel.add(limitObjects, c);

		c.gridx = 1;
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 10);
		objectsPanel.add(maxObjects, c);

		return objectsPanel;
	}

	/**
	 * Save the collection.
	 * @see fr.mkhelif.noadmin.view.bean.SimpleDialog#save()
	 */
	@Override
	protected boolean save() {
		return false;
	}

	/**
	 * Set the default name of the collection.
	 * @param name the default name.
	 */
	public void setDefaultName(final String name) {
		this.name.setText(name);
	}
}