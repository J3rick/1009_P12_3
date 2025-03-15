package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import abstractengine.scene;
import abstractengine.scenemanager;
import abstractengine.scenetransitionmanager;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class factsScene extends scene {
    private String fact;
    private BitmapFont font;
    private Texture bgTexture;
    private scenetransitionmanager sceneTransitionManager; // Reference to scene manager
    private ShapeRenderer shapeRenderer; // For drawing the box
    
    public factsScene(String name, Texture bgTexture, OrthographicCamera camera, String fact, scenetransitionmanager sceneTransitionManager) {
    	super(name, Color.WHITE, bgTexture, camera); // Must be first!
        this.fact = fact;
        this.sceneTransitionManager = sceneTransitionManager; // Assign scene manager
        this.bgTexture = bgTexture;
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void init() {

    }    
    
    @Override
    public void update() {
        if (Gdx.input.isTouched()) {  // Go back to the main game when tapped/clicked
        	System.out.println("Returning to main scene...");
        	sceneTransitionManager.loadScene("main");
        	((gamemaster) Gdx.app.getApplicationListener()).setGameState(gamemaster.gamestate.PLAYING);  // ✅ Resume game
            //sceneManager.loadScene("main");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(getCamera().combined);
        //batch.begin();

        // 🖼 Scale Background to Fit the Screen Properly
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        batch.draw(bgTexture, getCamera().position.x - (screenWidth / 2),
                   getCamera().position.y - (screenHeight / 2),
                   screenWidth, screenHeight);

        batch.end();

        // 💬 Draw Dialogue Box in the Center
        float boxWidth = 600, boxHeight = 200;
        float boxX = getCamera().position.x - (boxWidth / 2);
        float boxY = getCamera().position.y - (boxHeight / 2);

        shapeRenderer.setProjectionMatrix(getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(boxX, boxY, boxWidth, boxHeight);
        shapeRenderer.end();

        batch.begin();

        // 🔡 Center the Fact Text in the Box
        font.getData().setScale(2.0f);
        float textX = boxX + 20;
        float textY = boxY + (boxHeight / 2) + 10;
        font.draw(batch, fact, textX, textY);

        //batch.end();
    }



    @Override
    public void dispose() {
        font.dispose();
    }
}
