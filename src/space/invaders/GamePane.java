package space.invaders;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import space.invaders.projectiles.NormalProjectile;

/**
 *
 * @author Totom3
 */
public class GamePane extends AnchorPane {

	private static final Font TEXT_FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("fonts/Portico Vintage.otf"), 30);

	private final Canvas canvas;
	private final Label scoreLabel;
	private final Label levelLabel;
	private final Label highscoreLabel;
	private final Label gameStatusLabel;
	private final Rectangle spaceship;
	private final GameLogic gameLogic;

	private long lastShotTime;
	private volatile boolean mousePressed;
	private volatile DoubleProperty spaceshipPosition;
	private int currentHighscore;

	public GamePane(int highscore) {
		this.currentHighscore = highscore;
		this.spaceshipPosition = new SimpleDoubleProperty();

		this.canvas = new Canvas();
		this.gameLogic = new GameLogic(spaceshipPosition);

		this.scoreLabel = new Label("0");
		this.levelLabel = new Label("1");
		this.highscoreLabel = new Label(String.valueOf(highscore));
		this.gameStatusLabel = new Label("");
		this.spaceship = new Rectangle(GameConstants.LEFT_GAME_BOUND, GameConstants.SPACESHIP_Y, GameConstants.SPACESHIP_SIZE.getX(), GameConstants.SPACESHIP_SIZE.getX());
	}

	public GameLogic getLogic() {
		return gameLogic;
	}

	public void initialize() {
		// 1. Initialize game logic
		gameLogic.generateGame(getScene());

		// 2. Initialize GUI
		Scene scene = getScene();
		spaceship.setFill(Color.BLUE);
		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		Label levelTextLabel = new Label("LEVEL");
		Label scoreTextLabel = new Label("SCORE");
		Label highscoreTextLabel = new Label("HIGH SCORE");
		Label livesLabel = new Label("LIVES");

		formatLabels(scoreTextLabel, levelTextLabel, highscoreTextLabel, scoreLabel, levelLabel, highscoreLabel, livesLabel, gameStatusLabel);

		AnchorPane.setLeftAnchor(livesLabel, GameConstants.LEFT_GAME_BOUND);
		AnchorPane.setBottomAnchor(livesLabel, 3.0);

		gameStatusLabel.setVisible(false);
		gameStatusLabel.setAlignment(Pos.CENTER);
		gameStatusLabel.setTextAlignment(TextAlignment.CENTER);
		gameStatusLabel.prefWidthProperty().bind(scene.widthProperty());
		gameStatusLabel.prefHeightProperty().bind(scene.heightProperty());

		GridPane grid = new GridPane();
		grid.getColumnConstraints().addAll(constr(HPos.LEFT), constr(HPos.CENTER), constr(HPos.RIGHT));
		grid.prefWidthProperty().bind(scene.widthProperty());
		grid.setPadding(new Insets(20, 20, 0, 20));

		grid.addColumn(0, scoreTextLabel, scoreLabel);
		grid.addColumn(1, levelTextLabel, levelLabel);
		grid.addColumn(2, highscoreTextLabel, highscoreLabel);
		getChildren().addAll(grid, canvas, spaceship, livesLabel, gameStatusLabel);

		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		scene.setOnMouseMoved(this::handleMouseMove);
		scene.setOnMouseDragged(this::handleMouseMove);

		scene.setOnMousePressed(e -> {
			mousePressed = true;
		});
		scene.setOnMouseReleased(e -> {
			mousePressed = false;
		});

		gameLogic.getScore().addListener((obs, oldValue, newValue) -> {
			Platform.runLater(() -> {
				scoreLabel.setText(String.valueOf(newValue));
			});
		});
	}

	private void handleMouseMove(MouseEvent e) {
		if (gameLogic.hasWon() || gameLogic.hasLost())
			return;

		double pos = e.getSceneX() - GameConstants.SPACESHIP_SIZE.getX() / 2;
		if (gameLogic.isFrozen()) {
			pos = GameConstants.LEFT_GAME_BOUND;
		}

		pos = Math.max(10, Math.min(GameConstants.SCREEN_SIZE.getX() - GameConstants.SPACESHIP_SIZE.getX(), pos));
		spaceship.setX(pos);
		spaceshipPosition.set(pos);
	}

	private void handleShoot(MouseEvent e) {
		if (gameLogic.isFrozen())
			return;

		// To prevent spam, there must be a certain delay between shots
		long deltaT = System.currentTimeMillis() - lastShotTime;
		if (deltaT < GameConstants.SHOOT_DELAY) {
			return;
		}

		lastShotTime = System.currentTimeMillis();
		Vec2D position = new Vec2D(spaceship.getX() + spaceship.getWidth() / 2, spaceship.getY() - GameConstants.PROJECTILE_SIZE.getY() / 2);
		RectBounds bounds = new RectBounds(position, GameConstants.PROJECTILE_SIZE);
		Vec2D velocity = new Vec2D(0, GameConstants.PROJECTILE_SPEED_FRIENDLY);
		NormalProjectile proj = new NormalProjectile(true, bounds, velocity, NormalProjectile.DEFAULT_IMAGE);
		gameLogic.addProjectile(proj);
	}

	public void drawCanvas() {
		// Stop rendering if the player won or lost
		if (gameLogic.hasWon() || gameLogic.hasLost()) {
			throw new EndGameException();
		}

		// Display death message
		if (gameLogic.isFrozen()) {
			gameStatusLabel.setVisible(true);
			gameStatusLabel.setText("You died! " + gameLogic.getRemainingLives() + " lives left.");
		} else {
			gameStatusLabel.setVisible(false);
		}

		GraphicsContext graphics = canvas.getGraphicsContext2D();

		// 1. Clear Canvas
		clearCanvas(graphics);

		// 2. Draw Enemies
		gameLogic.forEachEnemy(e -> {
			Vec2D pos = e.getPosition(gameLogic.getEnemyPosition());
			graphics.drawImage(e.getImage(), pos.getX(), pos.getY());
		});

		// 3. Draw projectiles
		gameLogic.forEachProjectile(proj -> proj.draw(graphics));

		// 4. Draw life icons
		Image image = ImageResources.SPACESHIP_ICON.getImage();
		double x = 150;
		double y = GameConstants.BOTTOM_GAME_BOUND + 95;
		for (int i = 0; i < gameLogic.getRemainingLives(); ++i) {
			graphics.drawImage(image, x, y);
			x += 50;
		}
	}

	private void clearCanvas(GraphicsContext graphics) {
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public void tick() {
		// Tick spaceship
		if (mousePressed) {
			handleShoot(null);
		}

		// Tick game
		gameLogic.tickGame();
		if (gameLogic.hasWon()) {
			Platform.runLater(() -> {
				gameStatusLabel.setVisible(true);
				gameStatusLabel.setText("You have won!");
			});

			throw new EndGameException();
		} else if (gameLogic.hasLost()) {
			Platform.runLater(() -> {
				gameStatusLabel.setVisible(true);
				gameStatusLabel.setText("You have lost!");
			});

			throw new EndGameException();
		}
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
