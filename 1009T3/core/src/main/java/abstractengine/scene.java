package abstractengine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class scene {
    protected String sceneName;
    protected Color bgColor;
    protected Texture bgImg;
    protected boolean isActive;
    protected boolean isInitialized;
    protected boolean isPaused;
    protected OrthographicCamera camera;
    protected Viewport viewport;

    // constructors
    public scene() {
    	bgColor = Color.BLACK;
    	// add some more init stuff here...
    }
    
    public scene(String sceneName) {
        this.sceneName = sceneName;
    }
    
    public scene(String sceneName, String bgImgPath) {
        this.sceneName = sceneName;
        bgImg = new Texture(Gdx.files.internal(bgImgPath)); 
    }

    public scene(String sceneName, String bgImgPath, Color bgColor) {
    	this.sceneName = sceneName;
        bgImg = new Texture(Gdx.files.internal(bgImgPath));
        this.bgColor = bgColor;
    }
    
    public scene(String sceneName, Color bgColor, Texture bgImg, OrthographicCamera camera, Viewport viewport) {
    	this.sceneName = sceneName;
    	this.bgColor = bgColor;
    	this.bgImg = bgImg;
    	this.camera = camera;
    	this.viewport = viewport;
    	
    	return;
    }
    
    // getters and setters, i may add more later on
    // more added that's not in UML: isPaused, isActive, isInitialized, camera, viewport
    
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
    
    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
    public boolean getIsPaused() {
        return isPaused;
    }    
    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public boolean getIsInitialized() {
        return isInitialized;
    }
    public void setIsInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }
    public OrthographicCamera getCamera() {
    	return camera;
    }
    public void setCamera(OrthographicCamera camera) {
    	this.camera = camera;
    	camera.update();
    }
    public Viewport getViewport() {
    	return viewport;
    }
    public void setViewport(Viewport viewport) {
    	this.viewport = viewport;
    }
    
    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void init();
    public abstract void dispose();
}
