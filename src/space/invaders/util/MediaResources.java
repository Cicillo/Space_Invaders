package space.invaders.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Frankie
 */
public enum MediaResources {
	BACKGROUND_MUSIC("audio/background_music.mp3"),
	NORMAL_SHOOT_SOUND("audio/normal_shoot.mp3"),
	SUPER_SHOOT_SOUND("audio/super_shoot.mp3"),
	LASER_SHOOT_SOUND("audio/laser_shoot.mp3"),
	SPINNER_SHOOT_SOUND("audio/spinner_shoot.wav"),
	ENEMY_KILLED_SOUND("audio/enemy_killed.mp3"),
	PLAYER_KILLED_SOUND("audio/player_killed.mp3");

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

	public MediaPlayer playSound() {
		Media media = getMedia();
		if (media == null) {
			return null;
		}

		MediaPlayer player = new MediaPlayer(media);
		player.setOnReady(() -> {
			player.play();
		});

		return player;
	}
}
