package space.invaders;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import space.invaders.enemies.Enemy;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Tomer Moran
 */
public class GameLogic {

	private final Random random;
	private final TreeSet<Enemy> enemies;
	private final Set<Projectile> friendlyProjectiles;
	private final Set<Projectile> enemyProjectiles;

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
		this.random = new Random();
		this.enemies = new TreeSet<>((a, b) -> a.getCoordinates().compareTo(b.getCoordinates()));
		this.friendlyProjectiles = new HashSet<>();
		this.enemyProjectiles = new HashSet<>();
	}

	public Random getRandom() {
		return random;
	}

	public TreeSet<Enemy> getEnemies() {
		return enemies;
	}

	public Set<Projectile> getFriendlyProjectiles() {
		return friendlyProjectiles;
	}

	public Set<Projectile> getEnemyProjectiles() {
		return friendlyProjectiles;
	}

	public boolean addProjectile(Projectile proj) {
		return (proj.isFriendly() ? friendlyProjectiles : enemyProjectiles).add(proj);
	}

	public boolean removeProjectile(Projectile proj) {
		return (proj.isFriendly() ? friendlyProjectiles : enemyProjectiles).remove(proj);
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
		// Add enemies to a distinct set to maximize later efficiency
		// If they were added directly, the TreeSet would end up as a linked list.
		Set<Enemy> set = new HashSet<>();
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
		// 1. Move the enemies around
		moveEnemies();

		// 2. Tick each enemy
		enemies.forEach(e -> e.tick(this));

		// 3. Tick each projectile
		friendlyProjectiles.forEach(Projectile::tick);
		enemyProjectiles.forEach(Projectile::tick);

		// 4. Check collisions!
		// TODO
	}

	private void moveEnemies() {
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
