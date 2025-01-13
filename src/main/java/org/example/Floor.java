package org.example;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;

public class Floor extends Rectangle implements PhysicsObjectProvider {

    private Body body;

    public Floor(final Point2D location, final double width, final double height) {
        initializePhysicsObject(location, width, height);
        initializeRectangle(width, height);
    }


    private void initializePhysicsObject(Point2D location, final double width, final double height) {

        org.dyn4j.geometry.Rectangle rectShape = new org.dyn4j.geometry.Rectangle(width/ App.SCALE, height / App.SCALE);
        var f = new BodyFixture(rectShape);

        body = new Body();
        body.addFixture(f);
        body.setMass(MassType.INFINITE);
        body.translate(location.getX(), location.getY());

        body.setTransform(body.getTransform());
    }

    private void initializeRectangle(final double width, final double height) {
        setWidth(width);
        setHeight(height);
        setFill(Color.YELLOW);
        setStroke(Color.BLACK);
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
        setRotate(t.getRotation().toRadians() / 0.017453292519943295);

        System.out.println(this);
    }


    @Override
    public String toString() {
        var t = body.getTransform();
        return "Floor: \n" +
                "\tScreen: (" + getTranslateX() + ", " + getTranslateY() + ")" + "(" + getWidth() + ", " + getHeight() + ")\n" +
                "\tWorld: (" + t.getTranslationX() + ", " + t.getTranslationY() + ")";
    }
}
