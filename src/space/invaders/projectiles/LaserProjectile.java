package space.invaders.projectiles;

import java.util.concurrent.atomic.AtomicReference;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.ImageResources;
import space.invaders.RectBounds;
import space.invaders.Vec2D;
import space.invaders.enemies.Enemy;
import space.invaders.enemies.LaserEnemy;

/**
 *
 * @author Totom3
 */
public class LaserProjectile extends Projectile {

	private static final Image LASER_IMAGE = ImageResources.PROJECTILE_LASER.getImage();
	private static final Vec2D LASER_SIZE_CHANGE = new Vec2D(0, GameConstants.LASER_SPEED);

	private long lifetime;
	private RectBounds bounds;
	private final LaserEnemy enemy;
	private final AtomicReference<Vec2D> origin;

	public LaserProjectile(LaserEnemy enemy, AtomicReference<Vec2D> origin, long lifetime) {
		super(false);
		this.enemy = enemy;
		this.origin = origin;
		this.lifetime = lifetime;
		this.bounds = new RectBounds(Vec2D.ZERO, GameConstants.LASER_SIZE);
	}

	@Override
	public boolean tick() {
		if (--lifetime <= 0 || !enemy.isAlive()) {
			return true;
		}

		// Update Y component
		bounds.enlarge(LASER_SIZE_CHANGE);

		// Update X component
		bounds.position(enemy.getPosition(origin.get())
				.plus(GameConstants.ENEMY_SIZE.scale(0.5, 1))
				.minus(GameConstants.LASER_SIZE.scale(0.5, 0)));

		return false;
	}

	@Override
	public void draw(GraphicsContext graphics) {
		graphics.drawImage(LASER_IMAGE, bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
	}

	@Override
	public boolean collidesWith(double x, double y, double width, double height) {
		return bounds.intersects(x, y, x + width, y + height);
	}

	@Override
	public boolean collidesWith(Bounds b) {
		return bounds.intersects(b);
	}

	@Override
	public boolean collidesWith(RectBounds b) {
		return bounds.intersects(b);
	}

	@Override
	public Enemy getCollidedEnemy(GameLogic logic) {
		return null;
	}

}
