package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import abstractengine.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

import com.badlogic.gdx.Gdx;

public class gameoverscene extends scene{
    public gameoverscene(){
        super("Default Game Over Scene");
    }

    public gameoverscene(String name, Texture bgImg, Color bgColor, OrthographicCamera camera, List<entity> entityList_in){
		super(name,bgColor,bgImg,camera,entityList_in);
    }

    public gameoverscene(String name, Texture bgImg, Color bgColor, OrthographicCamera camera){
		super(name,bgColor,bgImg,camera);
    }

    @Override
    public void init() {
    	System.out.println("init");
    }

    @Override
    public void update() {
        // Handle scene updates
    }

    @Override
    public void render(SpriteBatch batch, float x, float y, float width, float height) {
        batch.draw(super.getBgImg(), x, y, width, height);
    }
    
    @Override
    public void render(SpriteBatch batch) {
    }
    

    @Override
    public void dispose() {
        super.getBgImg().dispose();
    }
}