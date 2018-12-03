package space.invaders.util;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author Frankie
 */
public enum AnimationResources {

	// 1. Enemy Sprites
	ENEMY_SPINNER(240, "animations/spinner/", "png"),
	ENEMY_LASER(240, "animations/laser/", "png"),
	ENEMY_TANK(240, "animations/tank/", "png"),
	BACKGROUND(301, "animations/background/", "jpg");

	private final int frames;
	private final String url;
	private final String format;

	private AnimationResources(int frames, String url, String format) {
		this.frames = frames;
		this.format = format;
		this.url = url;
	}

	public int getFrames() {
		return frames;
	}

	public String getFormat() {
		return format;
	}

	public String getURL() {
		return url;
	}

	public ObservableList<Image> getAnimation() {
		return AssetManager.getAnimation(this);
	}
}
