package catan.main;


import catan.data.GameMode;
import catan.io.ResourceLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class TitleScene extends Scene {

	private Stage stage;
	private ResourceLoader resources;

	private StackPane root;
	private ImageView bgView;
	private Image bgImage;
	private BorderPane borderPane;
	private VBox titleVBox;
	private HBox gameModesHBox;

	private Label titleLabel;
	private Button normalModeButton;


	public TitleScene(Stage stage, ResourceLoader resources) {
		super(new StackPane(), stage.getScene().getWidth(), stage.getScene().getHeight());
		this.init(stage, resources);
		this.start();
	}

	private void init(Stage stage, ResourceLoader resources) {
		this.stage = stage;
		this.resources = resources;
		root = (StackPane) this.getRoot();

		bgImage = resources.getImage("img/tabletop.png");
		bgView = new ImageView();
		borderPane = new BorderPane();
		titleVBox = new VBox();
		gameModesHBox = new HBox();

		titleLabel = new Label("Settlers of Catan");
		normalModeButton = new Button("Normal Mode");
	}

	private void start() {
		bgView.fitWidthProperty().bind(root.widthProperty());
		bgView.fitHeightProperty().bind(root.heightProperty());

		titleLabel.setFont(new Font(48));
		titleVBox.setAlignment(Pos.CENTER);
		titleVBox.getChildren().addAll(titleLabel);

		normalModeButton.setTooltip(new Tooltip("Base Game with no additional Expansions"));
		normalModeButton.setOnMouseClicked(me -> stage.setScene(new GameScene(GameMode.BASE_GAME, stage, resources)));

		gameModesHBox.setAlignment(Pos.CENTER);
		gameModesHBox.setPadding(new Insets(16, 0, 32, 0));
		gameModesHBox.getChildren().addAll(normalModeButton);

		borderPane.setCenter(titleVBox);
		borderPane.setBottom(gameModesHBox);

		root.getChildren().addAll(bgView, borderPane);
	}

}
