package abstractengine;

import java.util.Map;

public interface scenerepository {
    void addScene(scene scene);
    scene getScene(String name);
    Map<String, scene> getAllScenes();
}
