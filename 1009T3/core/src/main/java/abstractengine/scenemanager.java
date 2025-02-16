package abstractengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class scenemanager {
    private HashMap<String, scene> scenes = new HashMap<>();
    private scene currentScene;

    public scenemanager() {
    	return;
    }
    
    public scenemanager(HashMap<String, scene> scenes) {
    	this.scenes = scenes;
    }
    
    public void addScene(String name, scene newScene) {
        scenes.put(name, newScene);
    }
    
    // note for UML, switchScene can be removed.
    public void loadScene(String name) {
        if (scenes.containsKey(name)) {
        	// Clear screen to remove image residue
        	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        	Gdx.gl.glClearColor(0, 0, 0, 1); // Default black background
            currentScene = scenes.get(name);
            System.out.println("Switching to scene: " + name);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    // note for UML, pauseScene & resumeScene combined into one function below: (i could remove this in favor of just creating a dedicated pause scene and asking the program to load that...)
    public void togglePauseScene() {
    	if (currentScene != null && currentScene.getIsPaused() == false) {
    		currentScene.setIsPaused(true);
    	} else {
    		currentScene.setIsPaused(false);
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
