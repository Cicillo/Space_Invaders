package space.invaders;

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
	private final Vec2D size;

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
		return getMinX() + size.getX();
	}

	public double getMaxY() {
		return getMinX() + size.getY();
	}

	public double getWidth() {
		return size.getX();
	}

	public double getHeight() {
		return size.getX();
	}

	public void move(Vec2D delta) {
		this.position = position.plus(delta);
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
}
