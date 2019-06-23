package catan.main;

import catan.data.GameMode;
import catan.data.terrain.Board;
import catan.io.ResourceLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GameScene extends Scene {

	private Stage stage;
	private ResourceLoader resources;

	private StackPane root;
	private Canvas canvas;

	private GameMode gameMode;
	private Board board;
	private Point2D scroll, startDrag, translate;
	private double zoom;


	public GameScene(GameMode gameMode, Stage stage, ResourceLoader resources) {
		super(new StackPane(), stage.getScene().getWidth(), stage.getScene().getHeight());
		this.init(gameMode, stage, resources);
		this.start();
	}

	private void init(GameMode gameMode, Stage stage, ResourceLoader resources) {
		this.stage = stage;
		this.resources = resources;
		root = (StackPane) this.getRoot();

		this.gameMode = gameMode;
		canvas = new Canvas();
		board = new Board(gameMode, resources);
		scroll = Point2D.ZERO;
		translate = Point2D.ZERO;
		zoom = 1.0;
	}

	private void start() {
		canvas.widthProperty().bind(root.widthProperty());
		canvas.heightProperty().bind(root.heightProperty());
		canvas.widthProperty().addListener((obs, oldVal, newVal) -> this.render());
		canvas.heightProperty().addListener((obs, oldVal, newVal) -> this.render());

		canvas.setOnMousePressed(me -> startDrag = new Point2D(me.getSceneX(), me.getSceneY()));
		canvas.setOnMouseDragged(me -> {
			final double dragDeltaX = startDrag.getX() - me.getSceneX();
			final double dragDeltaY = startDrag.getY() - me.getSceneY();

			scroll = scroll.add(dragDeltaX, dragDeltaY);
			startDrag = new Point2D(me.getSceneX(), me.getSceneY());

			this.render();
		});
		canvas.setOnScroll(se -> {
			zoom += se.getDeltaY() * 2 / 1000;
			zoom = Math.max(zoom, 0.5);
			zoom = Math.min(zoom, 4);

			this.render();
		});

		root.getChildren().add(canvas);
		canvas.requestFocus();

		this.render();
	}


	private void render() {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.translate(translate.getX() * -1, translate.getY() * -1);
		translate = new Point2D(canvas.getWidth() / 2, canvas.getHeight() / 2);
		gc.translate(translate.getX(), translate.getY());

		gc.clearRect(-canvas.getWidth() / 2, -canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight());

		board.render(canvas.getGraphicsContext2D(), scroll, zoom);
	}

}
