package space.invaders.enemies;

import java.util.Random;
import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.ImageResources;
import space.invaders.IntegerCoordinates;

/**
 *
 * @author Frankie
 */
public class SuperEnemy extends NormalEnemy {

	private static final Image SUPER_IMAGE = ImageResources.ENEMY_SUPER.getImage();

	public SuperEnemy(IntegerCoordinates coords) {
		super(SUPER_IMAGE, coords);
	}

	@Override
	protected long getCooldown(Random rand) {
		// Random cooldown between 3 and 6 seconds
		return (long) ((3 + 3 * rand.nextDouble()) * GameConstants.GAME_TPS);
	}

}
