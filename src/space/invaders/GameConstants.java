package space.invaders;

/**
 *
 * @author Tomer Moran
 */
public interface GameConstants {

	int SCREEN_WIDTH = 1200;
	int SCREEN_HEIGHT = 900;

	int ALIENS_GRID_LENGTH = 11;
	int ALIENS_GRID_HEIGHT = 5;

	double LEFT_GAME_BOUND = 20;
	double RIGHT_GAME_BOUND = SCREEN_WIDTH - 20;

	double ALIEN_SIZE = 60;
	double ALIEN_SPACING_H = 15;
	double ALIEN_SPACING_V = 25;
	
	Vec2D START_ALIEN_POSITION = new Vec2D(LEFT_GAME_BOUND, 200);
	Vec2D ALIEN_MOVE_LEFT = new Vec2D(20, 0);
	Vec2D ALIEN_MOVE_RIGHT = new Vec2D(-20, 0);
	Vec2D ALIEN_MOVE_DOWN = new Vec2D(0, 20);
}
