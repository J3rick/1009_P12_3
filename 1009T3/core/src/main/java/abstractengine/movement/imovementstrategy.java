package abstractengine.movement;

import abstractengine.interfaces.imovable;

public interface imovementstrategy {
    void updateMovement(imovable obj, float deltaTime);
}
