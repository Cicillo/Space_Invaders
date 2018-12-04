package space.invaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import space.invaders.util.AssetManager;
import space.invaders.util.EndGameException;
import space.invaders.util.ImageAnimation;
import space.invaders.util.ImageResources;
import space.invaders.util.MediaResources;

/**
 *
 * @author cstuser
 */
public class Main extends Application {

	private static final File HIGHSCORE_FILE = new File("space_invaders_highscore.txt");

	private GamePane pane;
	private ScheduledFuture<?> gameTickTask;
	private ScheduledFuture<?> renderTask;
	private MediaPlayer musicPlayer;

	private int currentHighscore;

	@Override
	public void start(Stage stage) {
		try {
			Scanner sc = new Scanner(HIGHSCORE_FILE);
			if (sc.hasNextInt())
				currentHighscore = sc.nextInt();
		} catch (FileNotFoundException ex) {
			currentHighscore = 0;
		}

		pane = new GamePane(currentHighscore);
		Scene scene = new Scene(pane, GameConstants.SCREEN_SIZE.getX(), GameConstants.SCREEN_SIZE.getY());
		pane.initialize();

		stage.getIcons().add(ImageResources.ICON.getImage());
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Space Invaders");
		stage.show();

		int renderTick = (int) (1_000 / GameConstants.RENDER_FPS);
		int gameTick = (int) (1_000_000 / GameConstants.GAME_TPS);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
		gameTickTask = executor.scheduleAtFixedRate(this::tickGame, gameTick, gameTick, TimeUnit.MICROSECONDS);
		renderTask = executor.scheduleAtFixedRate(this::tickRender, renderTick, renderTick, TimeUnit.MILLISECONDS);

		musicPlayer = new MediaPlayer(AssetManager.getSound(MediaResources.BACKGROUND_MUSIC));
		musicPlayer.setOnReady(() -> {
			musicPlayer.setStopTime(musicPlayer.getTotalDuration());
			musicPlayer.setCycleCount(1000);
			musicPlayer.setVolume(0.5);
			musicPlayer.play();
		});

		ImageAnimation.initialize(executor);
	}

	private void tickGame() {
		try {
			pane.tick();
		} catch (Exception ex) {
			if (ex instanceof EndGameException) {
				gameTickTask.cancel(true);
				renderTask.cancel(true);
				System.out.println("end game!");
			} else {
				ex.printStackTrace(System.err);
				throw new RuntimeException(ex);
			}
		}
	}

	private void tickRender() {
		Platform.runLater(() -> {
			try {
				pane.drawCanvas();
			} catch (Exception ex) {
				if (ex instanceof EndGameException) {
					System.out.println("End of game!");
					gameTickTask.cancel(true);
					renderTask.cancel(true);
				} else {
					ex.printStackTrace(System.err);
					throw new RuntimeException(ex);
				}
			}
		});
	}

	private void endGame() {
		gameTickTask.cancel(true);
		renderTask.cancel(true);

		int score = pane.getLogic().getScore().get();
		if (score > currentHighscore) {
			try (FileWriter writer = new FileWriter(HIGHSCORE_FILE)) {
				writer.write(String.valueOf(score));
			} catch (IOException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void stop() {
		endGame();
		musicPlayer.stop();
		System.exit(0);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
