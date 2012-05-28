package com.noadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Main controller of the application. It is in charge of starting/stopping all
 * the services.
 *
 * @author mkhelif
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class Controller {

	private static Controller instance;
	public static Controller getInstance() { return instance; }

	/**
	 * List of services of the application.
	 */
	@Autowired
	private List<Service> services;

	/**
	 * The Spring context of the application.
	 */
	private ApplicationContext context;

	public Controller() {
		if (Controller.instance == null) {
			Controller.instance = this;
		} else {
			throw new RuntimeException("Application bootstrap already loaded.");
		}
	}

	/**
	 * Initialize the service and starts it.
	 */
	public void startService() {
		for (final Service service : services) {
			service.startService();
		}
	}

	/**
	 * Stop the service and free all used resources.
	 */
	public void stopService() {
		for (final Service service : services) {
			service.stopService();
		}
		System.exit(0);
	}

	/**
	 * Update the Spring application context.
	 */
	public void setContext(final ApplicationContext context) {
		if (this.context != null) {
			throw new RuntimeException("Application context already loaded.");
		}
		this.context = context;
	}

	/**
	 * Retrieve a bean instance by its name.
	 * @param name the name of the bean.
	 * @return the bean instance from the context.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(final String name) {
		return (T) context.getBean(name);
	}

	/**
	 * Retrieve a bean instance by its type.
	 * @param type the type of bean.
	 * @return the bean instance from the context.
	 */
	public <T> T getBean(final Class<T> type) {
		return context.getBean(type);
	}
}