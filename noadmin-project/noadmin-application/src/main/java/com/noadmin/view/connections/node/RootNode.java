package com.noadmin.view.connections.node;

import javax.swing.Icon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noadmin.model.ConfigurationElement;
import com.noadmin.view.connections.ConnectionsTree;
import com.noadmin.view.connections.ConnectionsTreeModel;

/**
 * Root node of the tree. There is no object attached to the node.
 *
 * @author mkhelif
 */
@Component
public class RootNode extends AbstractNode<ConfigurationElement> {

	/**
	 * The connections tree.
	 */
	private ConnectionsTree tree;

	/**
	 * The model of the tree.
	 */
	private ConnectionsTreeModel model;

	@Autowired
	public RootNode(final ConnectionsNode node) {
		super(null, null);
		this.add(node);
	}

	public void setTree(final ConnectionsTree tree) {
		this.tree = tree;
	}

	public void setModel(final ConnectionsTreeModel model) {
		this.model = model;
	}

	/**
	 * @return null. No label for root node.
	 */
	@Override
	public final String getLabel() {
		return null;
	}

	/**
	 * @return null. No icon for root node.
	 */
	@Override
	public final Icon getIcon() {
		return null;
	}

	/**
	 * @return the tree model to which this node belongs to.
	 */
	@Override
	public final ConnectionsTreeModel getModel() {
		return model;
	}

	/**
	 * @return the tree to which this node belongs to.
	 */
	@Override
	public final ConnectionsTree getTree() {
		return tree;
	}
}