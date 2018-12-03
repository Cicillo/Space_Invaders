package space.invaders.util;

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

	public Media getMedia() {
		return AssetManager.getSound(this);
	}
}
