/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

/**
 *
 * @author Frankie
 */
public enum AudioResources {
	BACKGROUND_MUSIC("./assets/images/background_music.wav"), 
	SPACESHIP_SHOOT("./assets/images/spaceship_shoot.wav"), 
	ALIEN_SHOOT("./assets/images/alien_shoot.wav"), 
	SHOT_HIT("./assets/images/shot_hit.wav"), 
	DEATH("./assets/images/death.wav"), 
	BOSS_INTRO("./assets/images/boss_intro.wav");
	
	private final String url;

	private AudioResources(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return url;
	}
}
