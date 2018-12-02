package space.invaders.enemies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import space.invaders.AnimationResources;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.ImageResources;
import space.invaders.IntegerCoordinates;
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
			shieldOverlay.xProperty().bind(animation.xProperty());
			shieldOverlay.yProperty().bind(animation.yProperty());
			pane.getChildren().add(shieldOverlay);
		}

		return success;
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
