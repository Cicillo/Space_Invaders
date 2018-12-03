package space.invaders.util;

/**
 *
 * @author Tomer Moran
 */
public class IntegerCoordinates implements Comparable<IntegerCoordinates> {

	private final int x, y;

	public IntegerCoordinates() {
		this.x = 0;
		this.y = 0;
	}

	public IntegerCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + this.x;
		hash = 37 * hash + this.y;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IntegerCoordinates)) {
			return false;
		}

		IntegerCoordinates other = (IntegerCoordinates) obj;
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public int compareTo(IntegerCoordinates t) {
		int diffX = this.x - t.x;
		if (diffX != 0)
			return diffX;
		
		return this.y - t.y;
	}

}
