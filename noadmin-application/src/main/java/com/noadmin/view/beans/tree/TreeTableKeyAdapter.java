package com.noadmin.view.beans.tree;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.tree.TreePath;

/**
 * Listen for {@link JTreeTable} {@link KeyEvent} to process expand/collapse.
 *
 * @author mkhelif
 */
final class TreeTableKeyAdapter extends KeyAdapter {

	/**
	 * The processed tree.
	 */
	private final JTreeTable tree;

	public TreeTableKeyAdapter(final JTreeTable tree) {
		this.tree = tree;
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		if (tree.getSelectedRowCount() <= 0) {
			return;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			processCollapse(tree.getSelectedRow());
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			processExpand(tree.getSelectedRow());
		}
	}

	/**
	 * Expand the row or select the first child if already expanded.
	 * @param row the index of the row.
	 */
	private void processExpand(final int row) {
		if (tree.isLeaf(row)) {
			return;
		}
		if (tree.isExpanded(row)) {
			tree.getSelectionModel().setSelectionInterval(row + 1, row + 1);
		} else {
			tree.expandRow(row);
		}
	}

	/**
	 * Collapse the row of select the parent if already collapsed.
	 * @param row the index of the row.
	 */
	private void processCollapse(final int row) {
		if (tree.isCollapsed(row)) {
			final TreePath path = tree.getPathForRow(row);
			if (path.getParentPath().getLastPathComponent() != tree.getTreeTableModel().getRoot()) {
				final int newRow = tree.getRowForPath(path.getParentPath());
				tree.getSelectionModel().setSelectionInterval(newRow, newRow);
			}
		} else {
			tree.collapseRow(row);
		}
	}
}