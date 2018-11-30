package space.invaders.projectiles;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.ImageResources;
import space.invaders.RectBounds;
import space.invaders.Vec2D;

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

	@Override
	public void tick() {
		bounds.move(velocity);
	}

	@Override
	public void draw(GraphicsContext graphics) {
		graphics.drawImage(image, bounds.getMinX(), bounds.getMinY());
	}

	@Override
	public boolean collidesWith(Bounds bounds) {
		return bounds.intersects(bounds);
	}

	@Override
	public boolean collidesWith(RectBounds bounds) {
		return bounds.intersects(bounds);
	}

	@Override
	public boolean collidesWith(double x, double y, double width, double height) {
		return bounds.intersects(x, y, x + width, y + height);
	}

}
