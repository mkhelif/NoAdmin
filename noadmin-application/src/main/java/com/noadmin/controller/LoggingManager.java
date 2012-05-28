package com.noadmin.controller;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Manager in charge of configuring Log4J library.
 *
 * @author mkhelif
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class LoggingManager implements Service {

	/**
	 * Connection logger (noadmin.Connection).
	 */
	public static final String CONNECTION = "noadmin.Connection";

	/**
	 * Configuration logger (noadmin.Configuration).
	 */
	public static final String CONFIGURATION = "noadmin.Configuration";

	/**
	 * Plugins logger (noadmin.Plugins).
	 */
	public static final String PLUGINS = "noadmin.Plugins";

	/**
	 * Initialize the service and starts it.
	 */
	public void startService() {
		final Logger root = Logger.getRootLogger();
		root.setLevel(Level.WARN);
		try {
			root.addAppender(new FileAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN), "logs" + File.separator + "noadmin.log"));
		} catch (final IOException e) {
			System.err.println("Can not initialize logging system: " + e.getMessage());
		}
	}

	/**
	 * Stop the service and free all used resources.
	 */
	public void stopService() {
		// Nothing to do
	}
}