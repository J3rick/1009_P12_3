package abstractengine;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class scene {
    private String name;
    private Color bgColor;
    private Texture bgImg;
    private boolean isActive;
    private boolean isInitialized;
    private boolean isPaused;
    private Camera camera;
    private Viewport viewport;

    // Default Constructor
    public scene() {
        this.name = "Unnamed Scene";
        this.bgColor = Color.BLACK;
        this.bgImg = null;
        this.isActive = false;
        this.isInitialized = false;
        this.isPaused = false;
    }

    // Constructor with name
    public scene(String name) {
        this();
        this.name = name;
        this.bgColor = Color.BLACK;
        this.bgImg = null;
        this.isActive = false;
        this.isInitialized = false;
        this.isPaused = false;
    }

    // Constructor with all fields
    public scene(String name, Color bgColor, Texture bgImg, Camera camera) {
        this.name = name;
        this.bgColor = bgColor;
        this.bgImg = bgImg;
        this.camera = camera;
        this.isActive = false;
        this.isInitialized = false;
        this.isPaused = false;
    }

    // Encapsulated Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Texture getBgImg() {
        return bgImg;
    }

    public void setBgImg(Texture bgImg) {
        this.bgImg = bgImg;
    }

    public boolean isPaused() { // Updated naming convention
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean isActive() { // Updated naming convention
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isInitialized() { // Updated naming convention
        return isInitialized;
    }

    public void setInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    // Abstract Methods
    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void init();
    public abstract void dispose();
}
