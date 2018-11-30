package space.invaders;

/**
 *
 * @author Tomer Moran
 */
public class Vec2D {

	public static final Vec2D ZERO = new Vec2D();

	public static final Vec2D add(Vec2D vec, IntegerCoordinates coords) {
		return new Vec2D(vec.x + coords.getX(), vec.y + coords.getY());
	}

	private final double x, y;

	public Vec2D() {
		this.x = this.y = 0;
	}

	public Vec2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double dot(Vec2D other) {
		return (this.x * other.x) + (this.y * other.y);
	}

	public Vec2D cross(Vec2D other) {
		return new Vec2D(this.x * other.y, -this.y * other.x);
	}

	public Vec2D plus(Vec2D other) {
		return new Vec2D(this.x + other.x, this.y + other.y);
	}

	public Vec2D minus(Vec2D other) {
		return new Vec2D(this.x - other.x, this.y - other.y);
	}

	public Vec2D scale(double k) {
		return new Vec2D(this.x * k, this.y * k);
	}

	public Vec2D scale(double kx, double ky) {
		return new Vec2D(this.x * kx, this.y * ky);
	}

	public Vec2D scale(Vec2D vec) {
		return new Vec2D(this.x * vec.x, this.y * vec.y);
	}

	public Vec2D divide(double k) {
		return new Vec2D(this.x / k, this.y / k);
	}

	public Vec2D divide(double kx, double ky) {
		return new Vec2D(this.x / kx, this.y / ky);
	}

	public Vec2D divide(Vec2D vec) {
		return new Vec2D(this.x / vec.x, this.y / vec.y);
	}

	public double norm() {
		return Math.sqrt(normSquared());
	}

	public double normSquared() {
		return (this.x * this.x) + (this.y * this.y);
	}

	public Vec2D normalize() {
		double norm = norm();
		return new Vec2D(this.x / norm, this.y / norm);
	}

	public double phase() {
		return Math.atan2(this.y, this.x);
	}

	public Vec2D project(Vec2D normal) {
		double k = dot(normal) / normal.normSquared();
		return normal.scale(k);
	}

	private static final double EPSILON = 0.00001;

	@Override
	public String toString() {
		return String.format("(%.2f, %.2f)", this.x, this.y);
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
		hash = 89 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vec2D)) {
			return false;
		}

		if (o == this) {
			return true;
		}

		Vec2D other = (Vec2D) o;
		return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
	}

}
