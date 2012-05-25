package com.noadmin.mongodb.actions;

import com.noadmin.view.actions.DefaultAction;
import com.noadmin.view.beans.DefaultMessageSource;

/**
 * Abstract class for all MongoDB actions.
 *
 * @author mkhelif
 */
public abstract class AbstractMongoDBAction extends DefaultAction {

	private DefaultMessageSource i18n;

	public AbstractMongoDBAction(final String name) {
		this(name, null);
	}

	public AbstractMongoDBAction(final String name, final String icon) {
		super(name, icon);
	}

	/**
	 * @see com.noadmin.view.actions.DefaultAction#i18n(java.lang.String)
	 */
	@Override
	protected String i18n(final String key, final Object... parameters) {
		if (i18n == null) {
			i18n = new DefaultMessageSource("mongodb");
		}
		return i18n.getMessage(key, parameters);
	}
}