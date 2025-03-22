package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class scenetransitionmanager {
    private scene currentscene;
    private scenerepository sceneRepo;

    public scenetransitionmanager(scenerepository repo) {
        this.sceneRepo = repo;
        this.currentscene = null;
    }

    // Delegate method to add scenes via the repository.
    public void addScene(scene newscene) {
        sceneRepo.addScene(newscene);
    }

    public void loadScene(String name) {
        scene next = sceneRepo.getScene(name);
        if (next != null) {
            if (currentscene != null) {
                currentscene.dispose();
            }
            currentscene = next;
            currentscene.init();
            System.out.println("Switched to scene: " + name);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    public void update() {
        if (currentscene != null) {
            currentscene.update();
        }
    }

    public void render(SpriteBatch batch) {
    	if (currentscene != null) {
            currentscene.render(batch); // by default, draws bgimg
        }
	}
    
    public void render(SpriteBatch batch, float x, float y, float width, float height) {
        if (currentscene != null) {
            currentscene.render(batch, x, y, width, height); // by default, draws bgimg
        }
    }

    public void dispose() {
        if (currentscene != null) {
            currentscene.dispose();
        }
    }

    public scene getCurrentScene() {
        return currentscene;
    }

	
}
