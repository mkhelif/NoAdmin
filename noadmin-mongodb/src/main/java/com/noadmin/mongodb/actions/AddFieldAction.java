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
import com.noadmin.mongodb.content.collection.CollectionTree;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.view.beans.ListSelectionModel;

/**
 * Remove the selected documents from the {@link DBCollection}.
 *
 * @author mkhelif
 */
public final class AddFieldAction extends AbstractMongoDBAction implements ListSelectionListener {

	private static final AddFieldAction INSTANCE = new AddFieldAction();
	public static AddFieldAction getInstance() { return INSTANCE; }

	/**
	 * List of documents which will hold the new field.
	 */
	private List<DBObject> documents;

	private AddFieldAction() {
		super("mongodb.field.add", "property;add");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String field = getUniqueFieldName();
		for (final DBObject document : documents) {
			document.addField(field, "");
		}
	}

	private String getUniqueFieldName() {
		int index = 1;
		while (true) {
			boolean contains = false;
			for (final DBObject document : documents) {
				if (document.containsField("field_" + index)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				return "field_" + index;
			}
			index++;
		}
	}

	/**
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final ListSelectionModel model = (ListSelectionModel) e.getSource();
		final CollectionTree tree = (CollectionTree) model.getComponent();

		documents = new ArrayList<>();
		final int[] rows = tree.getSelectedRows();
		for (final int row : rows) {
			final TreePath path = tree.getPathForRow(row);
			final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) path.getLastPathComponent();

			if (node.getUserObject() instanceof DBObject) {
				documents.add((DBObject) node.getUserObject());
			} else {
				final DefaultMutableTreeTableNode parent = (DefaultMutableTreeTableNode) node.getParent();
				if (parent.getUserObject() instanceof DBObject) {
					documents.add((DBObject) parent.getUserObject());
				}
			}
		}

		this.setEnabled(CollectionUtils.isNotEmpty(documents));
	}
}