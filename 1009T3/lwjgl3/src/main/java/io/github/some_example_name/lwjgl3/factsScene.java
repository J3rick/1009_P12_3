package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import abstractengine.scene;
import abstractengine.scenetransitionmanager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Input;

public class factsScene extends scene {
    private String fact;
    private BitmapFont font;
    private Texture bgTexture;
    private scenetransitionmanager sceneTransitionManager;

    private static final String[] FACTS = {
        "Broccoli helps detox and may fight cancer!",
        "Carrots boost eye health with vitamin A!",
        "Avocados are rich in healthy fats and fiber!",
        "Apples support gut and heart health!",
        "Berries protect the brain and fight inflammation!",
        "Salmon is packed with omega-3 for brain health!",
        "Eggs provide protein and boost brain function!",
        "Almonds are loaded with vitamin E for skin!",
        "Whole grains aid digestion and balance blood sugar!",
        "Green tea boosts metabolism and heart health!"
    };

    public factsScene(String name, Texture bgTexture, OrthographicCamera camera, scenetransitionmanager sceneTransitionManager) {
        super(name, Color.WHITE, bgTexture, camera);
        this.sceneTransitionManager = sceneTransitionManager;
        this.bgTexture = bgTexture;
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        generateRandomFact();
    }

    private void generateRandomFact() {
        int index = MathUtils.random(FACTS.length - 1);
        fact = FACTS[index];
        System.out.println("New fact loaded: " + fact);
    }

    @Override
    public void init() {
        System.out.println("Fact Scene Initialized with Fact: " + fact);
    }

    @Override
    public void update() {
        // Detect touch or any key press
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            sceneTransitionManager.loadScene("main");
            ((gamemaster) Gdx.app.getApplicationListener()).setGameState(gamemaster.gamestate.PLAYING);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!batch.isDrawing()) {
            batch.begin();
        }

        OrthographicCamera cam = (OrthographicCamera) getCamera();
        float camWidth = cam.viewportWidth;
        float camHeight = cam.viewportHeight;
        float bgX = cam.position.x - camWidth / 2f;
        float bgY = cam.position.y - camHeight / 2f;
        batch.draw(bgTexture, bgX, bgY, camWidth, camHeight);

        // Render fact text centered
        GlyphLayout factLayout = new GlyphLayout(font, fact);
        float factX = cam.position.x - factLayout.width / 2f;
        float factY = cam.position.y + 50;
        font.draw(batch, fact, factX, factY);

        // Render instruction text below the fact
        String instruction = "Press any key to continue...";
        GlyphLayout instructionLayout = new GlyphLayout(font, instruction);
        float instructionX = cam.position.x - instructionLayout.width / 2f;
        float instructionY = cam.position.y - 20;
        font.draw(batch, instruction, instructionX, instructionY);

        batch.end();
    }

    @Override
    public void render(SpriteBatch batch, float x, float y, float width, float height) {
    }

    
    @Override
    public void dispose() {
        font.dispose(); // Dispose font to prevent memory leaks
    }
}
