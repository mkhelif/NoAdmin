package com.noadmin.mongodb.content.collection;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.springframework.stereotype.Component;

import com.noadmin.mongodb.actions.AddDocumentAction;
import com.noadmin.mongodb.actions.AddFieldAction;
import com.noadmin.mongodb.actions.RefreshCollectionAction;
import com.noadmin.mongodb.actions.RemoveDocumentAction;
import com.noadmin.mongodb.actions.RemoveFieldAction;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.mongodb.model.DBObjectList;
import com.noadmin.mongodb.model.DBReference;
import com.noadmin.view.content.AbstractElementPanel;

/**
 * MongoDB Collection content panel.
 *
 * @author mkhelif
 */
@Component("com.noadmin.mongodb.node.CollectionNode")
public final class CollectionPanel extends AbstractElementPanel<Collection> implements ListSelectionListener {

	/**
	 * The MongoDB data tree.
	 */
	private CollectionTree tree;

	public CollectionPanel() {
		super("mongodb");
	}

	/**
	 * @see fr.mkhelif.noadmin.view.content.PluginPanel#initialize()
	 */
	@Override
	protected CollectionPanel initialize() {
		tree = new CollectionTree();
		tree.getSelectionModel().addListSelectionListener(this);
		tree.getSelectionModel().addListSelectionListener(RemoveDocumentAction.getInstance());
		tree.getSelectionModel().addListSelectionListener(AddFieldAction.getInstance());

		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;

		c.gridy = 0;
		c.weighty = 0;
		this.add(new CollectionToolbar(), c);

		c.gridy = 1;
		c.weighty = 1;
		this.add(new JScrollPane(tree), c);

		this.registerKeyboardAction(
			RefreshCollectionAction.getInstance(),
			KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
			JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		return this;
	}

	/**
	 * @see fr.mkhelif.noadmin.view.content.PluginPanel#setContent(java.lang.Object)
	 */
	@Override
	public void setContent(final Collection collection) {
		this.tree.setCollection(collection);

		RefreshCollectionAction.getInstance().setCollection(collection);
		AddDocumentAction.getInstance().setCollection(collection);
		RemoveDocumentAction.getInstance().setCollection(collection);
	}

	/**
	 * TODO review this method to simplify.
	 *
	 * Update the selection related actions.
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final int[] rows = tree.getSelectedRows();

		final List<DBObject> documents = new ArrayList<>();
		final Map<DBObject, List<String>> entries = new HashMap<>();
		for (final int row : rows) {
			final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) tree.getPathForRow(row).getLastPathComponent();

			if (node.getUserObject() instanceof DBObject) {
				if (tree.getTreeTableModel().getRoot() == node) {
					documents.add((DBObject) node.getUserObject());
				} else {
					final DefaultMutableTreeTableNode parentNode = (DefaultMutableTreeTableNode) node.getParent();
					final DBObject parent = (DBObject) parentNode.getUserObject();
					final String name = ((DBObject) node.getUserObject()).getName();

					if (!entries.containsKey(parent)) {
						entries.put(parent, new ArrayList<String>());
					}
					entries.get(parent).add(name);
				}
			} else if (node.getUserObject() instanceof DBObjectList) {
				// TODO
			} else if (node.getUserObject() instanceof DBReference) {
				// TODO
			} else {
				final DefaultMutableTreeTableNode parentNode = (DefaultMutableTreeTableNode) node.getParent();
				final Object parent = parentNode.getUserObject();
				final String field = (String) node.getUserObject();
				if (parent instanceof DBObject) {
					if (!entries.containsKey(parent)) {
						entries.put((DBObject) parent, new ArrayList<String>());
					}
					entries.get(parent).add(field);
				}
			}
		}
		RemoveFieldAction.getInstance().setEntries(entries);

		// Bind the best action to Delete key
		final ActionListener action;
		if (RemoveFieldAction.getInstance().isEnabled()) {
			action = RemoveFieldAction.getInstance();
		} else if (RemoveDocumentAction.getInstance().isEnabled()) {
			action = RemoveDocumentAction.getInstance();
		} else {
			action = null;
		}
		if (action != null) {
			this.registerKeyboardAction(action, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		}
	}
}