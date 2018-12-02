/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders.enemies;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;
import space.invaders.GameLogic;
import space.invaders.ImageResources;
import space.invaders.IntegerCoordinates;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Frankie
 */
public class NormalEnemy extends Enemy{
	public NormalEnemy(Image image, IntegerCoordinates coords) {
		super(image, coords);
	}
	
	public NormalEnemy(IntegerCoordinates coords) {
		super(ImageResources.ENEMY_NORMAL.getImage(), coords);
	}

	public void tick(GameLogic logic) {
		//TO DO: implement onShoot logic for normal enemy 
		
		//RNGesus to determine which enemy is firing. RNG to pick the index. Said enemy then fires
		int randomEnemyShooter = ThreadLocalRandom.current().nextInt(0, 12); //chooses between index 0 and 11
		//enemies[0][randomEnemyShooter] = shooter;
		//get position of this shooter
		//make a projectile using said position
		
		/*private final Rectangle spaceship;
		Vec2D position = new Vec2D(spaceship.getX() + spaceship.getWidth() / 2, spaceship.getY() + spaceship.getHeight() / 2);
		RectBounds projectileBounds = new RectBounds(position, GameConstants.PROJECTILE_SIZE);
		Vec2D velocity = new Vec2D(0, GameConstants.PROJECTILE_SPEED);
		NormalProjectile playerProjectile = new NormalProjectile(true, projectileBounds, velocity, NormalProjectile.DEFAULT_IMAGE);
		space.invaders.GamePane.gameLogic.addProjectile(playerProjectile);
		});*/
	}
		

	public boolean onHit(Projectile proj) {
		//TO DO:implement collision logic
		return true;
	}
}
