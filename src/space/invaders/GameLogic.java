package space.invaders;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javafx.beans.value.ObservableDoubleValue;
import space.invaders.enemies.Enemy;
import space.invaders.enemies.LaserEnemy;
import space.invaders.enemies.SpinnerEnemy;
import space.invaders.projectiles.NormalProjectile;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Tomer Moran
 */
public class GameLogic {

	private final Random random;
	private final ConcurrentHashMap<IntegerCoordinates, Enemy> enemies;
	private final Set<Projectile> friendlyProjectiles;
	private final Set<Projectile> enemyProjectiles;
	private final ObservableDoubleValue spaceshipPosition;

	private int leftmostEnemy;
	private int rightmostEnemy;
	private int downmostEnemy;
	private int remainingLives;
	private long frozenTicks;

	/**
	 * The direction in which enemies move. Left is {@code true} while right is
	 * {@code false}.
	 */
	private boolean enemyMovementDirection;

	private final AtomicReference<Vec2D> enemyPosition;

	public GameLogic(ObservableDoubleValue spaceshipPosition) {
		this.random = new Random();
		this.enemies = new ConcurrentHashMap<>();
		this.friendlyProjectiles = ConcurrentHashMap.newKeySet();
		this.enemyProjectiles = ConcurrentHashMap.newKeySet();
		this.spaceshipPosition = spaceshipPosition;
		this.enemyPosition = new AtomicReference<>();

		this.remainingLives = GameConstants.PLAYER_LIVES;
	}

	public Random getRandom() {
		return random;
	}

	public boolean isFrozen() {
		return frozenTicks > 0;
	}

	public ConcurrentHashMap<IntegerCoordinates, Enemy> getEnemies() {
		return enemies;
	}

	public Enemy getEnemy(IntegerCoordinates coords) {
		return enemies.get(coords);
	}

	public Enemy getEnemy(int gridX, int gridY) {
		return getEnemy(new IntegerCoordinates(gridX, gridY));
	}

	public void forEachEnemy(Consumer<Enemy> cons) {
		enemies.values().forEach(cons);
	}

	public void forEachProjectile(Consumer<Projectile> cons) {
		friendlyProjectiles.forEach(cons);
		enemyProjectiles.forEach(cons);
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
		return enemyPosition.get();
	}

	public AtomicReference<Vec2D> getEnemyPositionRef() {
		return enemyPosition;
	}

	public Vec2D getEnemyPosition(int gridX, int gridY) {
		return enemyPosition.get().plus(GameConstants.ENEMY_DELTA.scale(gridX, gridY));
	}

	public void generateGame() {
		// Create enemies
		for (int x = 0; x < GameConstants.ENEMIES_GRID_LENGTH; ++x) {
			for (int y = 0; y < GameConstants.ENEMIES_GRID_HEIGHT; ++y) {
				IntegerCoordinates coords = new IntegerCoordinates(x, y);
				enemies.put(coords, (x == 5 && y == 2) ? new SpinnerEnemy(coords) : new LaserEnemy(coords));
			}
		}

		// Make enemies move right
		enemyMovementDirection = true;

		// Initialize start position
		enemyPosition.set(GameConstants.START_ENEMIES_POSITION);

		// Initialize index pointers
		leftmostEnemy = 0;
		rightmostEnemy = GameConstants.ENEMIES_GRID_LENGTH - 1;
		downmostEnemy = GameConstants.ENEMIES_GRID_HEIGHT - 1;
	}

	public void tickGame() {
		if (isFrozen()) {
			--frozenTicks;
			return;
		}

		// 1. Move the enemies around
		moveEnemies();

		// 2. Tick each enemy
		forEachEnemy(e -> e.tick(this));

		// 3. Tick each projectile
		tickProjectiles(enemyProjectiles);
		tickProjectiles(friendlyProjectiles);

		// 4. Check collisions!
		handleProjectileToProjectileCollision();
		handleFriendlyProjectilesToEnemyCollision();
		handleEnemyProjectilesToPlayerCollision();
	}

