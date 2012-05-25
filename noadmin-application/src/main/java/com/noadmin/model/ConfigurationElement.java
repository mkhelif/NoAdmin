package com.noadmin.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

/**
 * Base interface for all the elements of the configuration.
 *
 * @author mkhelif
 */
public interface ConfigurationElement {

	/**
	 * @return the display name of this connection.
	 */
	String getName();

	/**
	 * Update the display name of this connection.
	 * @param name the new name of the connection.
	 */
	void setName(final String name);

	/**
	 * Add a new listener to the configuration element.
	 * @param listener the new listener.
	 */
	void addConfigurationElementListener(final ConfigurationElementListener listener);

	/**
	 * Remove a listener from the configuration element.
	 * @param listener the listener to remove.
	 */
	void removeConfigurationElementListener(final ConfigurationElementListener listener);

	/**
	 * Write the connection as XML in order to save it.
	 * @param document the XML document that will be stored.
	 * @param parent the parent node of the connection.
	 */
	void writeXML(final Document document, final Element parent);

	/**
	 * Load the connection settings from the XML attributes.
	 * @param attributes the XML attributes.
	 */
	void loadXML(final Attributes attributes);
}