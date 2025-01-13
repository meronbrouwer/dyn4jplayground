package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.Body;
import org.dyn4j.world.World;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    public static double SCENE_WIDTH = 800;
    public static double SCENE_HEIGHT = 600;
    public static double IMAGE_WIDTH_SCREEN = 20;
    /**
     * The scale 20 pixels per meter.
     */
    public static final double SCALE = 20;

    private List<PhysicsObjectProvider> objects = new ArrayList<>();

    Pane mainPane;
    World<Body> world;
    Scene scene;

    @Override
    public void start(Stage primaryStage) {

        var root = new Group();

        // the scale and translate mean 0,0 is at the bottom-left with height increasing up the screen
        mainPane = new Pane();
        var s = new Scale(1, -1);
        var t = new Translate(0, -SCENE_HEIGHT);
        mainPane.getTransforms().addAll(s, t);
        root.getChildren().add(mainPane);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Create the dyn4J World
        world = new World<>();
        var bounds = new AxisAlignedBounds(SCENE_WIDTH / SCALE, SCENE_HEIGHT / SCALE);
        world.setBounds(bounds);

        // create the floor
        var floor = new Floor(new Point2D(0, 0), SCENE_WIDTH, 10);
        // Add the Floor tp the dyn4J World
        world.addBody(floor.getPhysObj());
        // Add the Floor to the list of Objects that require the update() method to be called
        objects.add(floor);
        // Add the Floor to the JavaFX Pane
        mainPane.getChildren().add(floor);

        // Create the Smileys
        var img = new Image("smile.png", IMAGE_WIDTH_SCREEN, IMAGE_WIDTH_SCREEN, true, true);
        for (int i = 0; i < 1; i++) {
            var smiley = new Smiley(img, 400, 300);
            // Add the Smiley tp the dyn4J World
            world.addBody(smiley.getPhysObj());
            // Add the Smiley to the list of Objects that require the update() method to be called
            objects.add(smiley);
            // Add the Floor to the JavaFX Pane
            mainPane.getChildren().add(smiley);
        }

        // Call initial update to ensure all JavaFX Nodes are aligned with their dyn4J counterparts
        update();

        var gameLoop = new AnimationTimer() {

            long last;

            @Override
            public void handle(long now) { // now is in nanoseconds
                var delta = 1f / (1000.0f / ((now - last) / 1000000));  // seems long winded but avoids precision issues
                world.updatev(delta);
                update();

                last = now;
            }

        };
        gameLoop.start();
    }

    public void update() {
        for (var objectToUpdate : objects) {
            objectToUpdate.update();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
