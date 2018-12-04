package space.invaders.enemies;

import java.util.Random;
import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.projectiles.NormalProjectile;
import space.invaders.projectiles.Projectile;
import space.invaders.util.AnimationResources;
import space.invaders.util.ImageResources;
import space.invaders.util.IntegerCoordinates;
import space.invaders.util.MediaResources;
import space.invaders.util.RectBounds;
import space.invaders.util.Vec2D;

/**
 *
 * @author Tomer Moran
 */
public class NormalEnemy extends Enemy {

	private static final Image PROJECTILE_IMAGE = ImageResources.PROJECTILE_NORMAL.getImage();

	private long cooldownTimer = (long) (14 * GameConstants.GAME_TPS * Math.random());

	public NormalEnemy(IntegerCoordinates coords) {
		super(AnimationResources.ENEMY_NORMAL, coords);
	}

	protected NormalEnemy(AnimationResources res, IntegerCoordinates coords) {
		super(res, coords);
	}

	@Override
	public void tick(GameLogic logic) {
		// Initialize cooldown
		if (cooldownTimer < 0) {
			cooldownTimer = getCooldown(logic.getRandom());
			return;
		}

		// Check cooldown
		if (cooldownTimer > 0) {
			--cooldownTimer;
			return;
		}

		shootProjectile(logic);

		// Update cooldown timer
		cooldownTimer = getCooldown(logic.getRandom());
	}

	protected void shootProjectile(GameLogic logic) {
		// Shoot projectile
		Vec2D pos = getPosition(logic.getEnemyPosition())
				.plus(GameConstants.ENEMY_SIZE.scale(0.5, 1))
				.minus(GameConstants.PROJECTILE_SIZE.scale(0.5, 0));

		RectBounds bounds = new RectBounds(pos, GameConstants.PROJECTILE_SIZE);

		NormalProjectile proj = new NormalProjectile(false, bounds, new Vec2D(0, GameConstants.PROJECTILE_SPEED), PROJECTILE_IMAGE);
		logic.addProjectile(proj);

		MediaResources.NORMAL_SHOOT_SOUND.playSound(1);
	}

	@Override
	public boolean onHit(Projectile proj) {
		//TO DO:implement collision logic
		return true;
	}

	protected long getCooldown(Random rand) {
		// Random cooldown between 8 and 14 seconds
		return (long) ((8 + 6 * rand.nextDouble()) * GameConstants.GAME_TPS);
	}

}
