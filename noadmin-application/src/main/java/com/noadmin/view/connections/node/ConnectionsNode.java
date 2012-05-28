package com.noadmin.view.connections.node;

import javax.swing.Icon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.controller.configuration.ConfigurationListener;
import com.noadmin.controller.configuration.ConfigurationManager;
import com.noadmin.model.ConfigurationElement;
import com.noadmin.model.connection.Connection;
import com.noadmin.view.beans.DefaultMessageSource;
import com.noadmin.view.beans.IconManager;

/**
 * Node that displays as children all the configured connections.
 *
 * @author mkhelif
 */
@Component
public final class ConnectionsNode extends AbstractNode<ConfigurationManager> implements ConfigurationListener {

	/**
	 * The internationalization messages.
	 */
	private final DefaultMessageSource i18n;

	/**
	 * The manager of icons.
	 */
	private final IconManager icon;

	/**
	 * The connections node factory.
	 */
	private final ConnectionNodeFactory factory;

	/**
	 * Build the connections tree.
	 */
	@Autowired
	public ConnectionsNode(final ConfigurationManager manager, final DefaultMessageSource i18n, final IconManager icon, final ConnectionNodeFactory factory) {
		super(manager, icon);
		this.i18n = i18n;
		this.icon = icon;
		this.factory = factory;

		// Register listener
		manager.addConfigurationListener(this);
	}

	/**
	 * @see fr.mkhelif.noadmin.view.tree.node.AbstractNode#getLabel()
	 */
	@Override
	public String getLabel() {
		return i18n.getMessage("connections");
	}

	/**
	 * @see fr.mkhelif.noadmin.view.tree.node.AbstractNode#getIcon()
	 */
	@Override
	public Icon getIcon() {
		return icon.getIcon("connections");
	}

	/**
	 * A new connection has been added to the configuration, display it
	 * in the tree.
	 * @param connection the new connection.
	 */
	public final void connectionAdded(final Connection connection) {
		this.add(factory.newConnectionNode(connection));
		if (this.getChildCount() == 1) {
			getModel().nodeStructureChanged(this);
		} else {
			getModel().nodesWereInserted(this, new int[] { this.getChildCount() - 1 });
		}
		this.expand();
	}

	/**
	 * A connection has been removed from the configuration.
	 * @param connection the removed connection.
	 */
	public final void connectionRemoved(final Connection connection) {
		AbstractNode<?> node = null;
		int index = -1;
		for (int i = 0 ; i < this.getChildCount() ; i++) {
			final AbstractNode<ConfigurationElement> child = this.getChild(i);
			if (child.getUserObject() == connection) {
				node = child;
				index = i;
				break;
			}
		}

		if (index == -1 || node == null) {
			return;
		}
		this.remove(node);
		getModel().nodesWereRemoved(this, new int[] { index }, new Object[] { node });
	}
}