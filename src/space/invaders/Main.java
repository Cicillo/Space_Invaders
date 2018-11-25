package space.invaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author cstuser
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		GamePane pane = new GamePane();
		Scene scene = new Scene(pane, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
		pane.initPane();
		
		primaryStage.setTitle("Space Invaders");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
