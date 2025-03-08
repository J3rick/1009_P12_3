package abstractengine;

import abstractengine.interfaces.imovable;
import abstractengine.movement.imovementstrategy;

public class movementmanager {
    private imovementstrategy movementStrategy;

    public movementmanager(imovementstrategy strategy) {
        this.movementStrategy = strategy;
    }

    public void updateEnemyMovement(imovable movable, float deltaTime) {
        movementStrategy.updateMovement(movable, deltaTime);
    }
}
