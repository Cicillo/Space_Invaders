package space.invaders;

/**
 *
 * @author Frankie
 */
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.EnumMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class AssetManager {

	static {
		System.out.println("Loading resources...");
		imageMap = new EnumMap<>(ImageResources.class);
		mediaMap = new EnumMap<>(MediaResources.class);
		imageAnimationMap = new EnumMap<>(AnimationResources.class);
		loadResources();
		System.out.println("Finished loading resources!");
	}

	private static boolean loadedResources;

	private static final Map<ImageResources, Image> imageMap;
	private static final Map<MediaResources, Media> mediaMap;
	private static final Map<AnimationResources, ObservableList<Image>> imageAnimationMap;

	public static Image getImage(ImageResources image) {
		return imageMap.get(image);
	}

	public static Media getSound(MediaResources media) {
		return mediaMap.get(media);
	}

	public static ObservableList<Image> getAnimation(AnimationResources media) {
		return imageAnimationMap.get(media);
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

		// Load animations
		outer:
		for (AnimationResources res : AnimationResources.values()) {
			ObservableList<Image> images = FXCollections.observableArrayList();

			for (int i = 1; i <= res.getFrames(); ++i) {
				String url = String.format("%s%04d.png", res.getURL(), i);
				InputStream in = AssetManager.class.getClassLoader().getResourceAsStream(url);
				if (in == null) {
					System.err.println("Could not load resource " + res + ": no such asset '" + url + "'");
					continue outer;
				}

				Image image = new Image(in);
				Exception ex = image.getException();
				if (ex != null) {
					System.err.println("Could not load frame " + i + " of " + res + ": ");
					ex.printStackTrace(System.err);
					continue outer;
				} else {
					images.add(image);
				}
			}

			imageAnimationMap.put(res, images);

		}
	}

	private AssetManager() {
	}

}
