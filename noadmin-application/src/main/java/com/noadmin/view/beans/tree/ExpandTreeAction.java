package com.noadmin.view.beans.tree;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import org.jdesktop.swingx.JXTreeTable;

import com.noadmin.view.actions.DefaultAction;

/**
 * Expand all nodes of a tree.
 *
 * @author mkhelif
 */
public final class ExpandTreeAction extends DefaultAction {

	private static Map<Object, ExpandTreeAction> INSTANCES = new HashMap<>();

	public static ExpandTreeAction getInstance(final JTree tree) {
		if (!INSTANCES.containsKey(tree)) {
			INSTANCES.put(tree, new ExpandTreeAction(tree));
		}
		return INSTANCES.get(tree);
	}

	public static ExpandTreeAction getInstance(final JXTreeTable tree) {
		if (!INSTANCES.containsKey(tree)) {
			INSTANCES.put(tree, new ExpandTreeAction(tree));
		}
		return INSTANCES.get(tree);
	}

	/**
	 * The tree that is expanded.
	 */
	private final TreeHandler tree;

	private ExpandTreeAction(final JTree tree) {
		this(new JTreeHandler(tree));
	}

	private ExpandTreeAction(final JXTreeTable tree) {
		this(new JXTreeTableHandler(tree));
	}

	private ExpandTreeAction(final TreeHandler tree) {
		super("action.expand.all");
		this.setEnabled(true);
		this.tree = tree;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		for (int row = 0 ; row < tree.getRowCount() ; row++) {
			expandAll(row);
		}
	}

	/**
	 * Recursively expand all the nodes of a path.
	 */
	private void expandAll(final int row) {
		tree.expandRow(row);

		final TreeNode node = (TreeNode) tree.getPathForRow(row).getLastPathComponent();
		for (int i = 1 ; i <= node.getChildCount() ; i++) {
			expandAll(row + i);
		}
	}
}