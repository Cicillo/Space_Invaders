package space.invaders.enemies;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.IntegerCoordinates;
import space.invaders.RectBounds;
import space.invaders.Vec2D;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Totom3
 */
public abstract class Enemy {

	public static int getEnemyKillScore(Enemy e) {
		if (e instanceof LaserEnemy) {
			return GameConstants.SCORE_LASER_KILLED;
		}

		if (e instanceof SpinnerEnemy) {
			return GameConstants.SCORE_SPINNER_KILLED;
		}

		if (e instanceof TankEnemy) {
			return GameConstants.SCORE_TANK_KILLED;
		}

		if (e instanceof DummyEnemy) {
			return GameConstants.SCORE_DUMMY_KILLED;
		}

		throw new IllegalArgumentException();
	}

	protected boolean alive;
	protected volatile Image image;
	private final IntegerCoordinates coords;

	protected volatile Media media;
	protected volatile MediaPlayer mediaPlayer;
	protected volatile MediaView mediaView;

	public Enemy(Image image, IntegerCoordinates coords) {
		this.alive = true;
		this.image = image;
		this.coords = coords;
	}

	public Enemy(Media media, IntegerCoordinates coords) {
		this.alive = true;
		this.media = media;
		this.coords = coords;
	}

	/**
	 *
	 * @return {@code true} if this enemy is alive, {@code false} otherwise.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 *
	 * @return the visual image of this enemy.
	 */
	public Image getImage() {
		return image;
	}

	public boolean hasMedia() {
		return media != null;
	}

	public Media getMedia() {
		return media;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public MediaView getMediaView() {
		return mediaView;
	}

	public boolean initializeMedia(DoubleBinding xBinding, DoubleBinding yBinding) {
		if (media == null)
			return false;

		mediaPlayer = new MediaPlayer(media);
		mediaView = new MediaView(mediaPlayer);
		mediaPlayer.setOnReady(() -> {
			// Initialize media player
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();

			// Initialize media view
			mediaView.xProperty().bind(xBinding.add(GameConstants.ENEMY_DELTA.scale(coords.getX()).getX()));
			mediaView.yProperty().bind(yBinding.add(GameConstants.ENEMY_DELTA.scale(coords.getY()).getY()));
		});

		return true;
	}

	/**
	 *
	 * @return the grid coordinates of this enemy, relative to the top left.
	 */
	public IntegerCoordinates getCoordinates() {
		return coords;
	}

	/**
	 * Returns the position of this enemy.
	 *
	 * @param origin the position of the origin enemy.
	 * @return the position of this enemy.
	 */
	public Vec2D getPosition(Vec2D origin) {
		return origin.plus(GameConstants.ENEMY_DELTA.scale(coords.getX(), coords.getY()));
	}

	/**
	 * Returns the bounds of this enemy.
	 *
	 * @param origin the position of the origin enemy.
	 * @return the bounding box of this enemy.
	 */
	public RectBounds getBounds(Vec2D origin) {
		return new RectBounds(getPosition(origin), GameConstants.ENEMY_SIZE);
	}

	/**
	 * Ticks this enemy.
	 *
	 * @param logic the game logic instance.
	 */
	public abstract void tick(GameLogic logic);

	/**
	 * Handles any logic that must be handled when hit by a projectile, and
	 * returns whether or not this enemy is killed.
	 *
	 * @param proj the projectile that hit the enemy.
	 * @return {@code true} if this enemy is now dead, {@code false} otherwise.
	 */
	public abstract boolean onHit(Projectile proj);
}
