package io.github.some_example_name.lwjgl3;

import abstractengine.abstractengine;
import abstractengine.entitymanager;
import abstractengine.scenemanager;
import abstractengine.iomanager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;

public class gamemaster extends abstractengine {
    private SpriteBatch batch;
    private entitymanager entityManager;
    private scenemanager sceneManager;
    private iomanager inputManager;
    private OrthographicCamera camera;

    private Texture playerTexture, enemyTexture, platformTexture, backgroundTexture, gameOverTexture;
    private Rectangle player;
    private enemy singleEnemy;
    private Array<Rectangle> platforms;

    private float velocityY = 0;
    private final float gravity = -700;
    private final float jumpPower = 400;
    private final float speed = 200;
    private boolean isJumping = false;
    private float lastPlatformX = 100;
    private boolean onPlatform;
    private final float startX = 100, startY = 150;
    private final float fallThreshold = -100;
    
    private enum GameState {PLAYING, GAME_OVER, RESPAWNING}
    private GameState gameState = GameState.PLAYING;
    private float gameOverTimer = 0;
    private final float gameOverDuration = 3; 

    @Override
    protected void init() {
        batch = new SpriteBatch();
        entityManager = new entitymanager();
        sceneManager = new scenemanager();
        inputManager = new iomanager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        playerTexture = new Texture("player.png");
        enemyTexture = new Texture("enemy.png");
        platformTexture = new Texture("platform.png");
        backgroundTexture = new Texture("background.png");
        gameOverTexture = new Texture("gameover.png");

        platforms = new Array<>();
        generatePlatforms();

        // Place player on the first platform
        Rectangle firstPlatform = platforms.first();
        player = new Rectangle(firstPlatform.x + firstPlatform.width / 2 - 25, firstPlatform.y + firstPlatform.height, 50, 50);
        
        singleEnemy = new enemy(0, "enemy.png", MathUtils.random(100, 700), Gdx.graphics.getHeight());
    }

    private void generatePlatforms() {
        for (int i = 0; i < 5; i++) {
            addPlatform();
        }
    }

    private void addPlatform() {
        Rectangle platform = new Rectangle();
        platform.x = lastPlatformX + MathUtils.random(200, 400); // Platforms appear ahead
        platform.y = MathUtils.random(100, 300);
        platform.width = 150;
        platform.height = 20;
        platforms.add(platform);
        lastPlatformX = platform.x;
    }

    @Override
    protected void update() {
        inputManager.updateInput();
        
        //GameState stuff
        if (gameState == GameState.GAME_OVER) {
        	gameOverTimer -= Gdx.graphics.getDeltaTime();
        	if (gameOverTimer <= 0) {
        		resetPlayer();
        		gameState = GameState.PLAYING;
        	}
        	return; //Stop updates during game over
        }
        
        singleEnemy.update();
        
        // Player movement using iomanager
        if (inputManager.isMovingLeft()) player.x -= speed * Gdx.graphics.getDeltaTime();
        if (inputManager.isMovingRight()) player.x += speed * Gdx.graphics.getDeltaTime();

        // Jump logic
        if (inputManager.isJumping() && !isJumping) {
            velocityY = jumpPower;
            isJumping = true;
        }

        velocityY += gravity * Gdx.graphics.getDeltaTime();
        player.y += velocityY * Gdx.graphics.getDeltaTime();

        // Check if player is on a platform
        onPlatform = false;
        for (Rectangle platform : platforms) {
            if (player.overlaps(platform) && velocityY < 0) {
                player.y = platform.y + platform.height;
                velocityY = 0;
                isJumping = false;
                onPlatform = true;
            }
        }

        // Respawn if player falls
        if (player.y < fallThreshold) {
            gameState = GameState.GAME_OVER;
            gameOverTimer = gameOverDuration;
        }

        // Camera follows player
        camera.position.x = player.x + player.width / 2;
        camera.update();

        // Continuously generate platforms ahead of player
        if (player.x > lastPlatformX - 400) {
            addPlatform();
        }
    }

    @Override
    protected void draw() {
        // Clear screen to remove image residue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Black background

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        //Drawing game elements
        batch.draw(backgroundTexture, camera.position.x - 400, 0);
        batch.draw(playerTexture, player.x, player.y, player.width, player.height);
        singleEnemy.draw(batch); //Enemy element
        for (Rectangle platform : platforms) {
            batch.draw(platformTexture, platform.x, platform.y, platform.width, platform.height);
        }
        
        //Displaying "Game Over"
        if (gameState == GameState.GAME_OVER) {
        	float gameOverWidth = gameOverTexture.getWidth();
        	float gameOverHeight = gameOverTexture.getHeight();
        	
        	float centerX = camera.position.x - gameOverWidth / 2;
        	float centerY = camera.position.y - gameOverHeight / 2;
        	
        	batch.draw(gameOverTexture, centerX,centerY);
        }
        batch.end();
    }

    private void resetPlayer() {
        Rectangle firstPlatform = platforms.first();
        player.x = firstPlatform.x + firstPlatform.width / 2 - 25;
        player.y = firstPlatform.y + firstPlatform.height;
        velocityY = 0;
        isJumping = false;
    }

    @Override
    protected void cleanup() {
        batch.dispose();
        enemyTexture.dispose();
        playerTexture.dispose();
        platformTexture.dispose();
        backgroundTexture.dispose();
        gameOverTexture.dispose();
    }
}
