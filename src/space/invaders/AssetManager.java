package space.invaders;

/**
 *
 * @author Frankie
 */
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class AssetManager {

	private static boolean loadedResources;

	private static final Map<ImageResources, Image> imageMap = new EnumMap<>(ImageResources.class);
	private static final Map<AudioResources, AudioClip> audioMap = new EnumMap<>(AudioResources.class);

	public static Image getImage(ImageResources image) {
		return imageMap.get(image);
	}

	public static AudioClip getSound(AudioResources audio) {
		return audioMap.get(audio);
	}

	public static void loadResources() {
		if (loadedResources)
			throw new IllegalStateException("resources already loaded");

		// TODO: load resources
		loadedResources = true;
	}

}
