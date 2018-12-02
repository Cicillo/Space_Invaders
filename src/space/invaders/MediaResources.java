/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

import javafx.scene.media.Media;

/**
 *
 * @author Frankie
 */
public enum MediaResources {
	BACKGROUND_MUSIC("audio/background_music.mp3");
	
	private final String url;

	private MediaResources(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return url;
	}
	
	public Media getClip() {
		return AssetManager.getSound(this);
	}
}
