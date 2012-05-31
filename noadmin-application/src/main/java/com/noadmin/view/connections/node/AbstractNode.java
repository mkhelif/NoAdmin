package com.noadmin.view.connections.node;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.noadmin.controller.Controller;
import com.noadmin.model.ConfigurationElement;
import com.noadmin.view.beans.IconManager;
import com.noadmin.view.connections.ConnectionsTree;
import com.noadmin.view.connections.ConnectionsTreeModel;

/**
 * Abstract class for all the nodes in the tree.
 *
 * @param <T> the type of element this node handles.
 * @author mkhelif
 */
public abstract class AbstractNode<T> extends DefaultMutableTreeNode {

	/**
	 * The icon manager instance.
	 */
	private final IconManager manager;

	/**
	 * Create a new node attached to the element.
	 * @param element the element to display in the tree.
	 */
	public AbstractNode(final T element) {
		this(element, Controller.getInstance().getBean(IconManager.class));
	}

	protected AbstractNode(final T element, final IconManager manager) {
		super(element);
		this.manager = manager;
	}

	/**
	 * @return the object associated with this node.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final T getUserObject() {
		return (T) super.getUserObject();
	}

	/**
	 * The label of the object to display as node's text.
	 * @return the label of the node.
	 */
	public abstract String getLabel();

	/**
	 * @return the icon to display for this node.
	 */
	public abstract Icon getIcon();

	/**
	 * Load an icon from the class path.
	 * @param name the name of the icon to load.
	 * @return the loaded icon or null if not found.
	 */
	protected final Icon loadIcon(final String name) {
		return manager.getIcon(name);
	}

	/**
	 * Add the menu actions concerning this node type to the contextual
	 * pop-up menu.
	 * @param menu the contextual pop-up menu.
	 */
	public void addMenuItems(final JPopupMenu menu) {
		// No menu to add
	}

	/**
	 * @return the tree to which this node belongs to.
	 */
	public ConnectionsTree getTree() {
		return ((RootNode) this.getRoot()).getTree();
	}

	/**
	 * @return the tree model to which this node belongs to.
	 */
	public ConnectionsTreeModel getModel() {
		return ((RootNode) this.getRoot()).getModel();
	}

	/**
	 * @param <U> the type of configuration element the child node handles.
	 * @param index the index of the child.
	 * @return the child node.
	 */
	@SuppressWarnings("unchecked")
	public final <U extends ConfigurationElement> AbstractNode<U> getChild(final int index) {
		return (AbstractNode<U>) super.getChildAt(index);
	}

	/**
	 * Remove all the children.
	 */
	@Override
	public final void removeAllChildren() {
		super.removeAllChildren();
		this.getModel().nodeStructureChanged(this);
	}

	/**
	 * Expand the node.
	 */
	public final void expand() {
		this.getTree().expandPath(new TreePath(this.getPath()));
	}
}