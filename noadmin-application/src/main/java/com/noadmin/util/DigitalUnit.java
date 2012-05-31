package com.noadmin.util;

/**
 * A {@link DigitalUnit} represents data amount at a given unit and provides
 * utility methods to convert across units.
 *
 * For example : 1K = 10^3 = 1000
 *
 * @author mkhelif
 */
public enum DigitalUnit {
	UNIT("") {
		@Override public double toUnits(final double value) { return value; }
		@Override public double toKilos(final double value) { return value / THOUSAND; }
		@Override public double toMegas(final double value) { return value / THOUSAND / THOUSAND; }
		@Override public double toGigas(final double value) { return value / THOUSAND / THOUSAND / THOUSAND; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toUnits(d); }
	},
	KILO("K") {
		@Override public double toUnits(final double value) { return value * THOUSAND; }
		@Override public double toKilos(final double value) { return value; }
		@Override public double toMegas(final double value) { return value / THOUSAND; }
		@Override public double toGigas(final double value) { return value / THOUSAND / THOUSAND; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toKilos(d); }
	},
	MEGA("M") {
		@Override public double toUnits(final double value) { return value * THOUSAND * THOUSAND; }
		@Override public double toKilos(final double value) { return value * THOUSAND; }
		@Override public double toMegas(final double value) { return value; }
		@Override public double toGigas(final double value) { return value / THOUSAND; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toMegas(d); }
	},
	GIGA("G") {
		@Override public double toUnits(final double value) { return value * THOUSAND * THOUSAND * THOUSAND; }
		@Override public double toKilos(final double value) { return value * THOUSAND * THOUSAND; }
		@Override public double toMegas(final double value) { return value * THOUSAND; }
		@Override public double toGigas(final double value) { return value; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toGigas(d); }
	};

	private static final double THOUSAND = 1000;

	/**
	 * The symbol of the unit.
	 */
	private final String symbol;

	private DigitalUnit(final String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Conversion methods
	 * @param value the value to convert.
	 * @return the converted value.
	 */
	protected abstract double toUnits(final double value);
	protected abstract double toKilos(final double value);
	protected abstract double toMegas(final double value);
	protected abstract double toGigas(final double value);

	/**
	 * Convert a value into a specific unit.
	 * @param sourceValue the value.
	 * @param sourceUnit the source unit.
	 * @return the converted value.
	 */
	public abstract double convert(final double sourceValue, final DigitalUnit sourceUnit);

	/**
	 * @return the symbol of the unit.
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Retrieve the best matching {@link DigitalUnit} for a given value.
	 *
	 * @param value the number of unit.
	 * @return the best matching {@link DigitalUnit} which keep an integer part.
	 */
	public static DigitalUnit getBestMatching(final double value) {
		for (final DigitalUnit unit : DigitalUnit.values()) {
			if (unit.convert(value, DigitalUnit.UNIT) < THOUSAND) {
				return unit;
			}
		}
		return DigitalUnit.UNIT;
	}
}