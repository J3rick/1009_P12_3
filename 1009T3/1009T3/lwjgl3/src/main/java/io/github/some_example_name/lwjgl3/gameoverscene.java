package io.github.some_example_name.lwjgl3;

import abstractengine.scene;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class gameoverscene extends scene {

    public gameoverscene(String name, Texture bgImg, Color bgColor, OrthographicCamera camera) {
        super(name, bgColor, bgImg, camera);
    }

    @Override
    public void init() {
        System.out.println("Initializing Game Over Scene...");
    }

    @Override
    public void update() {
        System.out.println("Updating Game Over Scene...");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(super.getBgImg(), 0, 0);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing Game Over Scene...");
        super.getBgImg().dispose();
    }
}
