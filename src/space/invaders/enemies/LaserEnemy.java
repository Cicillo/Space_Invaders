package space.invaders.enemies;

import java.util.Random;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.projectiles.LaserProjectile;
import space.invaders.projectiles.Projectile;
import space.invaders.util.AnimationResources;
import space.invaders.util.IntegerCoordinates;
import space.invaders.util.MediaResources;

/**
 *
 * @author Totom3
 */
public class LaserEnemy extends Enemy {

    private static long getCooldown(Random rand) {
        // Random cooldown between 10 and 15 seconds
        return (long) ((10 + 5 * rand.nextDouble()) * GameConstants.GAME_TPS);
    }

    private long cooldownTimer = (long) (15 * GameConstants.GAME_TPS * Math.random());

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

        MediaResources.LASER_SHOOT_SOUND.playSound(0.5);

        // Update cooldown timer
        cooldownTimer = getCooldown(logic.getRandom());
    }

    @Override
    public boolean onHit(Projectile proj) {
        alive = false;
        return true;
    }

}
