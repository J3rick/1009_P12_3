package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//@Deprecated
public class scenemanager {
    private scenerepository sceneRepo;
    private scenelifecyclemanager lifecycleManager;
    private scenetransitionmanager transitionManager;

    public scenemanager(scenerepository repo) {
        this.sceneRepo = repo;
        // Create a dedicated lifecycle manager using the same repository.
        this.transitionManager = new scenetransitionmanager(repo);
        this.lifecycleManager = new scenelifecyclemanager();
    }

    // Delegate scene storage to the repository.
    public void addScene(scene newScene) {
        sceneRepo.addScene(newScene);
    }

    // Delegate lifecycle operations to the dedicated lifecycle manager.
    public void loadScene(String name) {
        transitionManager.loadScene(name);
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
