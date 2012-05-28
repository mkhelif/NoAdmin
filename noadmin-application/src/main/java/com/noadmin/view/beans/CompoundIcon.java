package com.noadmin.view.beans;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * Icon that is composed of a background icon and a top icon.
 *
 * @author mkhelif
 */
public class CompoundIcon implements Icon {

	/**
	 * The bottom icon.
	 */
	private final Icon background;

	/**
	 * Tick icon displayed on top of the background.
	 */
	private Icon tick;

	/**
	 * X-offset of the tick icon.
	 */
	private transient int offsetX;

	/**
	 * Y-offset of the tick icon.
	 */
	private transient int offsetY;

	/**
	 * Create a new Compound icon with the given background icon.
	 * @param background the bottom icon.
	 */
	public CompoundIcon(final Icon background) {
		this(background, null);
	}

	/**
	 * Create a new Compound icon with the given background and tick icons.
	 * @param background the bottom icon.
	 * @param tick the tick icon.
	 */
	public CompoundIcon(final Icon background, final Icon tick) {
		this.background = background;
		this.setTick(tick);
	}

	/**
	 * Update the tick icon.
	 * @param tick the new tick icon.
	 */
	public final void setTick(final Icon tick) {
		this.tick = tick;
		if (tick != null) {
			offsetX = this.getIconWidth() - tick.getIconWidth();
			offsetY = this.getIconHeight() - tick.getIconHeight();
		}
	}

	/**
     * @return the fixed width of the icon.
     */
	@Override
	public final int getIconWidth() {
		return background.getIconWidth();
	}

    /**
     * @return the fixed height of the icon.
     */
	@Override
	public final int getIconHeight() {
		return background.getIconHeight();
	}

	/**
     * Draw the icon at the specified location.  Icon implementations may use the
     * Component argument to get properties useful for painting, e.g. the foreground
     * or background color.
     * @param c the component where the icon is painted.
     * @param g the 2D graphics.
     * @param x the X location of the icon.
     * @param y the Y location of the icon.
     */
	@Override
	public final void paintIcon(final Component c, final Graphics g, final int x, final int y) {
		background.paintIcon(c, g, x, y);
		if (tick != null) {
			tick.paintIcon(c, g, x + offsetX, y + offsetY);
		}
	}
}