package com.noadmin.mongodb.actions;

import java.awt.event.ActionEvent;

import com.mongodb.DB;
import com.noadmin.mongodb.model.Collection;

/**
 * Add a new {@link Collection} to the {@link DB}.
 *
 * @author mkhelif
 */
public final class AddDBAction extends AbstractMongoDBAction {

	private static final AddDBAction INSTANCE = new AddDBAction();
	public static AddDBAction getInstance() { return INSTANCE; }

	private AddDBAction() {
		super("mongodb.collection.add", "collection;add");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
	}
}