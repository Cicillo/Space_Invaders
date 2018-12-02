/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders.enemies;

import javafx.scene.image.Image;
import space.invaders.GameConstants;
import space.invaders.GameLogic;
import space.invaders.ImageResources;
import space.invaders.IntegerCoordinates;
import space.invaders.Vec2D;
import space.invaders.projectiles.Projectile;

/**
 *
 * @author Frankie
 */
public class SuperEnemy extends Enemy {

	public SuperEnemy(Image image, IntegerCoordinates coords) {
		super(image, coords);
	}
	public SuperEnemy(IntegerCoordinates coords) {
		super(ImageResources.ENEMY_SUPER.getImage(), coords);
	}
	public void tick(GameLogic logic) {

	}
		
	public boolean onHit(Projectile proj) {
		//TO DO:implement collision logic
		return true;
	}
}

