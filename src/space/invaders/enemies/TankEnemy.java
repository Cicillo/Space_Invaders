package space.invaders.enemies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import space.invaders.util.AnimationResources;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.util.ImageResources;
import space.invaders.util.IntegerCoordinates;
import space.invaders.util.Vec2D;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Totom3
 */
public class TankEnemy extends Enemy {

	private static final Image SHIELD_MAX = ImageResources.TANK_SHIELD_MAX.getImage();
	private static final Image SHIELD_DAMAGED = ImageResources.TANK_SHIELD_DAMAGED.getImage();

	private int shieldCapacity;
	private ImageView shieldOverlay;

	public TankEnemy(IntegerCoordinates coords) {
		super(AnimationResources.ENEMY_TANK, coords);
		this.shieldCapacity = GameConstants.TANK_CAPACITY;
	}

	@Override
	public void tick(GameLogic logic) {
		// Do nothing
	}

	@Override
	public boolean initializeAnimation(Pane pane) {
		boolean success = super.initializeAnimation(pane);
		if (success) {
			shieldOverlay = new ImageView(SHIELD_MAX);
			pane.getChildren().add(shieldOverlay);
		}

		return success;
	}

	@Override
	public void moveAnimation(Vec2D origin) {
		super.moveAnimation(origin);
		if (shieldCapacity > 0) {
			shieldOverlay.xProperty().set(origin.getX() + GameConstants.ENEMY_DELTA.getX() * getCoordinates().getX());
			shieldOverlay.yProperty().set(origin.getY() + GameConstants.ENEMY_DELTA.getY() * getCoordinates().getY());
		}
	}

	@Override
	public boolean onHit(Projectile proj) {
		// If no shield, the tank is dead.
		if (shieldCapacity == 0)
			return true;

		// Reduce shield capacity
		--shieldCapacity;

		// Update image
		if (shieldCapacity == 1) {
			shieldOverlay.setImage(SHIELD_DAMAGED);
		} else if (shieldCapacity == 0) {
			shieldOverlay.setVisible(false);
		}
		return false;
	}

}
