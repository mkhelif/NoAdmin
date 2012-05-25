package com.noadmin.view.beans.tree;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import com.noadmin.util.DigitalUnit;

/**
 * Renderer (tree, table) used to render a Byte value.
 * Example: 1824536 (bytes) -> 1,82 MB.
 *
 * @author mkhelif
 */
public final class ByteRenderer implements TreeCellRenderer, TableCellRenderer {

	/**
	 * The wrapped tree renderer.
	 */
	private final DefaultTreeCellRenderer treeRenderer = new DefaultTreeCellRenderer();

	/**
	 * The wrapped table renderer.
	 */
	private final DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();

	/**
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
		treeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		treeRenderer.setHorizontalAlignment(JLabel.RIGHT);
		treeRenderer.setText(format((Number) value));

		return treeRenderer;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		tableRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		tableRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tableRenderer.setText(format((Number) value));

		return tableRenderer;
	}

	/**
	 * Format the byte value as String.
	 */
	private String format(final Number value) {
		if (value == null) {
			return null;
		}
		final DigitalUnit unit = DigitalUnit.getBestMatching(value.doubleValue());
		return unit.convert(value.doubleValue(), DigitalUnit.UNIT) + " " + unit.getSymbol() + "B";
	}
}