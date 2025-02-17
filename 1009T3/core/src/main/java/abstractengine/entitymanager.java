package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class entitymanager {
    private final List<entity> entities; // Fully encapsulated list

    public entitymanager() {
        this.entities = Collections.synchronizedList(new ArrayList<>()); // Thread safety
    }

    /**
     * Adds an entity to the manager if it is not null.
     * @param e The entity to be added.
     */
    public void addEntity(entity e) {
        if (e != null) {
            synchronized (entities) {
                entities.add(e);
            }
        }
    }

    /**
     * Removes an entity by its ID.
     * @param id The ID of the entity to remove.
     */
    public void removeEntity(int id) {
        synchronized (entities) {
            Iterator<entity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                entity e = iterator.next();
                if (e.getId() == id) {
                    e.dispose(); // Properly dispose before removing
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Updates all managed entities.
     */
    public void update() {
        synchronized (entities) {
            for (entity e : entities) {
                e.update();
            }
        }
    }

    /**
     * Renders all entities using the provided SpriteBatch.
     * @param batch The batch used to render entities.
     */
    public void render(SpriteBatch batch) {
        synchronized (entities) {
            for (entity e : entities) {
                e.draw(batch);
            }
        }
    }

    /**
     * Disposes of all entities and clears the list.
     */
    public void dispose() {
        synchronized (entities) {
            for (entity e : entities) {
                e.dispose();
            }
            entities.clear();
        }
    }

    /**
     * Gets a **read-only copy** of all entities to ensure encapsulation.
     * @return A list of entities.
     */
    public List<entity> getEntities() {
        synchronized (entities) {
            return new ArrayList<>(entities); // Defensive copy
        }
    }
}
