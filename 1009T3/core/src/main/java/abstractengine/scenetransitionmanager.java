package abstractengine;

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

    public scene getCurrentScene() {
        return currentscene;
    }
	
}
