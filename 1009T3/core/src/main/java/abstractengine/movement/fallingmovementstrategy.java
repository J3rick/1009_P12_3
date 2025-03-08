package abstractengine.movement;

import abstractengine.interfaces.imovable;

public class fallingmovementstrategy implements imovementstrategy {
    private final float fallSpeed;

    public fallingmovementstrategy(float fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    @Override
    public void updateMovement(imovable obj, float deltaTime) {
        // If the imovable doesn't handle falling itself, do it here:
        // obj.setY(obj.getY() - fallSpeed * deltaTime);
        // Or if imovable has its own logic, just call obj.updateMovement(deltaTime).
        obj.updateMovement(deltaTime);
    }
}
