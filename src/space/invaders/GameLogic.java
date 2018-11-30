package space.invaders;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import space.invaders.enemies.Enemy;

/**
 *
 * @author Tomer Moran
 */
public class GameLogic {

	private final TreeSet<Enemy> enemies;

	private int leftmostEnemy;
	private int rightmostEnemy;
	private int downmostEnemy;
	private int remainingLives;

	/**
	 * The direction in which enemies move. Left is {@code true} while right is
	 * {@code false}.
	 */
	private boolean enemyMovementDirection;

	private Vec2D enemyPosition;

	public GameLogic() {
		this.enemies = new TreeSet<>();
	}

	public TreeSet<Enemy> getEnemies() {
		return enemies;
	}

	public int getRemainingLives() {
		return remainingLives;
	}

	public Vec2D getEnemyPosition() {
		return enemyPosition;
	}

	public Vec2D getEnemyPosition(int gridX, int gridY) {
		return enemyPosition.plus(GameConstants.ENEMY_DELTA.scale(gridX, gridY));
	}
	
	public void generateGame() {
		// Add coordinates to a distinct set to maximize later efficiency
		// -> If they were added directly, the TreeSet would end up as a linked list.
		Set<IntegerCoordinates> set = new HashSet<>();
		for (int x = 0; x < GameConstants.ENEMIES_GRID_LENGTH; ++x) {
			for (int y = 0; y < GameConstants.ENEMIES_GRID_HEIGHT; ++y) {
				set.add(new IntegerCoordinates(x, y));
			}
		}

		enemies.addAll(set);

		// Make enemies move right
		enemyMovementDirection = true;

		// Initialize start position
		enemyPosition = GameConstants.START_ENEMIES_POSITION;

		// Initialize index pointers
		leftmostEnemy = 0;
		rightmostEnemy = GameConstants.ENEMIES_GRID_LENGTH - 1;
		downmostEnemy = GameConstants.ENEMIES_GRID_HEIGHT - 1;
	}

	public void tickGame() {
		updateEnemies();

		// TODO: other stuff
	}

	private void updateEnemies() {
		// Check if wall was hit
		if (shouldEnemiesJumpDown()) {

			// Move down
			this.enemyPosition = enemyPosition.plus(GameConstants.ENEMY_DOWN_JUMP);

			// Invert direction
			this.enemyMovementDirection = !enemyMovementDirection;
			return;
		}

		// Move right or left
		Vec2D move = GameConstants.ENEMY_MOVEMENT_SPEED;
		this.enemyPosition = (enemyMovementDirection) ? enemyPosition.plus(move) : enemyPosition.minus(move);
	}

	private boolean shouldEnemiesJumpDown() {
		if (enemyMovementDirection) {
			// Jump down when moving right & hit right wall
			return getEnemyPosition(rightmostEnemy, 0).getX() + GameConstants.ENEMY_SIZE.getX() >= GameConstants.RIGHT_GAME_BOUND;
		} else {
			// Jump down when moving left & hit left wall
			return getEnemyPosition(leftmostEnemy, 0).getX() <= GameConstants.LEFT_GAME_BOUND;
		}
	}
}
