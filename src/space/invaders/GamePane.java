package space.invaders;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Totom3
 */
public class GamePane extends AnchorPane {

	private static final Font TEXT_FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("fonts/Portico Vintage.otf"), 30);

	private final Label scoreLabel;
	private final Label levelLabel;
	private final Label highscoreLabel;
	private final Canvas canvas;

	public GamePane() {
		this.scoreLabel = new Label("0");
		this.levelLabel = new Label("0");
		this.highscoreLabel = new Label("0");

		this.canvas = new Canvas();
	}

	public void initPane() {
		Scene scene = getScene();
		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		Label levelTextLabel = new Label("LEVEL");
		Label scoreTextLabel = new Label("SCORE");
		Label highscoreTextLabel = new Label("HIGH SCORE");

		formatLabels(scoreTextLabel, levelTextLabel, highscoreTextLabel, scoreLabel, levelLabel, highscoreLabel);

		GridPane grid = new GridPane();
		grid.getColumnConstraints().addAll(constr(HPos.LEFT), constr(HPos.CENTER), constr(HPos.RIGHT));
		grid.prefWidthProperty().bind(scene.widthProperty());

		grid.addColumn(0, scoreTextLabel, scoreLabel);
		grid.addColumn(1, levelTextLabel, levelLabel);
		grid.addColumn(2, highscoreTextLabel, highscoreLabel);
		getChildren().addAll(grid, canvas);

		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	private static void formatLabels(Label... labels) {
		for (Label label : labels) {
			label.setFont(TEXT_FONT);
			label.setTextFill(Color.WHITE);
		}
	}

	private static ColumnConstraints constr(HPos hpos) {
		ColumnConstraints constr = new ColumnConstraints();
		constr.setFillWidth(true);
		constr.setHgrow(Priority.ALWAYS);
		constr.setHalignment(hpos);
		return constr;
	}

}
