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
	 * The size of an enemy.
	 */
	Vec2D ENEMY_SIZE = new Vec2D(50, 50);

	/**
	 * The spacing between adjacent enemies.
	 */
	Vec2D ENEMY_SPACING = new Vec2D(30, 25);

	/**
	 * The sum of the size of an enemy and the spacing between enemies.
	 */
	Vec2D ENEMY_DELTA = ENEMY_SIZE.plus(ENEMY_SPACING);

	/**
	 * The size of the spaceship.
	 */
	Vec2D SPACESHIP_SIZE = new Vec2D(60, 40);

	/**
	 * Left bound of the game area. Enemies bounce when they reach this.
	 */
	double LEFT_GAME_BOUND = 20;

	/**
	 * Top bound of the game area. Defines the starting position of enemies.
	 */
	double TOP_GAME_BOUND = 120;

	/**
	 * Right bound of the game area. Enemies bounce when they reach this.
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
	 * The number of enemies in a row.
	 */
	int ENEMIES_GRID_LENGTH = 11;

	/**
	 * The number of enemies in a column.
	 */
	int ENEMIES_GRID_HEIGHT = 5;

	/**
	 * Number of game ticks per second.
	 */
	double GAME_TPS = 100;

	/**
	 * Number of rendering ticks per second.
	 */
	double RENDER_FPS = 60;

	/**
	 * The starting position of enemies: at the left and top game bounds.
	 */
	Vec2D START_ENEMIES_POSITION = new Vec2D(LEFT_GAME_BOUND, TOP_GAME_BOUND);

	/**
	 * The horizontal movement speed of enemies, in pixels per second. Value
	 * adjusted so that it does not affected by {@link #GAME_TPS}.
	 */
	Vec2D ENEMY_MOVEMENT_SPEED = new Vec2D(50, 0).divide(GAME_TPS);

	/**
	 * The vertical jump enemies take when they reach the game bounds. The jump
	 * is instantaneous and therefore does not depend on {@link #GAME_TPS}.
	 */
	Vec2D ENEMY_DOWN_JUMP = new Vec2D(0, (ENEMY_SIZE.getY() + ENEMY_SPACING.getY()) / 2);

	/**
	 * The speed at which enemy projectiles move, in pixels per second.
	 */
	double PROJECTILE_SPEED = 200 / GAME_TPS;

	/**
	 * The speed at which friendly (allied + player) projectiles move, in pixels
	 * per second.
	 */
	double PROJECTILE_SPEED_FRIENDLY = -400 / GAME_TPS;

	/**
	 * The size of a normal projectile.
	 */
	Vec2D PROJECTILE_SIZE = new Vec2D(7, 25);

	/**
	 * Delay between player shoots.
	 */
	long SHOOT_DELAY = 750;

}
