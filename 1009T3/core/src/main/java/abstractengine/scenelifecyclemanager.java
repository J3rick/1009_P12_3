package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class scenelifecyclemanager {
    private scene currentScene;
    private scenerepository sceneRepo;

    public scenelifecyclemanager(scenerepository repo) {
        this.sceneRepo = repo;
        this.currentScene = null;
    }

    public void loadScene(String name) {
        scene next = sceneRepo.getScene(name);
        if (next != null) {
            if (currentScene != null) {
                currentScene.dispose();
            }
            currentScene = next;
            currentScene.init();
            System.out.println("Switched to scene: " + name);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    public void update() {
        if (currentScene != null) {
            currentScene.update();
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
}
