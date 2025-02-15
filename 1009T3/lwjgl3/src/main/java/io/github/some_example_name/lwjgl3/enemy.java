package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class enemy extends entity {
    private Texture texture;
    private final float fallSpeed = 150;

    public enemy(int id, String textureFile, float x, float y) {
        super(id, "enemy", x, y, 50, 50); // Set enemy size
        texture = new Texture(Gdx.files.internal(textureFile));
    }

    @Override
    public void update() {
        y -= fallSpeed * Gdx.graphics.getDeltaTime(); // Move the enemy downward
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    // Getter and setter methods for position
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
