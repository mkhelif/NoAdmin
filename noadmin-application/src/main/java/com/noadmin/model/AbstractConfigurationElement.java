package com.noadmin.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

/**
 * Abstract class of an element of the configuration.
 *
 * @author mkhelif
 */
public abstract class AbstractConfigurationElement implements ConfigurationElement {

	/**
	 * List of configuration listener listening for any changes on this
	 * element.
	 */
	private final transient List<ConfigurationElementListener> listeners;

	/**
	 * The display name of the element.
	 */
	private String name;

	protected AbstractConfigurationElement() {
		listeners = new ArrayList<ConfigurationElementListener>();
	}

	/**
	 * @return the display name of this connection.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Update the display name of this connection.
	 * @param name the new name of the connection.
	 */
	public final void setName(final String name) {
		if (!StringUtils.equals(this.name, name)) {
			this.name = name;
			this.fireElementUpdated();
		}
	}

	/**
	 * Add a new listener to the configuration element.
	 * @param listener the new listener.
	 */
	public final void addConfigurationElementListener(final ConfigurationElementListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a listener from the configuration element.
	 * @param listener the listener to remove.
	 */
	public final void removeConfigurationElementListener(final ConfigurationElementListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Fire an update event to all the listeners.
	 */
	protected final void fireElementUpdated() {
		for (final ConfigurationElementListener listener : listeners) {
			listener.elementUpdated();
		}
	}

	/**
	 * Write the XML attributes of the connection.
	 * @param element the element to add attributes on.
	 * @return the element.
	 */
	protected Element writeAttributes(final Element element) {
		element.setAttribute("name", name);
		return element;
	}

	/**
	 * Load the connection settings from the XML attributes.
	 * @param attributes the XML attributes.
	 */
	public void loadXML(final Attributes attributes) {
		this.name = attributes.getValue("name");
	}
}