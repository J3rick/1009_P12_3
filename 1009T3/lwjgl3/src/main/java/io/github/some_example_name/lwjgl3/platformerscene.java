package io.github.some_example_name.lwjgl3;

import abstractengine.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class platformerscene extends scene {
	
	platformerscene(){
		return;
	}
	
	platformerscene(String sceneName, String bgImgPath, Color bgColor, OrthographicCamera camera){
		this.sceneName = sceneName;
		bgImg = new Texture(bgImgPath);
		this.bgColor = bgColor;
		// set Camera, Viewport here
		/**
		 viewport = ;
		**/
		
		this.camera = camera;
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
    }
}
