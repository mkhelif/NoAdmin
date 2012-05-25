package com.noadmin.mongodb.actions;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.DBCollection;
import com.noadmin.controller.Controller;
import com.noadmin.mongodb.model.DBObject;
import com.noadmin.view.beans.GUIHandler;

/**
 * Remove the selected documents from the {@link DBCollection}.
 *
 * @author mkhelif
 */
public final class RemoveFieldAction extends AbstractMongoDBAction {

	private static final RemoveFieldAction INSTANCE = new RemoveFieldAction();
	public static RemoveFieldAction getInstance() { return INSTANCE; }

	/**
	 * List of fields to remove for each selected {@link DBObject}.
	 */
	private Map<DBObject, List<String>> entries;

	private RemoveFieldAction() {
		super("mongodb.field.remove", "property;remove");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// Ask confirmation:
		final boolean choice = Controller.getInstance().getBean(GUIHandler.class).confirm(
			i18n("mongodb.field.remove"),
			i18n("mongodb.field.remove.confirm")
		);
		if (!choice) {
			return;
		}

		// Delete the fields:
		final Iterator<Entry<DBObject, List<String>>> iterator = entries.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<DBObject, List<String>> entry = iterator.next();
			for (final String field : entry.getValue()) {
				entry.getKey().removeField(field);
			}
		}
	}

	public void setEntries(final Map<DBObject, List<String>> entries) {
		this.entries = entries;
		this.update();
	}

	private void update() {
		if (entries == null) {
			this.setEnabled(false);
		} else {
			boolean enabled = !entries.isEmpty();
			for (final List<String> keys: entries.values()) {
				enabled &= !keys.isEmpty() && !keys.contains("_id");
			}
			this.setEnabled(enabled);
		}
	}
}