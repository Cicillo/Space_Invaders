package space.invaders;

/**
 *
 * @author Frankie
 */
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class AssetManager {

	static {
		System.out.println("Loading resources...");
		imageMap = new EnumMap<>(ImageResources.class);
		audioMap = new EnumMap<>(AudioResources.class);
		loadResources();
		System.out.println("Finished loading resources!");
	}

	private static boolean loadedResources;

	private static final Map<ImageResources, Image> imageMap;
	private static final Map<AudioResources, AudioClip> audioMap;

	public static Image getImage(ImageResources image) {
		return imageMap.get(image);
	}

	public static AudioClip getSound(AudioResources audio) {
		return audioMap.get(audio);
	}

	public static void loadResources() {
		if (loadedResources)
			throw new IllegalStateException("resources already loaded");

		loadedResources = true;

		for (ImageResources res : ImageResources.values()) {
			InputStream in = AssetManager.class.getClassLoader().getResourceAsStream(res.getURL());
			if (in == null) {
				System.err.println("Could not load resource " + res + ": no such asset '" + res.getURL() + "'");
				continue;
			}

			Image image = new Image(in);
			Exception ex = image.getException();
			if (ex != null) {
				System.err.println("Could not load resource " + res + ": ");
				ex.printStackTrace(System.err);
			} else {
				imageMap.put(res, image);
				System.out.println("Loaded " + res + "!");
			}
		}

		// TODO: load audio clips
	}

	private AssetManager() {
	}

}
