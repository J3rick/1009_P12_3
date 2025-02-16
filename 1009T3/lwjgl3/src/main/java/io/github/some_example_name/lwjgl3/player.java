package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class player extends entity {
    private Texture texture;
    private float velocityY = 0;
    private final float gravity;
    private final float jumpPower;
    private final float speed;
    private boolean isJumping;

    public player(int id, String textureFile, float x, float y) {
        super(id, "Player", x, y, 50, 50);
        
        // Encapsulated physics constants
        this.gravity = -700;
        this.jumpPower = 400;
        this.speed = 200;
        this.isJumping = false;

        try {
            texture = new Texture(Gdx.files.internal(textureFile));
        } catch (Exception e) {
            System.err.println("Error loading player texture: " + textureFile);
            texture = new Texture(Gdx.files.internal("default.png")); // Fallback texture
        }
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setX(getX() - speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setX(getX() + speed * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            velocityY = jumpPower;
            isJumping = true;
        }

        velocityY += gravity * Gdx.graphics.getDeltaTime();
        setY(getY() + velocityY * Gdx.graphics.getDeltaTime());

        if (getY() < 0) {
            setY(0);
            velocityY = 0;
            isJumping = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    // Getter for jump state (Encapsulation)
    public boolean isJumping() {
        return isJumping;
    }

    // Getter for velocityY (Encapsulation)
    public float getVelocityY() {
        return velocityY;
    }
}