	private void moveEnemies() {
		// Check if wall was hit
		if (shouldEnemiesJumpDown()) {

			// Move down
			this.enemyPosition.set(enemyPosition.get().plus(GameConstants.ENEMY_DOWN_JUMP));

			// Invert direction
			this.enemyMovementDirection = !enemyMovementDirection;
			return;
		}

		// Move right or left
		Vec2D move = GameConstants.ENEMY_MOVEMENT_SPEED;
		this.enemyPosition.set((enemyMovementDirection) ? enemyPosition.get().plus(move) : enemyPosition.get().minus(move));
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

	private void tickProjectiles(Set<Projectile> projectiles) {
		for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext();) {
			Projectile proj = it.next();
			if (proj.tick())
				it.remove();
		}
	}

	private void handleFriendlyProjectilesToEnemyCollision() {
		for (Iterator<Projectile> it = friendlyProjectiles.iterator(); it.hasNext();) {
			Projectile proj = it.next();

			Enemy collided = proj.getCollidedEnemy(this);
			if (collided != null) {
				// Remove the projectile
				it.remove();

				// Let the enemy handle the collision & remove if needed
				boolean remove = collided.onHit(proj);
				if (remove) {
					IntegerCoordinates coords = collided.getCoordinates();
					enemies.remove(coords);

					// Update leftmost enemy 
					if (coords.getX() == leftmostEnemy) {
						leftmostEnemy = findIndex(LEFTMOST).getX();
					}

					if (coords.getX() == rightmostEnemy) {
						rightmostEnemy = findIndex(RIGHTMOST).getX();
					}

					if (coords.getY() == downmostEnemy) {
						downmostEnemy = findIndex(DOWNMOST).getY();
					}
				}

			}
		}
	}

	private void handleProjectileToProjectileCollision() {
		for (Iterator<Projectile> it = friendlyProjectiles.iterator(); it.hasNext();) {
			// Projectiles shot by the player are NormalProjectile instances
			NormalProjectile proj = (NormalProjectile) it.next();

			for (Iterator<Projectile> it2 = enemyProjectiles.iterator(); it2.hasNext();) {
				// Get enemy projectile
				Projectile proj2 = it2.next();

				// Check for collision (omit laser projectiles)
				if (!(proj2 instanceof NormalProjectile) || !proj.collidesWith((NormalProjectile) proj2))
					continue;

				// Collision: remove both projectiles
				it.remove();
				it2.remove();
			}
		}
	}

	private void handleEnemyProjectilesToPlayerCollision() {
		RectBounds spaceshipBounds = new RectBounds(new Vec2D(spaceshipPosition.get(), GameConstants.SPACESHIP_Y), GameConstants.SPACESHIP_SIZE);
		for (Iterator<Projectile> it = enemyProjectiles.iterator(); it.hasNext();) {
			// Get enemy projectile
			Projectile proj = it.next();

			// Check for collision
			if (proj.collidesWith(spaceshipBounds)) {
				// Remove projectile & remove player life
				it.remove();
				removePlayerLife();
			}
		}
	}

	public void removePlayerLife() {
		// Decrement life count
		if (--remainingLives <= 0) {
			// Game over
			// TODO
			System.exit(1);
		}

		enemyProjectiles.clear();
		friendlyProjectiles.clear();
		frozenTicks = GameConstants.DEATH_FREEZE_TIME;
	}

	private IntegerCoordinates findIndex(BiFunction<IntegerCoordinates, IntegerCoordinates, IntegerCoordinates> function) {
		IntegerCoordinates coords = null;
		for (IntegerCoordinates c : enemies.keySet()) {
			if (coords == null) {
				coords = c;
				continue;
			}

			coords = function.apply(coords, c);
		}

		return coords;
	}

	private static final BiFunction<IntegerCoordinates, IntegerCoordinates, IntegerCoordinates> LEFTMOST = (a, b) -> {
		return (a.getX() <= b.getX()) ? a : b;
	};

	private static final BiFunction<IntegerCoordinates, IntegerCoordinates, IntegerCoordinates> RIGHTMOST = (a, b) -> {
		return (a.getX() >= b.getX()) ? a : b;
	};

	private static final BiFunction<IntegerCoordinates, IntegerCoordinates, IntegerCoordinates> DOWNMOST = (a, b) -> {
		return (a.getY() >= b.getY()) ? a : b;
	};

}
