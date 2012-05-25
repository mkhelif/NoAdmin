package com.noadmin.view.beans.tree;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 * Extends the {@link JXTreeTable} and add some utility methods.
 *
 * @author mkhelif
 */
public class JTreeTable extends JXTreeTable {

	public JTreeTable(final TreeTableModel treeModel) {
		super(treeModel);

		// Register listener for usability:
		this.addMouseListener(new TreeTableMouseAdapter(this));
		this.addKeyListener(new TreeTableKeyAdapter(this));
	}

	/**
	 * Tells if a row is a leaf node or not.
	 * @param row the index of the row.
	 * @return TRUE if the node at the specified row is a leaf.
	 */
	public final boolean isLeaf(final int row) {
		return ((DefaultMutableTreeTableNode) this.getPathForRow(row).getLastPathComponent()).isLeaf();
	}
}