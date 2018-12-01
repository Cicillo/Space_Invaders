package space.invaders.projectiles;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import space.invaders.GameLogic;
import space.invaders.RectBounds;
import space.invaders.enemies.Enemy;

public abstract class Projectile {

	private final boolean friendly;

	public Projectile(boolean friendly) {
		this.friendly = friendly;
	}

	/**
	 * Returns whether or not this projectile is friendly to the player.
	 *
	 * @return {@code true} if the projectile was shot by the player or by an
	 * ally, {@code false} if it was shot by an enemy.
	 */
	public boolean isFriendly() {
		return friendly;
	}

	/**
	 * Ticks this projectile, updating the location or velocity as necessary.
	 */
	public abstract void tick();

	/**
	 * Draws this projectile onto the given {@code GraphicsContext}.
	 *
	 * @param graphics the graphics onto which this projectile should be drawn.
	 */
	public abstract void draw(GraphicsContext graphics);

	/**
	 * Returns whether or not this projectile collides with the given bounding
	 * box.
	 *
	 * @param x the min x location.
	 * @param y the min y location.
	 * @param width the width of the bounding box.
	 * @param height the height of the bounding box.
	 * @return {@code true} if this projectile collides with the given bounding
	 * box, {@code false} otherwise.
	 */
	public abstract boolean collidesWith(double x, double y, double width, double height);

	/**
	 * Returns whether or not this projectile collides with the given bounding
	 * box.
	 *
	 * @param bounds the bounds to check.
	 * @return {@code true} if there is a collision, {@code false} otherwise.
	 */
	public abstract boolean collidesWith(Bounds bounds);

	/**
	 * Returns whether or not this projectile collides with the given bounding
	 * box.
	 *
	 * @param bounds the bounds to check.
	 * @return {@code true} if there is a collision, {@code false} otherwise.
	 */
	public abstract boolean collidesWith(RectBounds bounds);

	/**
	 * Returns the enemy with which this projectile is currently colliding with,
	 * or {@code null} if none.
	 *
	 * @param logic the game logic instance.
	 * @return the collided enemy, or {@code null} if none.
	 */
	public abstract Enemy getCollidedEnemy(GameLogic logic);
}
