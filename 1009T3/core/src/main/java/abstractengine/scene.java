package abstractengine;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class scene {
    protected String name;
    protected Color bgColor;
    protected Texture bgImg;
    protected boolean isActive;			// using the primitive class, don't see the need for 'yes,no,null' scenarios
    protected boolean isInitialized;
    protected boolean isPaused;
    protected Camera camera;
    protected Viewport viewport;

    // constructors...
    public scene() {
    	return;
    	// add some more init stuff here...
    }
    
    public scene(String name) {
        this.name = name;
    }
    
    public scene(String name, Color bgColor, Texture bgImg, Camera camera) {
    	return;
    }
    
    // getters and setters, i may add more later on
    // more added that's not in UML: isPaused, isActive, isInitialized
    // will add Camera, Viewport later...
    
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
    
    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void init();
    public abstract void dispose();
}
