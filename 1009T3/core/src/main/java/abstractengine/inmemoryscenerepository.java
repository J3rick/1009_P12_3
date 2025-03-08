package abstractengine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class inmemoryscenerepository implements scenerepository {
    private final Map<String, scene> scenes = new HashMap<>();

    @Override
    public void addScene(scene scene) {
        if (scene != null && scene.getName() != null && !scenes.containsKey(scene.getName())) {
            scenes.put(scene.getName(), scene);
        }
    }

    @Override
    public scene getScene(String name) {
        return scenes.get(name);
    }

    @Override
    public Map<String, scene> getAllScenes() {
        return Collections.unmodifiableMap(scenes);
    }
}
