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

	double ALIEN_SIZE = 50;
	double ALIEN_SPACING_H = 35;
	double ALIEN_SPACING_V = 30;
	
	double SPACESHIP_Y = SCREEN_HEIGHT - 50;
	double SPACESHIP_WIDTH = 60;
	double SPACESHIP_HEIGHT = 40;
	
	
	Vec2D START_ALIEN_POSITION = new Vec2D(LEFT_GAME_BOUND, 120);
	Vec2D ALIEN_MOVE_LEFT = new Vec2D(20, 0);
	Vec2D ALIEN_MOVE_RIGHT = new Vec2D(-20, 0);
	Vec2D ALIEN_MOVE_DOWN = new Vec2D(0, 20);
}
