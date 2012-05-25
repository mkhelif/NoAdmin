package com.noadmin.view.beans.tree;

import javax.swing.JComboBox;

import com.noadmin.util.CollectionUtil;

/**
 * Combo box used to select class type.
 *
 * @author mkhelif
 */
public final class ClassComboBox extends JComboBox<Class<?>> {

	/**
	 * The available classes.
	 */
	private static final Class<?>[] CLASSES = new Class<?>[] {
		String.class,
		Integer.class,
		Boolean.class,
		Double.class,
		Long.class,
		Short.class,
		Byte.class,
		Float.class,
	};

	public ClassComboBox() {
		super(CLASSES);
		this.setRenderer(new ClassComboRenderer());
	}

	public ClassComboBox(final Class<?>... classes) {
		super(CollectionUtil.merge(CLASSES, classes));
		this.setRenderer(new ClassComboRenderer());
	}
}