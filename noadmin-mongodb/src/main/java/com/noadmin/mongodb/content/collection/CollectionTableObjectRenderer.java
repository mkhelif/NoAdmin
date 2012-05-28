package com.noadmin.mongodb.content.collection;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import com.noadmin.controller.Controller;
import com.noadmin.mongodb.model.Collection;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.mongodb.model.DBObjectList;
import com.noadmin.mongodb.model.DBReference;
import com.noadmin.view.beans.IconManager;

/**
 * Renderer of the {@link Collection} tree..
 *
 * @author mkhelif
 */
public final class CollectionTableObjectRenderer extends DefaultTreeCellRenderer {

	/**
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		final DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) value;
		final IconManager manager = Controller.getInstance().getBean(IconManager.class);
		if (node.getUserObject() instanceof DBObject) {
			final DBObject object = (DBObject) node.getUserObject();

			this.setIcon(manager.getIcon("node_document"));
			this.setText(getName(object));
		} else if (node.getUserObject() instanceof DBObjectList) {
			final DBObjectList list = (DBObjectList) node.getUserObject();

			this.setIcon(manager.getIcon("node_list"));
			this.setText(list.getName());
		} else if (node.getUserObject() instanceof DBReference) {
			final DBReference reference = (DBReference) node.getUserObject();

			this.setIcon(manager.getIcon("node_reference"));
			this.setText(reference.getTargetObject());
		} else {
			this.setIcon(manager.getIcon("property"));
			this.setText(String.valueOf(node.getUserObject()));
		}
		return this;
	}

	/**
	 * Retrieve the name of a {@link DBObject}.
	 * @param object the DB object.
	 * @return the name to display for the object.
	 */
	private String getName(final DBObject object) {
		final String name = object.getName();
		if (StringUtils.isNotEmpty(name)) {
			return name;
		}
		if (object.containsField("name")) {
			if (object.containsField("ns")) {
				return object.get("ns") + "." + object.get("name");
			} else {
				return String.valueOf(object.get("name"));
			}
		}
		if (object.containsField("_id")) {
			return String.valueOf(object.get("_id"));
		}
		return String.valueOf(object);
	}
}