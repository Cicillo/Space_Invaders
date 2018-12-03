package space.invaders.util;

import javafx.geometry.Bounds;

/**
 *
 * @author Totom3
 */
public class RectBounds {

	public static RectBounds fromCoordinates(double minX, double minY, double maxX, double maxY) {
		return new RectBounds(minX, minY, maxX - minX, maxY - minY);
	}

	private Vec2D position;
	private Vec2D size;

	public RectBounds(Vec2D position, Vec2D size) {
		this.position = position;
		this.size = size;
	}

	public RectBounds(double minX, double minY, double width, double height) {
		this.size = new Vec2D(width, height);
		this.position = new Vec2D(minX, minY);

	}

	public Vec2D getPosition() {
		return position;
	}

	public Vec2D getSize() {
		return size;
	}

	public double getMinX() {
		return position.getX();
	}

	public double getMinY() {
		return position.getY();
	}

	public double getMaxX() {
		return position.getX() + size.getX();
	}

	public double getMaxY() {
		return position.getY() + size.getY();
	}

	public double getWidth() {
		return size.getX();
	}

	public double getHeight() {
		return size.getY();
	}

	public void move(Vec2D delta) {
		this.position = position.plus(delta);
	}

	public void position(Vec2D pos) {
		this.position = pos;
	}

	public void enlarge(Vec2D delta) {
		this.size = size.plus(delta);
	}

	public boolean intersects(RectBounds bounds) {
		return intersects(bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
	}

	public boolean intersects(Bounds bounds) {
		return intersects(bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
	}

	public boolean intersects(double minX, double minY, double maxX, double maxY) {
		return (maxX >= getMinX()
				&& maxY >= getMinY()
				&& minX <= getMaxX()
				&& minY <= getMaxY());
	}

	@Override
	public String toString() {
		return String.format("{(%.1f,%.1f), (%.1f, %.1f)}", getMinX(), getMinY(), getMaxX(), getMaxY());
	}
}
