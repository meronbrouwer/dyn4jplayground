package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.util.Random;

public class Smiley extends ImageView implements PhysicsObjectProvider {

    private Body body;

    public Smiley(final Image image, final float xCoordinate, final float yCoordinate) {
        initializeImageView(image, xCoordinate, yCoordinate);
        initializePhysicsObject(xCoordinate, yCoordinate);
    }

    private void initializePhysicsObject(final float xCoordinate, final float yCoordinate) {
        var rectShape = new Rectangle(App.IMAGE_WIDTH_SCREEN / App.SCALE, App.IMAGE_WIDTH_SCREEN / App.SCALE);
        var f = new BodyFixture(rectShape);
        f.setDensity(1.2);
        f.setFriction(0.8);
        f.setRestitution(0.4);

        body = new Body();
        body.addFixture(f);
        body.setLinearVelocity(10, 10);
        body.setMass(new Mass(new Vector2(1, 1), 1, 1));
        body.translate(xCoordinate / App.SCALE + rnd(-1, 1), yCoordinate / App.SCALE + rnd(-1, 1));
        body.getTransform().setRotation(rnd(-3.141, 3.141));

        body.setTransform(body.getTransform());
    }

    private void initializeImageView(final Image image, final float xCoordinate, final float yCoordinate) {
        setImage(image);
        setTranslateX(xCoordinate);
        setTranslateY(yCoordinate);
    }

    private double rnd(double from, double to) {
        return to + (from - to) * new Random().nextDouble();
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
        return "Smiley: \n" +
                "\tScreen: (" + getTranslateX() + ", " + getTranslateY() + "\n)" +
                "\tWorld: (" + t.getTranslationX() + ", " + t.getTranslationY() + ")";
    }
}
