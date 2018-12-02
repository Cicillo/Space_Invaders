package space.invaders;

/**
 *
 * @author Frankie
 */
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class AssetManager {

	static {
		System.out.println("Loading resources...");
		imageMap = new EnumMap<>(ImageResources.class);
		mediaMap = new EnumMap<>(MediaResources.class);
		loadResources();
		System.out.println("Finished loading resources!");
	}

	private static boolean loadedResources;

	private static final Map<ImageResources, Image> imageMap;
	private static final Map<MediaResources, Media> mediaMap;

	public static Image getImage(ImageResources image) {
		return imageMap.get(image);
	}

	public static Media getSound(MediaResources media) {
		return mediaMap.get(media);
	}

	public static void loadResources() {
		if (loadedResources)
			throw new IllegalStateException("resources already loaded");

		loadedResources = true;

		// Load image
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

		// Load Media resources
		for (MediaResources res : MediaResources.values()) {
			try {
				Media media = new Media(AssetManager.class.getClassLoader().getResource(res.getURL()).toURI().toString());
				mediaMap.put(res, media);
				System.out.println("Loaded " + res + "!");
			} catch (NullPointerException | IllegalArgumentException | URISyntaxException ex) {
				System.err.println("Could not load resource " + res + ": ");
				ex.printStackTrace(System.err);
			}
		}
	}

	private AssetManager() {
	}

}
