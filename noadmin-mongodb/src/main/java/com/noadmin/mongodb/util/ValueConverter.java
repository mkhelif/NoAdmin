package com.noadmin.mongodb.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Utility class used to convert an object to a specific type.
 *
 * @author mkhelif
 */
public final class ValueConverter {

	/**
	 * Convert an object to the type.
	 * @param key the name of the property holding the value.
	 * @param value the original value.
	 * @param type the target type.
	 * @return the converted value.
	 */
	public static Object convertTo(final String key, final Object value, final Class<?> type) {
		if (Number.class.isAssignableFrom(type)) {
			return convertNumber(String.valueOf(value), type);
		} else if (Boolean.class == type) {
			return Boolean.valueOf(String.valueOf(value));
		} else if (String.class == type) {
			return String.valueOf(value);
		} else if (DBObject.class == type) {
			return convertObject(key, value);
		} else {
			return value;
		}
	}

	/**
	 * Convert the number to the right type.
	 */
	private static Number convertNumber(final String string, final Class<?> type) {
		try {
			final Double value = Double.parseDouble(string);
			if (Integer.class == type) {
				return value.intValue();
			}
			if (Double.class == type) {
				return value.doubleValue();
			}
			if (Long.class == type) {
				return value.longValue();
			}
			if (Short.class == type) {
				return value.shortValue();
			}
			if (Byte.class == type) {
				return value.byteValue();
			}
			if (Float.class == type) {
				return value.floatValue();
			}
			return value;
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Convert the value to a {@link DBObject}.
	 * @param document the document holding the property.
	 * @param name the name of the property.
	 * @param value the value to convert.
	 * @return the {@link DBObject}.
	 */
	private static DBObject convertObject(final String name, final Object value) {
		return new BasicDBObject(name, value);
	}
}