package space.invaders.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Frankie
 */
public enum MediaResources {
	BACKGROUND_MUSIC("audio/background_music.mp3"),
	PLAYER_SHOOT_SOUND("audio/player_shoot.wav"),
	ENEMY_SHOOT_SOUND("audio/enemy_shoot.wav"),
	LASER_SHOOT_SOUND("audio/laser_shoot.wav"),
	SPINNER_SHOOT_SOUND("audio/spinner_shoot.wav"),
	ENEMY_KILLED_SOUND("audio/enemy_killed.wav"),
	PLAYER_KILLED_SOUND("audio/player_killed.wav");

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
