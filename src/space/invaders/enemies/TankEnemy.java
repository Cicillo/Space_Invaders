package space.invaders.enemies;

import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.ImageResources;
import space.invaders.IntegerCoordinates;
import space.invaders.MediaResources;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Totom3
 */
public class TankEnemy extends Enemy {

	private static final Image[] IMAGES = {
		ImageResources.ENEMY_TANK_0.getImage(), ImageResources.ENEMY_TANK_1.getImage(), ImageResources.ENEMY_TANK_2.getImage()
	};

	private int shieldCapacity;

	public TankEnemy(IntegerCoordinates coords) {
		super(MediaResources.TANK_ANIMATION.getMedia(), coords);
		this.shieldCapacity = GameConstants.TANK_CAPACITY;
	}

	@Override
	public void tick(GameLogic logic) {
		// Do nothing
	}

	@Override
	public boolean onHit(Projectile proj) {
		// If no shield, the tank is dead.
		if (shieldCapacity == 0)
			return true;

		// Reduce shield capacity
		--shieldCapacity;

		// Update media
		//this.image = IMAGES[shieldCapacity];

		return false;
	}

}
