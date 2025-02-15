package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class enemy extends entity{
	private Texture texture;
	private final float fallSpeed = 150;
    
    public enemy(int id, String textureFile, float x, float y) {
        super(id, "enemy", x, y, 50, 50); // Set player size
        texture = new Texture(Gdx.files.internal(textureFile));
    }
    
    @Override
    public void update() {
    	y -= fallSpeed * Gdx.graphics.getDeltaTime();

    }
    
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
    
    @Override
    public void dispose() {
        texture.dispose();
    }
	
}
