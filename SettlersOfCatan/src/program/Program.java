package program;

import javafx.application.Application;

import javafx.beans.property.SimpleDoubleProperty;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.paint.Color;

import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import javafx.stage.Stage;

import terrain.Grid;

public class Program extends Application{
	
	private static Grid grid; // Create a hexagonal Grid
	
	private static Timer timer = new Timer(); // Create a new Timer to count track the frames

	public static void main(String[] args){
		launch(args);
	}
	
	/* Start the Program */
	public void start(Stage stage) throws Exception {
		
		grid = new Grid(3, 1, 1.1);
		
		SimpleDoubleProperty angleX = new SimpleDoubleProperty();
		SimpleDoubleProperty angleY = new SimpleDoubleProperty();
		
		PerspectiveCamera camera = new PerspectiveCamera(true);
		
		Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        yRotate.angleProperty().bind(angleX);
        xRotate.angleProperty().bind(angleY);
        
        Translate move = new Translate(0, 0, -50);
        
        camera.getTransforms().addAll(
        		pivot,
        		yRotate,
        		xRotate,
                move
        );

        PointLight bl = new PointLight();
        bl.setLightOn(true);
        bl.setTranslateY(-50);
        
        PointLight tl = new PointLight();
        tl.setLightOn(true);
        tl.setTranslateY(50);
        tl.setColor(Color.CYAN);
        
        pivot.setX(grid.getSize().getHeight() / 2);
        pivot.setY(0);
        pivot.setZ(grid.getSize().getWidth() / 2);
        
		Group root = new Group();
		
		grid.getTileMeshes().getChildren().stream().forEach(node -> node.setOnMouseClicked(event -> System.out.println(node.getTranslateX() + ", " + node.getTranslateZ())));

		root.getChildren().addAll(grid.getTileMeshes().getChildren());

		root.getChildren().add(bl);
		root.getChildren().add(tl);
		root.getChildren().add(camera);
		
		SubScene subScene = new SubScene(
				root,
				1024, 720,
				true,
				SceneAntialiasing.BALANCED
		);
		subScene.setCamera(camera);
		subScene.setFill(Color.BLACK);
		subScene.setOnScroll(event -> {
            angleX.set(angleX.doubleValue() + (event.getDeltaX() / 10));
            angleY.set(angleY.doubleValue() + (event.getDeltaY() / 10));
            if(angleY.doubleValue() < -55){
            	 angleY.set(-55);
            }
            if(angleY.doubleValue() > -15){
           	 angleY.set(-15);
           }
        });
		Group group = new Group();
        group.getChildren().add(subScene);
		
		Scene scene = new Scene(group, 1024, 720);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.DOWN) && move.getZ() > -80){
					move.setZ(move.getZ() - 0.5);
				}
				if(event.getCode().equals(KeyCode.UP) && move.getZ() < -10){
					move.setZ(move.getZ() + 0.5);
				}
			}
			
		});

		stage.setScene(scene);
		
		stage.show();
		
		timer.addKeyFrame(ae -> {tick();});
		timer.start();
		
	}
	
	/* The actions to do each frame, such as updating the screen */
	private static void tick(){ 
		
	}
	
	/* */

}
