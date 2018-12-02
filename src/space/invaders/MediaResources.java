package space.invaders;

import javafx.scene.media.Media;

/**
 *
 * @author Frankie
 */
public enum MediaResources {
	BACKGROUND_MUSIC("audio/background_music.mp3"),
	TANK_ANIMATION("images/enemies/tank_video.m4v"),
	SPINNER_ANIMATION("images/enemies/spinner_video.m4v"),
	BACKGROUND_ANIMATION("images/background_animation.mp4");

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
