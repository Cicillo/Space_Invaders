package space.invaders;

/**
 *
 * @author Frankie
 */
import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import static jdk.nashorn.internal.objects.NativeJava.type;

public class AssetManager {

	private static final Map<ImageResources, Image> imageMap = new EnumMap<>(ImageResources.class);

	private static final Map<AudioResources, AudioClip> audioMap = new EnumMap<>(AudioResources.class);

	public static Image getImage(ImageResources imR) {
		return imageMap.get(imR);
	}

	public static AudioClip getSound(AudioResources auR) {
		return audioMap.get(auR);
	}

	public void initialize() {
		
	}
}
