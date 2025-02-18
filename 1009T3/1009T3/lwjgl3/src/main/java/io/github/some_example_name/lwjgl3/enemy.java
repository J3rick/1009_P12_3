package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class enemy extends entity {
    private Texture texture;
    private static final float FALL_SPEED = 150; // Constant for controlled downward movement

    public enemy(int id, String textureFile, float x, float y) {
        super(id, "enemy", x, y, 50, 50); // Use parent constructor
        this.texture = new Texture(Gdx.files.internal(textureFile));
    }

    @Override
    public void update() {
        setY(getY() - FALL_SPEED * Gdx.graphics.getDeltaTime()); // Move enemy downward safely
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
