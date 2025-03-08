package abstractengine;

import abstractengine.collision.icollisionstrategy;
import abstractengine.interfaces.icollidable;
import java.util.ArrayList;
import java.util.List;

public class collisionmanager {
    private List<icollidable> collidables;
    private icollisionstrategy collisionStrategy;

    public collisionmanager(icollisionstrategy strategy) {
        collidables = new ArrayList<>();
        this.collisionStrategy = strategy;
    }

    public void addCollidable(icollidable obj) {
        collidables.add(obj);
    }

    public void removeCollidable(icollidable obj) {
        collidables.remove(obj);
    }

    public void checkCollisions() {
        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {
                icollidable obj1 = collidables.get(i);
                icollidable obj2 = collidables.get(j);
                if (collisionStrategy.detectCollision(obj1, obj2)) {
                    handleCollision(obj1, obj2);
                }
            }
        }
    }

    private void handleCollision(icollidable obj1, icollidable obj2) {
        System.out.println("Collision detected between: " + obj1 + " and " + obj2);
        // Additional collision response logic can be added here.
    }
}
