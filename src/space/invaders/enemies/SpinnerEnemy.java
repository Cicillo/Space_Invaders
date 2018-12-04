package space.invaders.enemies;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.projectiles.NormalProjectile;
import space.invaders.projectiles.Projectile;
import space.invaders.util.AnimationResources;
import space.invaders.util.ImageAnimation;
import space.invaders.util.IntegerCoordinates;
import space.invaders.util.MediaResources;
import space.invaders.util.RectBounds;
import space.invaders.util.Vec2D;

/**
 *
 * @author Totom3
 */
public class SpinnerEnemy extends Enemy {

	private static long getCooldown(Random rand) {
		// Random cooldown between 18 and 25 seconds
		return (long) ((18 + 7 * rand.nextDouble()) * GameConstants.GAME_TPS);
	}

	private static final ImageAnimation PROJECTILE_ANIMATION;

	static {
		PROJECTILE_ANIMATION = new ImageAnimation(AnimationResources.PROJECTILE_SPINNER.getAnimation());
		PROJECTILE_ANIMATION.play();
	}

	private long shootingTimer = -1;
	private long cooldownTimer = (long) (25 * GameConstants.GAME_TPS * Math.random());

	public SpinnerEnemy(IntegerCoordinates coords) {
		super(AnimationResources.ENEMY_SPINNER, coords);
	}

	@Override
	public void tick(GameLogic logic) {
		if (shootingTimer >= 0) {
			shoot(logic);
			return;
		}

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

		shootingTimer = 1 + (GameConstants.SPINNER_SHOOT_COUNT * GameConstants.SPINNER_SHOOT_DELAY);
	}

	private void shoot(GameLogic logic) {
		// Update shooting timer
		--shootingTimer;

		// Check for end of shooting
		if (shootingTimer <= 0) {
			// Done shooting; set cooldown.
			cooldownTimer = getCooldown(logic.getRandom());
			return;
		}

		// Delay inbetween pellets
		if (shootingTimer % GameConstants.SPINNER_SHOOT_DELAY != 0) {
			return;
		}

		Vec2D position = getPosition(logic.getEnemyPosition());

		// Shoot pellets
		double slope = 2 * GameConstants.SPINNER_MAX_ANGLE / (GameConstants.SPINNER_PELLET_COUNT - 1);
		double offset = -(GameConstants.SPINNER_MAX_ANGLE + Math.PI / 2);
		for (int i = 0; i < GameConstants.SPINNER_PELLET_COUNT; ++i) {
			// Compute angle
			double angle = offset + (i * slope);

			// Compute velocity
			Vec2D velocity = new Vec2D(Math.cos(angle), -Math.sin(angle)).scale(GameConstants.PROJECTILE_SPEED);

			// Get position of pellet
			Vec2D pelletPosition = position
					.plus(GameConstants.ENEMY_SIZE.scale(0.5, 1))
					.minus(GameConstants.SPINNER_PELLET_SIZE.scale(0.5, 0));

			RectBounds bounds = new RectBounds(pelletPosition, GameConstants.SPINNER_PELLET_SIZE);
			NormalProjectile proj = new NormalProjectile(false, bounds, velocity, null) {

				@Override
				public void draw(GraphicsContext graphics, Pane pane) {
					RectBounds projBounds = getBounds();
					graphics.drawImage(PROJECTILE_ANIMATION.getCurrentImage(), projBounds.getMinX(), projBounds.getMinY());
				}

			};
			logic.addProjectile(proj);
		}

		MediaResources.SPINNER_SHOOT_SOUND.playSound(0.6);
	}

	@Override
	public boolean onHit(Projectile proj) {
		return true;
	}

}
