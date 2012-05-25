package com.noadmin.view.connections;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.noadmin.view.connections.node.AbstractNode;

/**
 * Renderer for the connections tree.
 *
 * @author mkhelif
 */
public final class ConnectionsTreeRenderer extends DefaultTreeCellRenderer {

	/**
     * Sets the value of the current tree cell to value.
     * @return	the Component that the renderer uses to draw the value.
     */
	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		final AbstractNode<?> node = (AbstractNode<?>) value;
		if (node != null) {
			this.setText(node.getLabel());
			this.setIcon(node.getIcon());
		}

		return this;
	}
}