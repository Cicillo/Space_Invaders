/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

import javafx.scene.image.Image;

/**
 *
 * @author Frankie
 */
public enum ImageResources {

	// 1. Enemy Sprites
	ENEMY_NORMAL("./assets/images/enemies/enemy_normal.png"),
	ENEMY_SUPER("./assets/images/enemies/enemy_super.png"),
	ENEMY_LASER("./assets/images/enemies/enemy_laser.png"),
	ENEMY_TANK("./assets/images/enemy_tank.png"),
	ENEMY_TANK_NS("./assets/images/enemies/enemy_tank_noshield.png"),
	ENEMY_SPINER("./assets/images/enemies/enemy_spiner.png"),
	//
	// 2. Player & Ally Sprites
	//
	SPACESHIP("./assets/images/spaceship.png"),
	ALLIES("./assets/images/allies.png"),
	//
	// 3. Projectiles
	//
	PROJECTILE_NORMAL("./assets/images/projectiles/projectile_normal.png");

	private final String url;

	private ImageResources(String url) {
		this.url = url;
	}

	public String getURL() {
		return url;
	}

	public Image getImage() {
		return AssetManager.getImage(this);
	}
}
