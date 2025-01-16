package org.example;

import javafx.scene.paint.Color;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;

import java.util.Random;

public class Ball extends javafx.scene.shape.Circle implements PhysicsObjectProvider {

    private Body body;

    public Ball(final float xCoordinate, final float yCoordinate) {
        initializeJavaFXCircle(xCoordinate, yCoordinate);
        initializePhysicsObject(xCoordinate, yCoordinate);
    }

    private void initializePhysicsObject(final float xCoordinate, final float yCoordinate) {
        var circleShape = new Circle(App.Ball_RADIUS / (App.SCALE));
        var fixture = new BodyFixture(circleShape);
        fixture.setDensity(10);
        fixture.setFriction(1);
        fixture.setRestitution(0.5);

        body = new Body();
        body.addFixture(fixture);
        body.setMass(new Mass(new Vector2(1, 1), 2, 1));
        body.translate(xCoordinate / App.SCALE, yCoordinate / App.SCALE);

        body.setTransform(body.getTransform());
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
        setRotate(t.getRotation().toDegrees());
    }

    @Override
    public String toString() {
        var t = body.getTransform();
        return "Ball: \n" +
                "\tScreen: (" + getTranslateX() + ", " + getTranslateY() + "\n)" +
                "\tWorld: (" + t.getTranslationX() + ", " + t.getTranslationY() + ")";
    }
}
