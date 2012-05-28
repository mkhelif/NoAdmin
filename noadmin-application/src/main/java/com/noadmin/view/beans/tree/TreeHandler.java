package com.noadmin.view.beans.tree;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;

/**
 * Interface used to put in common {@link JTree} and {@link JXTreeTable}.
 *
 * @author mkhelif
 */
interface TreeHandler {

	/**
	 * @return the number of viewable nodes.
	 */
	int getRowCount();

	/**
	 * @return the path for the specified row.
	 */
	TreePath getPathForRow(final int row);

	/**
	 * Ensures that the node in the specified row is expanded and viewable.
	 */
	void expandRow(final int row);

	/**
	 * Ensures that the node in the specified row is collapsed.
	 */
	void collapseRow(final int row);
}