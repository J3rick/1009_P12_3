package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class scenemanager {
    private final Map<String, scene> scenes; // Final map to store scenes (ensures immutability)
    private scene currentScene; // Stores the currently active scene

    /**
     * Constructor: Initializes the scene manager with an empty scene map.
     */
    public scenemanager() {
        this.scenes = new HashMap<>();
        this.currentScene = null;
    }

    /**
     * Adds a new scene to the manager if it's not null and doesn't already exist.
     *
     * @param name      The name identifier for the scene.
     * @param newScene  The scene object.
     */
    public void addScene(String name, scene newScene) {
        if (name != null && newScene != null && !scenes.containsKey(name)) {
            scenes.put(name, newScene);
        }
    }

    /**
     * Loads and switches to a scene if it exists.
     * Disposes of the previous scene if necessary.
     *
     * @param name The name of the scene to load.
     */
    public void loadScene(String name) {
        if (scenes.containsKey(name)) {
            if (currentScene != null) {
                currentScene.dispose(); // Ensure proper cleanup
            }
            currentScene = scenes.get(name);
            currentScene.init(); // Initialize the new scene
            System.out.println("Switched to scene: " + name);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    /**
     * Updates the currently active scene.
     */
    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    /**
     * Renders the currently active scene.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch batch) {
        if (currentScene != null) {
            currentScene.render(batch);
        }
    }

    /**
     * Disposes of all stored scenes and clears the manager.
     */
    public void dispose() {
        for (scene s : scenes.values()) {
            if (s != null) {
                s.dispose();
            }
        }
        scenes.clear();
        currentScene = null;
    }

    /**
     * Returns an **immutable copy** of the scene map to prevent external modifications.
     *
     * @return A read-only copy of all scenes.
     */
    public Map<String, scene> getScenes() {
        return Collections.unmodifiableMap(scenes);
    }

    /**
     * Gets the currently active scene.
     *
     * @return The current scene, or `null` if no scene is active.
     */
    public scene getCurrentScene() {
        return currentScene;
    }
}
