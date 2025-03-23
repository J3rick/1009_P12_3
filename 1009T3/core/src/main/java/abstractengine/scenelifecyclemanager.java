package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class scenelifecyclemanager {
    private scene currentScene;

    public scenelifecyclemanager() {
        this.currentScene = null;
    }

    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public void render(SpriteBatch batch) {
        if (currentScene != null) {
            currentScene.render(batch);
        }
    }
    
    public void render(SpriteBatch batch, float x, float y, float width, float height) {
        if (currentScene != null) {
            currentScene.render(batch, x, y, width, height);
        }
    }

    public void dispose() {
        if (currentScene != null) {
            currentScene.dispose();
        }
    }

    public scene getCurrentScene() {
        return currentScene;
    }
    
    public void setCurrentScene(scene scene_in) {
    	currentScene = scene_in;
    }
}
