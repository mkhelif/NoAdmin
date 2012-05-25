package com.noadmin;

import javax.swing.UIManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.noadmin.controller.Controller;

/**
 * Bootstrap for the NoSQL database administration application.
 *
 * @author mkhelif
 */
public final class Bootstrap {

	/**
	 * The Spring configuration context XML.
	 */
	private static final String SPRING_CONFIGURATION = "/application.xml";

	/**
	 * Initialize Spring context and start {@link Controller} service.
	 */
	public static void main(final String[] args) {
		// Default settings
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {}

		// Initialize Spring context
		final ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_CONFIGURATION);

		// Start Controller service
		final Controller controller = context.getBean(Controller.class);
		controller.setContext(context);
		controller.startService();
	}
}