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

	private long lifetime;
	private double lowPositionY;

	private final LaserEnemy enemy;
	private final AtomicReference<Vec2D> origin;

	public LaserProjectile(LaserEnemy enemy, AtomicReference<Vec2D> origin, long lifetime) {
		super(false);
		this.enemy = enemy;
		this.origin = origin;
		this.lifetime = lifetime;
	}

	@Override
	public boolean tick() {
		if (--lifetime <= 0 || !enemy.isAlive()) {
			return true;
		}

		// Update Y component
		lowPositionY += GameConstants.LASER_SPEED;
		return false;
	}

	@Override
	public void draw(GraphicsContext graphics) {
		// Get position of laser
		Vec2D pos = enemy.getPosition(origin.get())
				.plus(GameConstants.ENEMY_SIZE.scale(0.5, 1))
				.minus(GameConstants.LASER_SIZE.scale(0.5, 0));

		double height = Math.max(0, lowPositionY - pos.getY());
		graphics.drawImage(LASER_IMAGE, pos.getX(), pos.getY(), GameConstants.LASER_SIZE.getX(), height);
	}

	@Override
	public boolean collidesWith(double x, double y, double width, double height) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean collidesWith(Bounds bounds) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean collidesWith(RectBounds bounds) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Enemy getCollidedEnemy(GameLogic logic) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
