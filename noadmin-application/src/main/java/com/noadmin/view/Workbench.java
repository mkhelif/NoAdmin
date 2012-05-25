package com.noadmin.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.noadmin.controller.Controller;
import com.noadmin.controller.Service;
import com.noadmin.view.beans.IconManager;
import com.noadmin.view.connections.ConnectionsTree;
import com.noadmin.view.content.ContentsPanel;

/**
 * Main window of the application.
 *
 * @author mkhelif
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Workbench extends JFrame implements Service {

	/**
	 * The {@link Controller} instance.
	 */
	private final Controller controller;

	/**
	 * The manager of icons.
	 */
	private final IconManager manager;

	/**
	 * The main menu bar of the application.
	 */
	private final Menu menu;

	/**
	 * The tree of connections.
	 */
	private final ConnectionsTree tree;

	/**
	 * The panel that display content specific to the tree selection.
	 */
	private final ContentsPanel contents;

	/**
	 * Information panel that displays any contextual information.
	 */
	private final InformationsPanel informations;

	@Autowired
	public Workbench(final Controller controller, final IconManager manager, final Menu menu, final ConnectionsTree tree, final ContentsPanel contents, final InformationsPanel informations) {
		this.controller = controller;
		this.manager = manager;
		this.menu = menu;
		this.tree = tree;
		this.contents = contents;
		this.informations = informations;
	}

	/**
	 * Initialize the service and starts it.
	 */
	public void startService() {
		// Initialize view components
		menu.initialize();
		tree.addTreeSelectionListener(contents);

		final JSplitPane split = new JSplitPane();
		split.setLeftComponent(new JScrollPane(tree));
		split.setRightComponent(contents);
		split.setDividerSize(2);
		split.setDividerLocation(200);

		// Layout components :
		this.setJMenuBar(menu);
		this.setLayout(new BorderLayout());
		this.add(new ToolBar(), BorderLayout.NORTH);
		this.add(split, BorderLayout.CENTER);
		this.add(informations, BorderLayout.SOUTH);

		// Window settings :
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				controller.stopService();
			}
		});
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setIconImage(manager.getIcon("icon").getImage());
		this.setTitle("NoSQL Admin");
		this.setSize(1024, 780);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Stop the service and free all used resources.
	 */
	public void stopService() {
		this.dispose();
	}
}