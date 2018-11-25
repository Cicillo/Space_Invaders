package space.invaders;

/**
 *
 * @author Tomer Moran
 */
public interface GameConstants {

	/**
	 * The size of the screen.
	 */
	Vec2D SCREEN_SIZE = new Vec2D(1200, 900);

	/**
	 * The size of an alien.
	 */
	Vec2D ALIEN_SIZE = new Vec2D(50, 50);

	/**
	 * The spacing between adjacent aliens.
	 */
	Vec2D ALIEN_SPACING = new Vec2D(30, 35);

	/**
	 * The size of the spaceship.
	 */
	Vec2D SPACESHIP_SIZE = new Vec2D(60, 40);

	/**
	 * Left bound of the game area. Aliens bounce when they reach this.
	 */
	double LEFT_GAME_BOUND = 20;

	/**
	 * Top bound of the game area. Defines the starting position of aliens.
	 */
	double TOP_GAME_BOUND = 120;

	/**
	 * Right bound of the game area. Aliens bounce when they reach this.
	 */
	double RIGHT_GAME_BOUND = SCREEN_SIZE.getX() - 20;

	/**
	 * Bottom bound of the game area. This is where the spaceship moves around.
	 */
	double BOTTOM_GAME_BOUND = SCREEN_SIZE.getY() - 50;

	/**
	 * The Y component of the spaceship.
	 */
	double SPACESHIP_Y = BOTTOM_GAME_BOUND;

	/**
	 * The number of aliens in a row.
	 */
	int ALIENS_GRID_LENGTH = 11;

	/**
	 * The number of aliens in a column.
	 */
	int ALIENS_GRID_HEIGHT = 5;

	/**
	 * Number of game ticks per second.
	 */
	double GAME_TPS = 100;

	/**
	 * Number of rendering ticks per second.
	 */
	double RENDER_FPS = 60;

	/**
	 * The starting position of aliens: at the left and top game bounds.
	 */
	Vec2D START_ALIEN_POSITION = new Vec2D(LEFT_GAME_BOUND, TOP_GAME_BOUND);

	/**
	 * The horizontal movement speed of aliens, in pixels per second. Value
	 * adjusted so that it does not affected by {@link #GAME_TPS}.
	 */
	Vec2D ALIEN_MOVEMENT_SPEED = new Vec2D(50, 0).divide(GAME_TPS);

	/**
	 * The vertical jump aliens take when they reach the game bounds. The jump
	 * is instantaneous and therefore does not depend on {@link #GAME_TPS}.
	 */
	Vec2D ALIEN_DOWN_JUMP = new Vec2D(0, 20);
}
