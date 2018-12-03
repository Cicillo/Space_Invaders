package space.invaders;

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

	private int index;
	private boolean played;
	private ObservableList<Image> images;

	public ImageAnimation(ObservableList<Image> images) {
		this.images = images;
	}

	public boolean play() {
		if (played)
			return false;

		played = true;
		animations.add(this);
		return true;
	}

	public boolean stop() {
		if (!played)
			return false;

		played = false;
		animations.remove(this);
		return true;
	}

	public void setImages(ObservableList<Image> images) {
		if (index >= images.size())
			index = 0;
		this.images = images;
	}

}
