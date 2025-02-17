package io.github.some_example_name.lwjgl3;

import abstractengine.scene;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class platformerscene extends scene {
    private Texture background;

    public platformerscene(String name) {
        super(name);
    }

    @Override
    public void init() {
        background = new Texture(Gdx.files.internal("background.png"));
    }

    @Override
    public void update() {
        // Handle scene updates
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0);
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
