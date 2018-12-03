package space.invaders.enemies;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import space.invaders.AnimationResources;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.ImageAnimation;
import space.invaders.IntegerCoordinates;
import space.invaders.RectBounds;
import space.invaders.Vec2D;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Totom3
 */
public abstract class Enemy {

	public static int getEnemyKillScore(Enemy e) {
		if (e instanceof LaserEnemy) {
			return GameConstants.SCORE_LASER_KILLED;
		}

		if (e instanceof SpinnerEnemy) {
			return GameConstants.SCORE_SPINNER_KILLED;
		}

		if (e instanceof TankEnemy) {
			return GameConstants.SCORE_TANK_KILLED;
		}

		if (e instanceof DummyEnemy) {
			return GameConstants.SCORE_DUMMY_KILLED;
		}

		if (e instanceof NormalEnemy) {
			return GameConstants.SCORE_NORMAL_KILLED;
		}
		
		if (e instanceof SuperEnemy) {
			return GameConstants.SCORE_SUPER_KILLED;
		}
		
		
		throw new IllegalArgumentException();
	}

	protected boolean alive;
	protected volatile Image image;
	private final IntegerCoordinates coords;

	protected Pane displayPane;
	protected volatile ImageAnimation animation;

	public Enemy(Image image, IntegerCoordinates coords) {
		this.alive = true;
		this.image = image;
		this.coords = coords;
	}

	public Enemy(AnimationResources res, IntegerCoordinates coords) {
		this.alive = true;
		this.coords = coords;

		this.animation = new ImageAnimation(res.getAnimation());
	}

	/**
	 *
	 * @return {@code true} if this enemy is alive, {@code false} otherwise.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 *
	 * @return the visual image of this enemy.
	 */
	public Image getImage() {
		return image;
	}

	public boolean hasAnimation() {
		return animation != null;
	}

	public ImageAnimation getAnimation() {
		return animation;
	}

	public boolean initializeAnimation(Pane pane) {
		if (animation == null)
			return false;

		animation.play();
		this.displayPane = pane;
		Platform.runLater(() -> pane.getChildren().add(animation));
		return true;
	}

	/**
	 *
	 * @return the grid coordinates of this enemy, relative to the top left.
	 */
	public IntegerCoordinates getCoordinates() {
		return coords;
	}

	/**
	 * Returns the position of this enemy.
	 *
	 * @param origin the position of the origin enemy.
	 * @return the position of this enemy.
	 */
	public Vec2D getPosition(Vec2D origin) {
		return origin.plus(GameConstants.ENEMY_DELTA.scale(coords.getX(), coords.getY()));
	}

	/**
	 * Returns the bounds of this enemy.
	 *
	 * @param origin the position of the origin enemy.
	 * @return the bounding box of this enemy.
	 */
	public RectBounds getBounds(Vec2D origin) {
		return new RectBounds(getPosition(origin), GameConstants.ENEMY_SIZE);
	}

	/**
	 * Ticks this enemy.
	 *
	 * @param logic the game logic instance.
	 */
	public abstract void tick(GameLogic logic);

	/**
	 * Handles any logic that must be handled when hit by a projectile, and
	 * returns whether or not this enemy is killed.
	 *
	 * @param proj the projectile that hit the enemy.
	 * @return {@code true} if this enemy is now dead, {@code false} otherwise.
	 */
	public abstract boolean onHit(Projectile proj);
}
