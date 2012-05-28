package com.noadmin.view.actions;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import com.noadmin.controller.Controller;
import com.noadmin.view.beans.CompoundIcon;
import com.noadmin.view.beans.DefaultMessageSource;
import com.noadmin.view.beans.IconManager;

/**
 * Base action used to assign localized text and image to an action.
 *
 * @author mkhelif
 */
public abstract class DefaultAction extends AbstractAction {

	/**
	 * The key for the disabled icon property.
	 */
    private static final String DISABLED_SMALL_ICON = "DisabledSmallIcon";

	/**
	 * The internationalization messages manager.
	 */
	private final DefaultMessageSource i18n;

	/**
	 * The manager of icons.
	 */
	private final IconManager icon;

	/**
	 * Create a new action with no icon.
	 * @param name the i18n key of the action label
	 */
	protected DefaultAction(final String name) {
		this(name, null);
	}

	/**
	 * Create a new action.
	 * @param name the i18n key of the action label
	 * @param icon the path of the icon.
	 */
	protected DefaultAction(final String name, final String icon) {
		super();

		// Retrieve beans
		final Controller controller = Controller.getInstance();
		this.i18n = controller.getBean(DefaultMessageSource.class);
		this.icon = controller.getBean(IconManager.class);

		// Settings :
		this.putValue(NAME, i18n(name));
		this.putValue(SHORT_DESCRIPTION, i18n(name));
		if (icon != null) {
			this.putValue(SMALL_ICON, getIcon(icon));
			this.putValue(DISABLED_SMALL_ICON, getDisabledIcon(icon));
		}
		this.setEnabled(false);
	}

	/**
	 * Compute the icon of the action.
	 * @param key the key of the icon: <path>;<tick>
	 * @return the image.
	 */
	private final Icon getIcon(final String key) {
		final int index = key.indexOf(';');
		if (index != -1) {
			return new CompoundIcon(
				icon.getIcon(key.substring(0, index)),
				icon.getIcon("tick/" + key.substring(index + 1))
			);
		}
		return icon.getIcon(key);
	}

	/**
	 * Compute the disabled icon of the action.
	 * @param key the key of the icon: <path>;<tick>
	 * @return the disabled version of the icon.
	 */
	private final Icon getDisabledIcon(final String key) {
		final int index = key.indexOf(';');
		if (index != -1) {
			return new CompoundIcon(
				icon.getDisabledIcon(key.substring(0, index)),
				icon.getDisabledIcon("tick/" + key.substring(index + 1))
			);
		}
		return icon.getDisabledIcon(key);
	}

	/**
	 * Create a new JMenuItem based on the action.
	 * This is needed since we use CompoundIcon and we must update the disabled icon.
	 * @return the menu item configured.
	 */
	public final JMenuItem newMenu() {
		final JMenuItem item = new JMenuItem(this);
		item.setDisabledIcon((Icon) getValue(DISABLED_SMALL_ICON));
		return item;
	}

	/**
	 * Create a new JButton based on the action.
	 * @return the button configured.
	 */
	public final JButton newButton() {
		final JButton button = new JButton(this);
		button.setText(null);
		button.setDisabledIcon((Icon) getValue(DISABLED_SMALL_ICON));

		return button;
	}

	protected String i18n(final String key, final Object... parameters) {
		return i18n.getMessage(key, parameters);
	}
}