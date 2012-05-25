package com.noadmin.view.beans;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

import org.springframework.stereotype.Component;

/**
 * Bean used to load icon for the application.
 *
 * @author mkhelif
 */
@Component
public final class IconManager {

	/**
	 * Component used to generate the disabled versions of the icons.
	 */
	private static final JLabel COMPONENT = new JLabel();
	static {
		COMPONENT.putClientProperty("WindowsLookAndFeel.generateHiResDisabledIcon", Boolean.TRUE);
	}

	/**
	 * Map between the name and the icons.
	 */
	private final Map<String, ImageIcon> icons;
	private final Map<String, ImageIcon> disabled;

	public IconManager() {
		icons = new HashMap<>();
		disabled = new HashMap<>();
	}

	/**
	 * Retrieve the icon mapped with the name.
	 * @param name the name of the icon.
	 * @return the matching icon.
	 */
	public ImageIcon getIcon(final String name) {
		if (!icons.containsKey(name)) {
			loadIcon(name);
		}
		return icons.get(name);
	}

	/**
	 * Retrieve the disabled version of the icon mapped with the name.
	 * @param name the name of the icon.
	 * @return the matching icon.
	 */
	public ImageIcon getDisabledIcon(final String name) {
		if (!disabled.containsKey(name)) {
			loadDisabledIcon(name);
		}
		return disabled.get(name);
	}

	/**
	 * Load the icon mapped with the name.
	 */
	private synchronized void loadIcon(final String name) {
		final String path = "/images/" + name.replace(".", "/") + ".png";
		icons.put(name, new ImageIcon(IconManager.class.getResource(path)));
	}

	/**
	 * Load the disabled version of the icon mapped with the name.
	 */
	private synchronized void loadDisabledIcon(final String name) {
		final ImageIcon icon = this.getIcon(name);
		final ImageIcon disabledIcon = (ImageIcon) UIManager.getLookAndFeel().getDisabledIcon(COMPONENT, icon);
		disabled.put(name, disabledIcon);
	}
}