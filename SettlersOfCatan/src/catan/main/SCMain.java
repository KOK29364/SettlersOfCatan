package catan.main;

import catan.io.ResourceLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class SCMain extends Application {

	public static final String NAME = "Settlers of Catan";
	public static final String VERSION = "v0.0.1";
	public static final String AUTHOR = "Alpin & Yoel";

	private ResourceLoader resources;


	public static void main(String[] args) {
		System.out.format("%s %s by %s.\n", NAME, VERSION, AUTHOR);
		Application.launch(args);
	}


	@Override
	public void init() throws Exception {
		super.init();
		resources = new ResourceLoader();
	}

	@Override
	public void start(Stage stage) throws Exception {
		resources.load();
		stage.getIcons().add(resources.getImage("img/catan.png"));

		stage.setScene(new Scene(new Pane(), 1280, 720));
		stage.setScene(new TitleScene(stage, resources));
		stage.setResizable(true);
		stage.setTitle("Settlers of Catan");
		stage.setOnCloseRequest(e -> this.stop());
		stage.show();
	}

	@Override
	public void stop() {
		try {
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
