package com.noadmin.view.beans.tree;

import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;

/**
 * {@link TreeHandler} for Java {@link JXTreeTable}
 *
 * @author mkhelif
 */
final class JXTreeTableHandler implements TreeHandler {

	/**
	 * The wrapped {@link JXTreeTable}.
	 */
	private final JXTreeTable tree;

	public JXTreeTableHandler(final JXTreeTable tree) {
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