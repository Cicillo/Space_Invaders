/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders.util;

import javafx.scene.image.Image;

/**
 *
 * @author Frankie
 */
public enum ImageResources {

	// 1. Enemy Sprites
	ENEMY_NORMAL("images/enemies/enemy_normal.png"),
	ENEMY_SUPER("images/enemies/enemy_super.png"),
	ENEMY_LASER("images/enemies/enemy_laser.png"),
	TANK_SHIELD_MAX("images/tank_shieldmax.png"),
	TANK_SHIELD_DAMAGED("images/tank_shield_damaged.png"),
	ENEMY_SPINNER("images/enemies/enemy_spinner.png"),
	ENEMY_DUMMY("images/enemies/enemy_dummy.png"),
	//
	// 2. Player & Ally Sprites
	//
	SPACESHIP("images/spaceship.png"),
	SPACESHIP_ICON("images/dummy_spaceship.png"),
	ALLIES("images/allies.png"),
	//
	// 3. Projectiles
	//
	PROJECTILE_PLAYER("images/projectiles/projectile_player.png"),
	PROJECTILE_NORMAL("images/projectiles/projectile_normal.png"),
	PROJECTILE_SUPER("images/projectiles/projectile_super.png"),
	PROJECTILE_LASER("images/projectiles/projectile_laser.png"),
	PROJECTILE_SPINNER("images/projectiles/projectile_spinner.png"),
	//	
	// 4. Misc
	//
	ICON("images/icon.png");

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
