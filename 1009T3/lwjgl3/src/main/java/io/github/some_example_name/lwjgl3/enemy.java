package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import abstractengine.interfaces.imovable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class enemy extends entity implements imovable {
    private Texture texture;
    private static final float FALL_SPEED = 150; // Constant for controlled downward movement

    public enemy(int id, String textureFile, float x, float y) {
        super(id, "enemy", x, y, 50, 50); // Use parent constructor
        this.texture = new Texture(Gdx.files.internal(textureFile));
    }

    @Override
    public void update() {
        // You may choose to call updateMovement here or leave it empty if handled elsewhere.
    }

    @Override
    public void updateMovement(float deltaTime) {
        setY(getY() - FALL_SPEED * deltaTime);
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
