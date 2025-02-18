package io.github.some_example_name.lwjgl3;

import abstractengine.scene;
import abstractengine.entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class platformerscene extends scene {

    public platformerscene(String name, Texture bgImg, Color bgColor, OrthographicCamera camera) {
        super(name, bgColor, bgImg, camera);
    }

    @Override
    public void init() {
        System.out.println("Initializing Platformer Scene...");
    }

    @Override
    public void update() {
        System.out.println("Updating Platformer Scene...");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(super.getBgImg(), 0, 0);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing Platformer Scene...");
        super.getBgImg().dispose();
    }
}
