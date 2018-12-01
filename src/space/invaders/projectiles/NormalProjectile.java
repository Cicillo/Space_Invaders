package space.invaders.projectiles;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.ImageResources;
import space.invaders.RectBounds;
import space.invaders.Vec2D;
import space.invaders.enemies.Enemy;

/**
 *
 * @author Totom3
 */
public class NormalProjectile extends Projectile {

	public static final Vec2D DEFAULT_VELOCITY = new Vec2D(0, GameConstants.PROJECTILE_SPEED);
	public static final Image DEFAULT_IMAGE = ImageResources.PROJECTILE_NORMAL.getImage();

	private final Image image;
	private final Vec2D velocity;
	private final RectBounds bounds;

	public NormalProjectile(boolean friendly, RectBounds bounds) {
		this(friendly, bounds, DEFAULT_VELOCITY, DEFAULT_IMAGE);
	}

	public NormalProjectile(boolean friendly, RectBounds bounds, Vec2D velocity, Image image) {
		super(friendly);
		this.bounds = bounds;
		this.velocity = velocity;
		this.image = image;
	}

	public RectBounds getBounds() {
		return bounds;
	}

	@Override
	public boolean tick() {
		bounds.move(velocity);
		return bounds.getMinY() <= 0;
	}

	@Override
	public void draw(GraphicsContext graphics) {
		graphics.drawImage(image, bounds.getMinX(), bounds.getMinY());
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
	public boolean collidesWith(double x, double y, double width, double height) {
		return bounds.intersects(x, y, x + width, y + height);
	}

	@Override
	public Enemy getCollidedEnemy(GameLogic logic) {
		Vec2D origin = logic.getEnemyPosition();
		Vec2D gridPosition = bounds.getPosition().minus(origin).divide(GameConstants.ENEMY_DELTA);
		int gridX = (int) gridPosition.getX();
		int gridY = (int) gridPosition.getY();

		// Check enemies near the detected grid cell
		for (int i = 0; i <= 1; ++i) {
			for (int j = 0; j <= 1; ++j) {
				Enemy enemy = logic.getEnemy(gridX + i, gridY + j);
				if (enemy != null && collidesWith(enemy.getBounds(origin))) {
					return enemy;
				}
			}
		}
		return null;
	}

}
