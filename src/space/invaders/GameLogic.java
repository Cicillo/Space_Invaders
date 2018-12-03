package space.invaders;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import space.invaders.enemies.DummyEnemy;
import space.invaders.enemies.Enemy;
import space.invaders.enemies.LaserEnemy;
import space.invaders.enemies.NormalEnemy;
import space.invaders.enemies.SpinnerEnemy;
import space.invaders.enemies.SuperEnemy;
import space.invaders.enemies.TankEnemy;
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
	private final SimpleIntegerProperty scoreValue;

	private int leftmostEnemy;
	private int rightmostEnemy;
	private int downmostEnemy;
	private int remainingLives;
	private long frozenTicks;

	private int enemiesKilled;
	private volatile int gameState;

	/**
	 * The direction in which enemies move. Left is {@code true} while right is
	 * {@code false}.
	 */
	private boolean enemyMovementDirection;

	private final SimpleObjectProperty<Vec2D> enemyPosition = new SimpleObjectProperty<Vec2D>() {
		AtomicReference<Vec2D> pos = new AtomicReference<>();

		@Override
		public Vec2D get() {
			return pos.get();
		}

		@Override
		public Vec2D getValue() {
			return pos.get();
		}

		@Override
		public void set(Vec2D newValue) {
			pos.set(newValue);
			fireValueChangedEvent();
		}

		@Override
		public void setValue(Vec2D newValue) {
			pos.set(newValue);
			fireValueChangedEvent();
		}

	};

	private Scene scene;
	private final Robot robot;

	private Pane enemiesPane;

	public GameLogic(ObservableDoubleValue spaceshipPosition) {
		this.random = new Random();
		this.enemies = new ConcurrentHashMap<>();
		this.friendlyProjectiles = ConcurrentHashMap.newKeySet();
		this.enemyProjectiles = ConcurrentHashMap.newKeySet();
		this.spaceshipPosition = spaceshipPosition;
		this.remainingLives = GameConstants.PLAYER_LIVES;

		try {
			robot = new Robot();
		} catch (AWTException ex) {
			throw new RuntimeException(ex);
		}

		this.scoreValue = new SimpleIntegerProperty(0);
	}

	public Random getRandom() {
		return random;
	}

	public ObservableIntegerValue getScore() {
		return scoreValue;
	}

	public boolean isFrozen() {
		return frozenTicks > 0;
	}

	public boolean hasWon() {
		return gameState == 1;
	}

	public boolean hasLost() {
		return gameState == -1;
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

	public ObservableValue<Vec2D> getEnemyPositionRef() {
		return enemyPosition;
	}

	public Vec2D getEnemyPosition(int gridX, int gridY) {
		return enemyPosition.get().plus(GameConstants.ENEMY_DELTA.scale(gridX, gridY));
	}

	public void generateGame(GamePane gamePane) {
		// Initialize start position
		enemyPosition.set(GameConstants.START_ENEMIES_POSITION);

		this.scene = gamePane.getScene();

		// Create enemies
		enemiesPane = new Pane();
		gamePane.getChildren().add(enemiesPane);
		for (int x = 0; x < GameConstants.ENEMIES_GRID_LENGTH; ++x) {
			for (int y = 0; y < GameConstants.ENEMIES_GRID_HEIGHT; ++y) {
				IntegerCoordinates coords = new IntegerCoordinates(x, y);
				Enemy enemy = makeEnemy(coords);
				enemies.put(coords, enemy);
				enemy.initializeAnimation(enemiesPane);
			}
		}

		// Make enemies move right
		enemyMovementDirection = true;

		// Initialize index pointers
		leftmostEnemy = 0;
		rightmostEnemy = GameConstants.ENEMIES_GRID_LENGTH - 1;
		downmostEnemy = GameConstants.ENEMIES_GRID_HEIGHT - 1;
	}

	private static final String[] LEVEL_ENEMIES = {
		"NSNSNSNSNSN",
		"NNNNNNNNNNN",
		"NNPNLNLNPNN",
		"NTTNTNTNTTN",
		"NNNTTTTTNNN"
	};

	private Enemy makeEnemy(IntegerCoordinates coords) {
		char c = LEVEL_ENEMIES[coords.getY()].charAt(coords.getX());
		switch (c) {
			case 'N':
				return new NormalEnemy(coords);
			case 'S':
				return new SuperEnemy(coords);
			case 'E':
				return new DummyEnemy(coords);
			case 'T':
				return new TankEnemy(coords);
			case 'P':
				return new SpinnerEnemy(coords);
			case 'L':
				return new LaserEnemy(coords);
			default:
				throw new AssertionError("Unexpected char " + c);
		}
	}

	public void tickGame() {
		if (gameState != 0)
			return;

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
		handleEnemyToPlayerCollision();
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
					if (collided.hasAnimation()) {
						collided.getAnimation().stop();
						Platform.runLater(() -> enemiesPane.getChildren().remove(collided.getAnimation()));
					}

					// Check for victory
					++enemiesKilled;
					scoreValue.set(Enemy.getEnemyKillScore(collided) + scoreValue.get());
					if (enemiesKilled >= GameConstants.ENEMIES_COUNT) {
						gameState = 1;
						return;
					}

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

		synchronized (friendlyProjectiles) {
			for (Iterator<Projectile> it = friendlyProjectiles.iterator(); it.hasNext();) {
				// Projectiles shot by the player are NormalProjectile instances
				NormalProjectile proj = (NormalProjectile) it.next();
				if (proj == null)
					continue;

				for (Iterator<Projectile> it2 = enemyProjectiles.iterator(); it2.hasNext();) {
					// Get enemy projectile
					Projectile proj2 = it2.next();

					// Check for collision (omit laser projectiles)
					if (!(proj2 instanceof NormalProjectile) || !proj.collidesWith((NormalProjectile) proj2))
						continue;

					// Collision: add score
					scoreValue.set(GameConstants.SCORE_PROJECTILE_KILLED + scoreValue.get());

					// Remove both projectiles
					try {
						it.remove();
						it2.remove();
					} catch (IllegalStateException err) {
					}
				}
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

	private void handleEnemyToPlayerCollision() {
		// Preliminary check
		Vec2D pos = getEnemyPosition(0, downmostEnemy);
		if (pos.getY() + GameConstants.ENEMY_SIZE.getY() < GameConstants.SPACESHIP_Y)
			return;

		// Check collision
		RectBounds spaceshipBounds = new RectBounds(
				new Vec2D(spaceshipPosition.get(), GameConstants.SPACESHIP_Y),
				GameConstants.SPACESHIP_SIZE);
		for (int x = 0; x < GameConstants.ENEMIES_GRID_LENGTH; ++x) {
			IntegerCoordinates coords = new IntegerCoordinates(x, downmostEnemy);
			Enemy e = enemies.get(coords);
			if (e != null && e.getBounds(enemyPosition.get()).intersects(spaceshipBounds)) {
				removePlayerLife(remainingLives);
			}
		}
	}

	public void removePlayerLife() {
		removePlayerLife(1);
	}

	public void removePlayerLife(int qty) {
		// Decrement life count
		if ((remainingLives -= qty) <= 0) {
			// Game over
			gameState = -1;
			return;
		}

		robot.mouseMove((int) (scene.getWindow().getX() + GameConstants.LEFT_GAME_BOUND),
				(int) (scene.getWindow().getY() + scene.getHeight() / 2));

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
