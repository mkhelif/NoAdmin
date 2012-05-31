package com.noadmin.view.content;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.view.connections.ConnectionsTree;
import com.noadmin.view.connections.node.AbstractNode;

/**
 * Main panel of the application. It is used to display informations about the selected
 * element.
 *
 * @author mkhelif
 */
@Component
public final class ContentsPanel extends JPanel implements TreeSelectionListener {

	/**
	 * Map between the key and the {@link ElementPanel}.
	 */
	private final Map<String, ElementPanel<Object>> panels;

	/**
	 * Map between the key of an {@link ElementPanel} and its state (initialized or not).
	 */
	private final Map<String, Boolean> initialized;

	@Autowired
	public ContentsPanel(final Map<String, ElementPanel<Object>> panels) {
		super(new CardLayout());
		this.initialized = new HashMap<>(panels.size());
		this.panels = panels;

		// Layout panels
		this.add("EMPTY", new JPanel());
		for (final Entry<String, ElementPanel<Object>> panel : panels.entrySet()) {
			this.add(panel.getKey(), panel.getValue());
			initialized.put(panel.getKey(), false);
		}
	}

	/**
	 * The selection in the tree has changed, update the displayed informations.
	 * @param evt the event that changes the tree selection.
	 */
	@Override
	public void valueChanged(final TreeSelectionEvent evt) {
		// Ignore empty selection
		final ConnectionsTree tree = (ConnectionsTree) evt.getSource();
		final TreePath[] paths = tree.getSelectionPaths();
		if (ArrayUtils.isEmpty(paths)) {
			return;
		}

		// Update or initialize the panel
		final AbstractNode<?> node = (AbstractNode<?>) paths[0].getLastPathComponent();
		final String key = node.getClass().getName();
		final ElementPanel<Object> panel = panels.get(key);
		if (panel == null) {
			return;
		}

		if (initialized.get(key) == Boolean.FALSE) {
			panel.initialize();
			initialized.put(key, true);
		}
		panel.setContent(node.getUserObject());

		// Show the panel
		getLayout().show(this, key);
	}

	/**
	 * @see java.awt.Container#getLayout()
	 */
	@Override
	public CardLayout getLayout() {
		return (CardLayout) super.getLayout();
	}
}