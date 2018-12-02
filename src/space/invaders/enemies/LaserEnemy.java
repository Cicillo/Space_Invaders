package space.invaders.enemies;

import java.util.Random;
import space.invaders.AnimationResources;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.IntegerCoordinates;
import space.invaders.projectiles.LaserProjectile;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Totom3
 */
public class LaserEnemy extends Enemy {

	private static long getCooldown(Random rand) {
		// Random cooldown between 10 and 15 seconds
		return (long) ((10 + 5 * rand.nextDouble()) * GameConstants.GAME_TPS);
	}

	private long cooldownTimer = -1;

	public LaserEnemy(IntegerCoordinates coords) {
		super(AnimationResources.ENEMY_LASER, coords);
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

		// Shoot projectile
		LaserProjectile proj = new LaserProjectile(this, logic.getEnemyPositionRef(), GameConstants.LASER_LIFETIME);
		logic.addProjectile(proj);

		// Update cooldown timer
		cooldownTimer = getCooldown(logic.getRandom());
	}

	@Override
	public boolean onHit(Projectile proj) {
		alive = false;
		return true;
	}

}
