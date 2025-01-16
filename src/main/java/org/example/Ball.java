package org.example;

import javafx.scene.paint.Color;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.*;

import java.util.Random;

public class Ball extends javafx.scene.shape.Circle implements PhysicsObjectProvider {

    private Body body;

    public Ball(final float xCoordinate, final float yCoordinate) {
        initializeJavaFXCircle(xCoordinate, yCoordinate);
        initializePhysicsObject(xCoordinate, yCoordinate);
    }

    private void initializePhysicsObject(final float xCoordinate, final float yCoordinate) {
        body = new Body();
        body.addFixture(Geometry.createCircle(App.Ball_RADIUS / App.SCALE), 1.0, 20.0, 0.1);
        body.setMass(MassType.NORMAL);
        body.translate(xCoordinate / App.SCALE, yCoordinate / App.SCALE);
    }

    private void initializeJavaFXCircle(final float xCoordinate, final float yCoordinate) {
        setTranslateX(xCoordinate);
        setTranslateY(yCoordinate);
        setFill(Color.BLUE);
        setStroke(Color.BLACK);
        setRadius(App.Ball_RADIUS);
    }

    @Override
    public Body getPhysObj() {
        return body;
    }

    @Override
    public void update() {
        var t = body.getTransform();
        setTranslateX(t.getTranslationX() * App.SCALE);
        setTranslateY(t.getTranslationY() * App.SCALE);
    }

    @Override
    public String toString() {
        var t = body.getTransform();
        return "Ball: \n" +
                "\tScreen: (" + getTranslateX() + ", " + getTranslateY() + "\n)" +
                "\tWorld: (" + t.getTranslationX() + ", " + t.getTranslationY() + ")";
    }
}
