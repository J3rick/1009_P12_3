package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class entitymanager {
    private List<entity> entities = new ArrayList<>();

    public void addEntity(entity e) {
        entities.add(e);
    }

    public void removeEntity(int id) {
        entities.removeIf(e -> e.getId() == id);
    }

    public void update() {
        for (entity e : entities) {
            e.update();
        }
    }

    public void render(SpriteBatch batch) {
        for (entity e : entities) {
            e.draw(batch);
        }
    }

    public void dispose() {
        for (entity e : entities) {
            e.dispose();
        }
        entities.clear();
    }
}
