package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class player extends entity {
    private Texture texture;
    private float velocityY = 0;
    private final float gravity = -700;
    private final float jumpPower = 400;
    private final float speed = 200;
    private boolean isJumping = false;

    public player(int id, String textureFile, float x, float y) {
        super(id, "Player", x, y, 50, 50); // Set player size
        texture = new Texture(Gdx.files.internal(textureFile));
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= speed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += speed * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            velocityY = jumpPower;
            isJumping = true;
        }

        velocityY += gravity * Gdx.graphics.getDeltaTime();
        y += velocityY * Gdx.graphics.getDeltaTime();

        if (y < 0) {
            y = 0;
            velocityY = 0;
            isJumping = false;
        }
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
