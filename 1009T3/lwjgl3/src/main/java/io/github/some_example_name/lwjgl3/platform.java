package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import abstractengine.entity;

public class platform extends entity {
	
	private Texture texture;
	
	public platform(int id, float x, float y, float width, float height) {
        super(id, "Platform", x, y, width, height);
    }
    
    @Override
    public void update() {
    	
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
