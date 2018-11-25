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

	public Vec2D getAlienPosition(IntegerCoordinates coords) {
		return getAlienPosition(coords.getX(), coords.getY());
	}

	private static final Vec2D ALIEN_DELTA = GameConstants.ALIEN_SIZE.plus(GameConstants.ALIEN_SPACING);

	public Vec2D getAlienPosition(int gridX, int gridY) {
		return alienPosition.plus(ALIEN_DELTA.scale(gridX, gridY));
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
		updateAliens();

		// TODO: other stuff
	}

	private void updateAliens() {
		// Check if wall was hit
		if (shouldAliensJumpDown()) {

			// Move down
			this.alienPosition = alienPosition.plus(GameConstants.ALIEN_DOWN_JUMP);

			// Invert direction
			this.alienMovementDirection = !alienMovementDirection;
			return;
		}

		// Move right or left
		Vec2D move = GameConstants.ALIEN_MOVEMENT_SPEED;
		this.alienPosition = (alienMovementDirection) ? alienPosition.plus(move) : alienPosition.minus(move);
	}

	private boolean shouldAliensJumpDown() {
		if (alienMovementDirection) {
			// Jump down when moving right & hit right wall
			return getAlienPosition(rightmostAlien, 0).getX() + GameConstants.ALIEN_SIZE.getX() >= GameConstants.RIGHT_GAME_BOUND;
		} else {
			// Jump down when moving left & hit left wall
			return getAlienPosition(leftmostAlien, 0).getX() <= GameConstants.LEFT_GAME_BOUND;
		}
	}
}
