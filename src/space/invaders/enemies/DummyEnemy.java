package space.invaders.enemies;

import javafx.scene.image.Image;
import space.invaders.GameLogic;
import space.invaders.ImageResources;
import space.invaders.IntegerCoordinates;
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
		return true;
	}

}
