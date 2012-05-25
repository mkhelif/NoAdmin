package com.noadmin.controller;


/**
 * Interface used by all the services of the application.
 *
 * @author mkhelif
 */
public interface Service {

	/**
	 * Initialize the service and starts it.
	 */
	void startService();

	/**
	 * Stop the service and free all used resources.
	 */
	void stopService();
}