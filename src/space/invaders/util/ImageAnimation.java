package space.invaders.util;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Totom3
 */
public class ImageAnimation extends ImageView {

	private static final ConcurrentLinkedQueue<ImageAnimation> animations = new ConcurrentLinkedQueue<>();

	public static void initialize(ScheduledExecutorService exec) {
		long delay = 1000 / 24;
		exec.scheduleAtFixedRate(() -> {
			Platform.runLater(() -> {
				for (ImageAnimation animation : animations) {
					animation.index = (animation.index + 1) % animation.images.size();
					animation.setImage(animation.images.get(animation.index));
				}
			});
		}, delay, delay, TimeUnit.MILLISECONDS);
	}

	private boolean playing;
	private volatile int index;
	private ObservableList<Image> images;

	public ImageAnimation(ObservableList<Image> images) {
		this.images = images;
	}

	public boolean play() {
		if (playing)
			return false;

		playing = true;
		animations.add(this);
		return true;
	}

	public boolean stop() {
		if (!playing)
			return false;

		playing = false;
		animations.remove(this);
		return true;
	}

	public Image getCurrentImage() {
		return images.get(index);
	}

	public void setImages(ObservableList<Image> images) {
		if (index >= images.size())
			index = 0;
		this.images = images;
	}

}
