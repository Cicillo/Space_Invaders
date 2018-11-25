package space.invaders;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Tomer Moran
 */
public class GameLogic {

	private final TreeSet<IntegerCoordinates> aliens;

	private int leftmostAlien;
	private int rightmostAlien;
	private int downmostAlien;
	private int remainingLives;

	/**
	 * The direction in which aliens move. Left is {@code true} while right is
	 * {@code false}.
	 */
	private boolean alienMovementDirection;

	private Vec2D alienPosition;

	private volatile boolean frozen;

	public GameLogic() {
		this.aliens = new TreeSet<>();
	}

	public TreeSet<IntegerCoordinates> getAliens() {
		return aliens;
	}

	public int getRemainingLives() {
		return remainingLives;
	}

	public Vec2D getAlienPosition() {
		return alienPosition;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public Vec2D getAlienPosition(IntegerCoordinates coords) {
		return getAlienPosition(coords.getX(), coords.getY());
	}

	public Vec2D getAlienPosition(int gridX, int gridY) {
		double x = gridX * (GameConstants.ALIEN_SIZE + GameConstants.ALIEN_SPACING_V);
		double y = gridY * (GameConstants.ALIEN_SIZE + GameConstants.ALIEN_SPACING_H);

		return alienPosition.plus(new Vec2D(x, y));
	}

	public void generateGame() {
		// Add coordinates to a distinct set to maximize later efficiency
		// -> If they were added directly, the TreeSet would end up as a linked list.
		Set<IntegerCoordinates> set = new HashSet<>();
		for (int x = 0; x < GameConstants.ALIENS_GRID_LENGTH; ++x) {
			for (int y = 0; y < GameConstants.ALIENS_GRID_HEIGHT; ++y) {
				set.add(new IntegerCoordinates(x, y));
			}
		}

		aliens.addAll(set);

		// Make aliens move right
		alienMovementDirection = true;

		// Initialize start position
		alienPosition = GameConstants.START_ALIEN_POSITION;

		// Initialize index pointers
		leftmostAlien = 0;
		rightmostAlien = GameConstants.ALIENS_GRID_LENGTH - 1;
		downmostAlien = GameConstants.ALIENS_GRID_HEIGHT - 1;
	}

	public void tickGame() {
		if (frozen) {
			return;
		}

		updateAliens();

		// TODO: other stuff
	}

	private void updateAliens() {
		// Move down
		if (getAlienPosition(rightmostAlien, 0).getX() >= GameConstants.RIGHT_GAME_BOUND
				|| getAlienPosition(leftmostAlien, 0).getX() <= GameConstants.LEFT_GAME_BOUND) {

			this.alienPosition = alienPosition.plus(GameConstants.ALIEN_MOVE_DOWN);
			return;
		}

		// Move right or left
		Vec2D move = alienMovementDirection ? GameConstants.ALIEN_MOVE_RIGHT : GameConstants.ALIEN_MOVE_LEFT;
		this.alienPosition = alienPosition.plus(move);
	}
}
