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
		@Override public double toKilos(final double value) { return value / 1000; }
		@Override public double toMegas(final double value) {  return value / 1000 / 1000; }
		@Override public double toGigas(final double value) { return value / 1000 / 1000 / 1000; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toUnits(d); }
	},
	KILO("K") {
		@Override public double toUnits(final double value) { return value * 1000; }
		@Override public double toKilos(final double value) { return value; }
		@Override public double toMegas(final double value) { return value / 1000; }
		@Override public double toGigas(final double value) { return value / 1000 / 1000; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toKilos(d); }
	},
	MEGA("M") {
		@Override public double toUnits(final double value) { return value * 1000 * 1000; }
		@Override public double toKilos(final double value) { return value * 1000; }
		@Override public double toMegas(final double value) { return value; }
		@Override public double toGigas(final double value) { return value / 1000; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toMegas(d); }
	},
	GIGA("G") {
		@Override public double toUnits(final double value) { return value * 1000 * 1000 * 1000; }
		@Override public double toKilos(final double value) { return value * 1000 * 1000; }
		@Override public double toMegas(final double value) { return value * 1000; }
		@Override public double toGigas(final double value) { return value; }
		@Override public double convert(final double d, final DigitalUnit u) { return u.toGigas(d); }
	};

	private final String symbol;

	private DigitalUnit(final String symbol) {
		this.symbol = symbol;
	}

	protected abstract double toUnits(final double value);
	protected abstract double toKilos(final double value);
	protected abstract double toMegas(final double value);
	protected abstract double toGigas(final double value);

	public abstract double convert(final double sourceValue, final DigitalUnit sourceUnit);

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
			if (unit.convert(value, DigitalUnit.UNIT) < 1000) {
				return unit;
			}
		}
		return DigitalUnit.UNIT;
	}
}