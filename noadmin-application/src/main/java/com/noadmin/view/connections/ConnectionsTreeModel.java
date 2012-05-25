package com.noadmin.view.connections;

import javax.swing.tree.DefaultTreeModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.view.connections.node.RootNode;

/**
 * Model of the connections tree.
 *
 * @author mkhelif
 */
@Component
public final class ConnectionsTreeModel extends DefaultTreeModel {

	@Autowired
	public ConnectionsTreeModel(final RootNode root) {
		super(root);
		root.setModel(this);
	}

	void setTree(final ConnectionsTree tree) {
		final RootNode root = (RootNode) this.getRoot();
		root.setTree(tree);
	}
}