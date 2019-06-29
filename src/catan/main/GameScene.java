package catan.main;

import catan.data.GameMode;
import catan.data.terrain.Board;
import catan.data.terrain.Tile;
import catan.io.ResourceLoader;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GameScene extends Scene {

	private Stage stage;
	private ResourceLoader resources;

	private StackPane root;
	private ImageView bgView;
	private Canvas canvas;

	private Image bgImage;
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
		bgView = new ImageView();
		canvas = new Canvas();

		bgImage = resources.getImage("img/tabletop.png");
		this.gameMode = gameMode;
		board = new Board(gameMode, resources);
		translate = Point2D.ZERO;
		zoom = 1.0;

		switch (gameMode) {
			case BASE_GAME:
				final Dimension2D tileDimensions = Tile.getDimensions(Tile.TILE_SIZE, zoom);
				scroll = new Point2D(tileDimensions.getWidth() * 4.5, tileDimensions.getHeight() * 2.25);
				break;
			case SEAFARERS:
				scroll = Point2D.ZERO;
				break;
			case CITIES_AND_KNIGHTS:
				scroll = Point2D.ZERO;
				break;
			case TRADERS_AND_BARBARIANS:
				scroll = Point2D.ZERO;
				break;
			case EXPLORERS_AND_PIRATES:
				scroll = Point2D.ZERO;
				break;
		}
	}

	private void start() {
		bgView.setImage(bgImage);
		bgView.fitWidthProperty().bind(root.widthProperty());
		bgView.fitHeightProperty().bind(root.heightProperty());

		canvas.widthProperty().bind(root.widthProperty());
		canvas.heightProperty().bind(root.heightProperty());
		canvas.widthProperty().addListener((obs, oldVal, newVal) -> this.render());
		canvas.heightProperty().addListener((obs, oldVal, newVal) -> this.render());

		canvas.setOnMousePressed(me -> {
			startDrag = new Point2D(me.getSceneX(), me.getSceneY());
		});
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
		canvas.setOnMouseMoved(me -> {
			final Point2D mouseCoords = Tile.pixelToAxial(new Point2D(me.getX(), me.getY()), zoom, scroll.multiply(-1).add(canvas.getWidth() / 2, canvas.getHeight() / 2));
			final Tile tile = board.getTile(mouseCoords);

			this.render();
			if (tile != null) {
				tile.highlight(canvas.getGraphicsContext2D(), Tile.axialToPixel(mouseCoords, zoom, scroll.multiply(-1)), zoom, new Color(1.0, 0.0, 1.0, 1.0));
			}
		});

		root.getChildren().addAll(bgView, canvas);
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
