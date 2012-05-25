package com.noadmin.mongodb.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import com.mongodb.DBCollection;
import com.noadmin.controller.Controller;
import com.noadmin.mongodb.content.collection.CollectionTree;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.view.beans.GUIHandler;
import com.noadmin.view.beans.ListSelectionModel;

/**
 * Remove the selected documents from the {@link DBCollection}.
 *
 * @author mkhelif
 */
public final class RemoveDocumentAction extends AbstractMongoDBAction implements ListSelectionListener {

	private static final RemoveDocumentAction INSTANCE = new RemoveDocumentAction();
	public static RemoveDocumentAction getInstance() { return INSTANCE; }

	/**
	 * The {@link Collection} to remove the {@link DBObject} from.
	 */
	private Collection collection;

	/**
	 * The {@link DBObject} document to remove;
	 */
	private List<DBObject> documents;

	private RemoveDocumentAction() {
		super("mongodb.document.remove", "document;remove");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// Ask confirmation:
		final boolean choice = Controller.getInstance().getBean(GUIHandler.class).confirm(
			i18n("mongodb.document.remove"),
			i18n("mongodb.document.remove.confirm")
		);
		if (!choice) {
			return;
		}

		// Delete the documents:
		for (final DBObject document : documents) {
			collection.remove(document);
		}
	}

	public void setCollection(final Collection collection) {
		this.collection = collection;
		this.update();
	}

	private void update() {
		this.setEnabled(collection != null && CollectionUtils.isNotEmpty(documents));
	}

	/**
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final ListSelectionModel model = (ListSelectionModel) e.getSource();
		final CollectionTree tree = (CollectionTree) model.getComponent();

		documents = new ArrayList<>();
		final int[] rows = tree.getSelectedRows();
		for (final int row : rows) {
			final TreePath path = tree.getPathForRow(row);
			if (path.getPathCount() == 2) {
				final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) path.getLastPathComponent();
				documents.add((DBObject) node.getUserObject());
			} else {
				documents = null;
				break;
			}
		}

		this.update();
	}
}