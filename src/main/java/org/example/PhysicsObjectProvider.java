package org.example;

import org.dyn4j.dynamics.Body;

public interface PhysicsObjectProvider {

    Body getPhysObj();

    void update();
}
