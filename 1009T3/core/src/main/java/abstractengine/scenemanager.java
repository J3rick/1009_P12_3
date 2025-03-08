<<<<<<< Updated upstream
package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;

public class scenemanager {
    private HashMap<String, scene> scenes = new HashMap<>();
    private scene currentScene;

    public void addScene(String name, scene newScene) {
        scenes.put(name, newScene);
    }

    public void loadScene(String name) {
        if (scenes.containsKey(name)) {
            currentScene = scenes.get(name);
            System.out.println("Switching to scene: " + name);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    public void update() {
        if (currentScene != null) currentScene.update();
    }

    public void render(SpriteBatch batch) {
        if (currentScene != null) currentScene.render(batch);
    }

    public void dispose() {
        if (currentScene != null) currentScene.dispose();
    }
}
=======
package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//@Deprecated
public class scenemanager {
    private scenerepository sceneRepo;
    private scenelifecyclemanager lifecycleManager;

    public scenemanager(scenerepository repo) {
        this.sceneRepo = repo;
        // Create a dedicated lifecycle manager using the same repository.
        this.lifecycleManager = new scenelifecyclemanager(repo);
    }

    // Delegate scene storage to the repository.
    public void addScene(scene newScene) {
        sceneRepo.addScene(newScene);
    }

    // Delegate lifecycle operations to the dedicated lifecycle manager.
    public void loadScene(String name) {
        lifecycleManager.loadScene(name);
    }

    public void update() {
        lifecycleManager.update();
    }

    public void render(SpriteBatch batch) {
        lifecycleManager.render(batch);
    }

    public void dispose() {
        lifecycleManager.dispose();
    }

    public scene getCurrentScene() {
        return lifecycleManager.getCurrentScene();
    }
}
>>>>>>> Stashed changes
