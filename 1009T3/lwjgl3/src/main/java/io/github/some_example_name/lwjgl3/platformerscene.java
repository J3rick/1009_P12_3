package io.github.some_example_name.lwjgl3;

import abstractengine.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class platformerscene extends scene {
	
	// can we rename this?
	
	// shouldn't these constructors be in the scene...
	// can check the lab to compare?
    public platformerscene(String sceneName, String bgImgPath) {
        super(sceneName);
        bgImg = new Texture(Gdx.files.internal(bgImgPath)); //background.png
    }

    public platformerscene(String sceneName, String bgImgPath, Color bgColor) {
    	super(sceneName);
        bgImg = new Texture(Gdx.files.internal(bgImgPath)); //background.png
        this.bgColor = bgColor;
    }
    
    @Override
    public void update() {
        // Handle scene updates
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(bgImg, 0, 0);
    }

    @Override
    public void dispose() {
        bgImg.dispose();
    }
    
    @Override
    public void init() {
    	return;
    	// Not sure how to handle this yet, or if necessary tbh
    }
}
