package com.noadmin.view.beans;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Implementation of {@link MessageSource} which provides helper methods.
 *
 * @author mkhelif
 */
public final class DefaultMessageSource extends ReloadableResourceBundleMessageSource {

	public DefaultMessageSource() {
		super();
	}

	public DefaultMessageSource(final String basename) {
		super();
		this.setBasename(basename);
		this.setDefaultEncoding("UTF-8");
	}

	/**
	 * Resolves the given message code as key in the retrieved bundle files,
	 * @returns the value found in the bundle as-is (without MessageFormat parsing).
	 */
	public final String getMessage(final String code) {
		return resolveCodeWithoutArguments(code, Locale.getDefault());
	}

	/**
	 * Try to resolve the message.
	 * @param code the code to lookup up.
	 * @param args array of arguments that will be filled in for params within
	 *        the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
	 *        or <code>null</code> if none.
	 * @return the resolved message if the lookup was successful.
	 */
	public final String getMessage(final String code, final Object... args) {
		return getMessage(code, args, Locale.getDefault());
	}
}