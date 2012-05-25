package com.noadmin.view.beans.tree;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * {@link TreeHandler} for Java {@link JTree}
 *
 * @author mkhelif
 */
final class JTreeHandler implements TreeHandler {

	/**
	 * The wrapped {@link JTree}.
	 */
	private final JTree tree;

	public JTreeHandler(final JTree tree) {
		this.tree = tree;
	}

	/**
	 * @see fr.mkhelif.noadmin.view.bean.tree.TreeHandler#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return tree.getRowCount();
	}

	/**
	 * @see fr.mkhelif.noadmin.view.bean.tree.TreeHandler#getPathForRow(int)
	 */
	@Override
	public TreePath getPathForRow(final int row) {
		return tree.getPathForRow(row);
	}

	/**
	 * @see fr.mkhelif.noadmin.view.bean.tree.TreeHandler#expandRow(int)
	 */
	@Override
	public void expandRow(final int row) {
		tree.expandRow(row);
	}

	/**
	 * @see fr.mkhelif.noadmin.view.bean.tree.TreeHandler#collapseRow(int)
	 */
	@Override
	public void collapseRow(final int row) {
		tree.collapseRow(row);
	}
}