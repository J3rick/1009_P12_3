package abstractengine.movement;

import abstractengine.interfaces.imovable;

public class horizontalmovementstrategy implements imovementstrategy {
    private final float horizontalSpeed;
    private final boolean movingRight; // Direction flag
    
    public horizontalmovementstrategy(float horizontalSpeed, boolean movingRight) {
        this.horizontalSpeed = horizontalSpeed;
        this.movingRight = movingRight;
    }
    
    @Override
    public void updateMovement(imovable obj, float deltaTime) {
        // Let the object handle horizontal movement if it has its own logic
        obj.updateMovement(deltaTime);
       
    }
}
