package abstractengine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

public final class collisionmanager {

    private List<entity> entities;

    public collisionmanager() {
        entities = new ArrayList<>();
    }

    public void addEntity(entity entity) {
        entities.add(entity);
    }
 

    public void removeEntity(entity entity) {
        entities.remove(entity);
    }

    public void checkCollisions() {
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                entity obj1 = entities.get(i);
                entity obj2 = entities.get(j);
                if (detectCollision(obj1, obj2)) {
                    handleCollision(obj1, obj2);
                }
            }
        }
    }

    private boolean detectCollision(entity obj1, entity obj2) {
        return obj1.getBounds().overlaps(obj2.getBounds());
    }

    private void handleCollision(entity obj1, entity obj2) {
        // Define collision handling logic here
        System.out.println("Collision detected between entities: " + obj1 + " and " + obj2);
    }
}
