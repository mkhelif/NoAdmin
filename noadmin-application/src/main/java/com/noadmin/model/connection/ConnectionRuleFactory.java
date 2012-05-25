package com.noadmin.model.connection;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.xml.sax.Attributes;

import com.noadmin.controller.Controller;

/**
 * Connection factory used by digester.
 *
 * @author mkhelif
 */
public final class ConnectionRuleFactory extends AbstractObjectCreationFactory {

	/**
	 * @see org.apache.commons.digester.AbstractObjectCreationFactory#createObject(org.xml.sax.Attributes)
	 */
	@Override
	public Connection createObject(final Attributes attrs) throws Exception {
		final Connection connection = getFactory().newConnection(attrs.getValue("type"));
		connection.loadXML(attrs);

		return connection;
	}

	/**
	 * Retrieve the connection factory instance.
	 */
	private ConnectionFactory getFactory() {
		return Controller.getInstance().getBean(ConnectionFactory.class);
	}
}