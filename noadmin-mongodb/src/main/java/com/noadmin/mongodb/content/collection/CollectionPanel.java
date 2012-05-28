package com.noadmin.mongodb.content.collection;

import java.awt.GridBagConstraints;
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
	 * Update the selection related actions.
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final int[] rows = tree.getSelectedRows();

		boolean onlyFields = rows.length > 1;
		final List<DBObject> documents = new ArrayList<>();
		final Map<DBObject, List<String>> entries = new HashMap<>();
		for (final int row : rows) {
			final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) tree.getPathForRow(row).getLastPathComponent();

			if (node.getUserObject() instanceof DBObject) {
				documents.add((DBObject) node.getUserObject());
				onlyFields = false;
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

		this.registerKeyboardAction(
			(onlyFields ? RemoveFieldAction.getInstance() : RemoveDocumentAction.getInstance()),
			KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
			JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
		);
	}
}