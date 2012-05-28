package com.noadmin.view.beans.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.tree.TreePath;

/**
 * Listener for {@link MouseEvent} on the {@link JTreeTable}.
 * This is used to expand rows on double click.
 *
 * @author mkhelif
 */
final class TreeTableMouseAdapter extends MouseAdapter {

	/**
	 * The tree to handle.
	 */
	private final JTreeTable tree;

	public TreeTableMouseAdapter(final JTreeTable tree) {
		this.tree = tree;
	}

	/**
	 * Handle double-click.
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(final MouseEvent e) {
		if (e.getClickCount() != 2) {
			return;
		}

		final int row = tree.getSelectedRow();
		final TreePath clicked = tree.getPathForLocation(e.getX(), e.getY());
		final TreePath selected = tree.getPathForRow(row);
		if (clicked != null && clicked.equals(selected)) {
			if (tree.isExpanded(row)) {
				tree.collapseRow(row);
			} else {
				tree.expandRow(row);
			}
		}
	}
}