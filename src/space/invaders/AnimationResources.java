package space.invaders;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author Frankie
 */
public enum AnimationResources {

	// 1. Enemy Sprites
	ENEMY_SPINNER(240, "animations/spinner/"),
	ENEMY_LASER(240, "animations/laser/"),
	ENEMY_TANK(240, "animations/tank/");

	private final int frames;
	private final String url;

	private AnimationResources(int frames, String url) {
		this.frames = frames;
		this.url = url;
	}

	public int getFrames() {
		return frames;
	}

	public String getURL() {
		return url;
	}

	public ObservableList<Image> getAnimation() {
		return AssetManager.getAnimation(this);
	}
}
