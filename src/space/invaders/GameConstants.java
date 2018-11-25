package space.invaders;

/**
 *
 * @author Tomer Moran
 */
public interface GameConstants {

	Vec2D SCREEN_SIZE = new Vec2D(1200, 900);

	int ALIENS_GRID_LENGTH = 11;
	int ALIENS_GRID_HEIGHT = 5;

	double LEFT_GAME_BOUND = 20;
	double RIGHT_GAME_BOUND = SCREEN_SIZE.getX() - 20;

	Vec2D ALIEN_SIZE = new Vec2D(50, 50);
	Vec2D ALIEN_SPACING = new Vec2D(30, 35);
	Vec2D SPACESHIP_SIZE = new Vec2D(60, 40);
	double SPACESHIP_Y = SCREEN_SIZE.getY() - 50;

	// Game ticks per second
	double GAME_TPS = 100;

	// Frames per second
	double RENDER_FPS = 60;

	// Start position of aliens
	Vec2D START_ALIEN_POSITION = new Vec2D(LEFT_GAME_BOUND, 120);

	// Horizontal speed of aliens, in pixels per second.
	Vec2D ALIEN_MOVEMENT_SPEED = new Vec2D(20, 0).divide(GAME_TPS);

	// The vertical jump aliens do when reaching the end of the screen
	Vec2D ALIEN_DOWN_JUMP = new Vec2D(0, 20);
}
