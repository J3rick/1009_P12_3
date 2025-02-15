package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class enemy extends entity {
    private Texture texture;
    private final float fallSpeed = 150;
    private final float screenBottom = -50; // When it falls below this, respawn at the top
    private final float screenTop = 480; // Spawn enemies above this

    public enemy(int id, String textureFile, float x, float y) {
        super(id, "enemy", x, y, 50, 50); // Set enemy size
        texture = new Texture(Gdx.files.internal(textureFile));
    }

    @Override
    public void update() {
        // Move enemy downwards
        y -= fallSpeed * Gdx.graphics.getDeltaTime();

        // If enemy goes below the screen, respawn it at the top
        if (y < screenBottom) {
            respawn();
        }
    }

    public void respawn() {
        x = MathUtils.random(50, 750); // Random X position within screen bounds
        y = screenTop; // Place enemy at the top
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void setY(float y) { this.y = y; }
}
