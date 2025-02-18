package abstractengine;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.List;

public abstract class scene {
    private String name;
    private Color bgColor;
    private Texture bgImg;
    private boolean isActive;
    private boolean isInitialized;
    private boolean isPaused;
    private Camera camera;
    private Viewport viewport;
    private List<entity> entities; // Store entities for the scene

    // Constructor with all fields (needed for platformerscene and gameoverscene)
    public scene(String name, Color bgColor, Texture bgImg, Camera camera) {
        this.name = name;
        this.bgColor = bgColor;
        this.bgImg = bgImg;
        this.camera = camera;
        this.isActive = false;
        this.isInitialized = false;
        this.isPaused = false;
        this.entities = new ArrayList<>();
    }

    // Getter and Setter methods
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Color getBgColor() { return bgColor; }
    public void setBgColor(Color bgColor) { this.bgColor = bgColor; }

    public Texture getBgImg() { return bgImg; }
    public void setBgImg(Texture bgImg) { this.bgImg = bgImg; }

    public boolean isPaused() { return isPaused; }
    public void setPaused(boolean isPaused) { this.isPaused = isPaused; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    public boolean isInitialized() { return isInitialized; }
    public void setInitialized(boolean isInitialized) { this.isInitialized = isInitialized; }

    public Camera getCamera() { return camera; }
    public void setCamera(Camera camera) { this.camera = camera; }

    public Viewport getViewport() { return viewport; }
    public void setViewport(Viewport viewport) { this.viewport = viewport; }

    public void addEntityToList(entity e) {
        if (e != null) {
            entities.add(e);
        }
    }

    public List<entity> getEntities() {
        return entities;
    }

    // Abstract Methods
    public abstract void init();
    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}
