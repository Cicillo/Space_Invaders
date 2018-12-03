package space.invaders.enemies;

import javafx.scene.image.Image;
import space.invaders.GameLogic;
import space.invaders.util.ImageResources;
import space.invaders.util.IntegerCoordinates;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Totom3
 */
public class DummyEnemy extends Enemy {

	private static final Image IMAGE = ImageResources.ENEMY_DUMMY.getImage();

	public DummyEnemy(IntegerCoordinates coords) {
		super(IMAGE, coords);
	}

	@Override
	public void tick(GameLogic logic) {
		// Do nothing
	}

	@Override
	public boolean onHit(Projectile proj) {
		alive = false;
		return true;
	}

}
