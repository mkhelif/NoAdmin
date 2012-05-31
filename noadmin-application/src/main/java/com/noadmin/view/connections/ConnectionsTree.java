package com.noadmin.view.connections;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.noadmin.model.ConfigurationElement;
import com.noadmin.view.actions.connections.AddConnectionAction;
import com.noadmin.view.actions.connections.ConnectAction;
import com.noadmin.view.actions.connections.DisconnectAction;
import com.noadmin.view.actions.connections.RemoveConnectionAction;
import com.noadmin.view.connections.node.AbstractNode;

/**
 * Tree of the defined database connections.
 *
 * @author mkhelif
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class ConnectionsTree extends JTree {

	@Autowired
	public ConnectionsTree(final ConnectionsTreeModel model) {
		model.setTree(this);

		// Tree settings
		this.setModel(model);
		this.setCellRenderer(new ConnectionsTreeRenderer());
		this.setRootVisible(false);
		this.setShowsRootHandles(true);
		this.expandRow(0);
		this.expandPath(new TreePath(((DefaultMutableTreeNode) this.getModel().getRoot()).getPath()));
		ToolTipManager.sharedInstance().registerComponent(this);

		// Register listeners
		//this.addTreeSelectionListener(ConnectAction.getInstance());
		//this.addTreeSelectionListener(DisconnectAction.getInstance());
		//this.addTreeSelectionListener(RemoveConnectionAction.getInstance());

		// Keystrokes mapping
		//this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "RemoveConnectionAction");
		//this.getActionMap().put("RemoveConnectionAction", RemoveConnectionAction.getInstance());

		// Popup menu handling
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent e) {
				if (!e.isPopupTrigger()) {
					return;
				}
				ConnectionsTree.this.mouseReleased(e);
			}
		});
	}

	/**
	 * The mouse button has been released.
	 * @param e the event of the click release.
	 */
	public void mouseReleased(final MouseEvent e) {
		final JPopupMenu menu = new JPopupMenu();

		// Update pop-up menu with contextual actions:
		final TreePath[] paths = this.getSelectionPaths();
		if (paths != null && paths.length == 1) {
			final AbstractNode<?> node = (AbstractNode<?>) paths[0].getLastPathComponent();
			node.addMenuItems(menu);
		}

		// Default actions:
		menu.add(ConnectAction.getInstance().newMenu());
		menu.addSeparator();
		menu.add(AddConnectionAction.getInstance().newMenu());
		menu.add(DisconnectAction.getInstance().newMenu());
		menu.addSeparator();
		menu.add(RemoveConnectionAction.getInstance().newMenu());

		// Show menu :
		menu.show(this, e.getX(), e.getY());
	}

	/**
	 * Returns the selected element of the right type.
	 * @param <T> the type of element to returns.
	 * @param type the type of element to returns.
	 * @return the selected element of the right type or null if no selection.
	 */
	public <T extends ConfigurationElement> T getSelected(final Class<T> type) {
		// Ignore empty or multiple selections:
		final TreePath[] paths = this.getSelectionPaths();
		if (paths == null || paths.length != 1) {
			return null;
		}

		// Ignore wrong type selection:
		for (final Object entry : paths[0].getPath()) {
			final Object item = ((AbstractNode<?>) entry).getUserObject();
			if (type.isInstance(item)) {
				return type.cast(item);
			}
		}
		return null;
	}
}