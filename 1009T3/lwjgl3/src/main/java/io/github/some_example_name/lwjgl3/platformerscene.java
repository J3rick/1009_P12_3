package io.github.some_example_name.lwjgl3;

import abstractengine.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class platformerscene extends scene {

    public platformerscene(){
        super("Default Scene");
    }

    public platformerscene(String name, Texture bgImg, Color bgColor, OrthographicCamera camera){
		super(name,bgColor,bgImg,camera);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        // Handle scene updates
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(super.getBgImg(), 0, 0);
    }

    @Override
    public void dispose() {
        super.getBgImg().dispose();
    }
}
