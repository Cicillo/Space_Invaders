package space.invaders.enemies;

import java.util.Random;
import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.util.ImageResources;
import space.invaders.util.IntegerCoordinates;
import space.invaders.util.RectBounds;
import space.invaders.util.Vec2D;
import space.invaders.projectiles.NormalProjectile;
import space.invaders.util.MediaResources;

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
    protected void shootProjectile(GameLogic logic) {
        // Shoot projectile
        Vec2D pos1 = getPosition(logic.getEnemyPosition())
                .plus(GameConstants.ENEMY_SIZE.scale(0.5, 1))
                .minus(GameConstants.PROJECTILE_SIZE.scale(1, 0));

        RectBounds bounds1 = new RectBounds(pos1, GameConstants.PROJECTILE_SIZE);
        NormalProjectile proj1 = new NormalProjectile(false, bounds1, new Vec2D(0, GameConstants.PROJECTILE_SPEED_SUPER), PROJECTILE_IMAGE);
        logic.addProjectile(proj1);

        Vec2D pos2 = pos1.plus(GameConstants.PROJECTILE_SIZE.scale(2, 0));
        RectBounds bounds2 = new RectBounds(pos2, GameConstants.PROJECTILE_SIZE);
        NormalProjectile proj2 = new NormalProjectile(false, bounds2, new Vec2D(0, GameConstants.PROJECTILE_SPEED_SUPER), PROJECTILE_IMAGE);
        logic.addProjectile(proj2);

        MediaResources.ENEMY_SHOOT_SOUND.playSound();
    }

    @Override
    protected long getCooldown(Random rand) {
        // Random cooldown between 8 and 10 seconds
        return (long) ((8 + 10 * rand.nextDouble()) * GameConstants.GAME_TPS);
    }

}
