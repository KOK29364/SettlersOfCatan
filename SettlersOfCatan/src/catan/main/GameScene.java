package catan.main;

import catan.io.ResourceLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GameScene extends Scene {

	private Stage stage;
	private ResourceLoader resources;

	private StackPane root;


	public GameScene(Stage stage, ResourceLoader resources) {
		super(new StackPane(), stage.getScene().getWidth(), stage.getScene().getHeight());
		this.init(stage, resources);
		this.start();
	}

	private void init(Stage stage, ResourceLoader resources) {
		this.stage = stage;
		this.resources = resources;
		root = (StackPane) this.getRoot();
	}

	private void start() {

	}

}
