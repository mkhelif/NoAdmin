package com.noadmin.view.beans.tree;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;

import com.noadmin.view.actions.DefaultAction;

/**
 * Expand all nodes of a tree.
 *
 * @author mkhelif
 */
public final class CollapseTreeAction extends DefaultAction {

	private static Map<Object, CollapseTreeAction> INSTANCES = new HashMap<>();

	public static CollapseTreeAction getInstance(final JTree tree) {
		if (!INSTANCES.containsKey(tree)) {
			INSTANCES.put(tree, new CollapseTreeAction(tree));
		}
		return INSTANCES.get(tree);
	}

	public static CollapseTreeAction getInstance(final JXTreeTable tree) {
		if (!INSTANCES.containsKey(tree)) {
			INSTANCES.put(tree, new CollapseTreeAction(tree));
		}
		return INSTANCES.get(tree);
	}

	/**
	 * The tree that is expanded.
	 */
	private final TreeHandler tree;

	private CollapseTreeAction(final JTree tree) {
		this(new JTreeHandler(tree));
	}

	private CollapseTreeAction(final JXTreeTable tree) {
		this(new JXTreeTableHandler(tree));
	}

	private CollapseTreeAction(final TreeHandler tree) {
		super("action.collapse.all");
		this.setEnabled(true);
		this.tree = tree;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		for (int row = 0 ; row < tree.getRowCount() ; row++) {
			collapseAll(row);
		}
	}

	/**
	 * Recursively collapse all the nodes of a path.
	 */
	private void collapseAll(final int row) {
		final TreePath path = tree.getPathForRow(row);
		if (path == null) {
			return;
		}

		final TreeNode node = (TreeNode) path.getLastPathComponent();
		for (int i = 1 ; i <= node.getChildCount() ; i++) {
			collapseAll(row + i);
		}

		tree.collapseRow(row);
	}
}