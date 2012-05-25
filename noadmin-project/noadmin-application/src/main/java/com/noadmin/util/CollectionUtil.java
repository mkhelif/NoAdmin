package com.noadmin.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for {@link Collection}.
 *
 * @author mkhelif
 */
public final class CollectionUtil {
	private CollectionUtil() {}

	/**
	 * Convert a {@link Collection} of <T> to a sorted {@link List} of <T>.
	 */
	public static <T extends Comparable<T>> List<T> asSortedList(final Collection<T> collection) {
		final List<T> list = new ArrayList<>(collection);
		Collections.sort(list);
		return list;
	}

	/**
	 * Merged all arrays into a single array.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] merge(final T[]... arrays) {
		// Compute total size
		Class<T> type = null;
		int size = 0;
		for (final T[] array : arrays) {
			size += array.length;
			if (type == null && array.length > 0) {
				type = (Class<T>) array[0].getClass();
			}
		}

		// Copy all arrays
		final T[] merged = (T[]) Array.newInstance(type, size);

		int index = 0;
		for (final T[] array : arrays) {
			System.arraycopy(array, 0, merged, index, array.length);
			index += array.length;
		}

		return merged;
	}
}