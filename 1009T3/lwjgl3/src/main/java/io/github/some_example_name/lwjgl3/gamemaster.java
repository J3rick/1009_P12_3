package io.github.some_example_name.lwjgl3;

import abstractengine.abstractengine;
import abstractengine.entitymanager;
import abstractengine.exceptionhandler;
import abstractengine.scenemanager;
import io.github.some_example_name.lwjgl3.gamemaster.GameState;
import abstractengine.iomanager;
import abstractengine.collisionmanager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;

public class gamemaster extends abstractengine {
    private SpriteBatch batch;
    private entitymanager entityManager;
    private scenemanager sceneManager;
    private iomanager inputManager;
    private OrthographicCamera camera;
    private collisionmanager collisionManager;
    private exceptionhandler exceptionHandler;

    private Texture playerTexture, enemyTexture, platformTexture, backgroundTexture, gameOverTexture;
    private player player;
    private Array<enemy> enemies;
    private Array<platform> platforms;
    private platformerscene platformerScene;
    private gameoverscene gameOverScene;

    private float velocityY = 0;
    private final float gravity = -700;
    private final float jumpPower = 400;
    private final float speed = 200;
    private boolean isJumping = false;
    private float lastPlatformX = 100;
    private boolean onPlatform;
    private final float startX = 100, startY = 150;
    private final float fallThreshold = -100;

    public enum GameState {PLAYING, GAME_OVER, RESPAWNING}
    private GameState gameState = GameState.PLAYING;
    private float gameOverTimer = 0;
    private final float gameOverDuration = 3;

    @Override
    protected void init() {
        try{
            exceptionHandler = new exceptionhandler();
            batch = new SpriteBatch();
            entityManager = new entitymanager();
            sceneManager = new scenemanager();
            inputManager = new iomanager();
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            collisionManager = new collisionmanager();

            playerTexture = new Texture("player.png");
            enemyTexture = new Texture("enemy.png");
            platformTexture = new Texture("platform.png");
            backgroundTexture = new Texture("background.png");
            gameOverTexture = new Texture("gameover.png");

            platforms = new Array<>();
            enemies = new Array<>();
            generatePlatforms();
            
            player = new player(1, "player.png", startX, startY);
            collisionManager.addEntity(player);
            
            platform firstPlatform = platforms.first();
            player.setPosition(firstPlatform.getX() + firstPlatform.getWidth() / 2 - 25, firstPlatform.getY() + firstPlatform.getHeight());
            
            //Rectangle firstPlatform = platforms.first();
            //setPlayer(new Rectangle(firstPlatform.x + firstPlatform.width / 2 - 25, firstPlatform.y + firstPlatform.height, 50, 50));

            //Create scenes here, then add them to sceneManager
            platformerScene = new platformerscene("Main", backgroundTexture, Color.BLUE, camera);
            gameOverScene = new gameoverscene("Game Over", gameOverTexture, Color.BLACK, camera);

            sceneManager.addScene(platformerScene);
            sceneManager.addScene(gameOverScene);

            spawnEnemy();
            
            for (int i = 0; i < enemies.size; i++) {
                collisionManager.addEntity(enemies.get(i));
            }
        } catch (GdxRuntimeException ex){
            exceptionHandler.exceptionOccured(ex);
            cleanup();       
        }
    }
    private void generatePlatforms() {
        for (int i = 0; i < 5; i++) {
            addPlatform();
        }
    }

    private void addPlatform() {
    	/*
        Rectangle platform = new Rectangle();
        platform.x = lastPlatformX + MathUtils.random(200, 400);
        platform.y = MathUtils.random(100, 300);
        platform.width = 150;
        platform.height = 20;
        platforms.add(platform);
        lastPlatformX = platform.x;
        */
        
        float x = lastPlatformX + MathUtils.random(200, 400);
	    float y = MathUtils.random(100, 300);
	    platform newPlatform = new platform(platforms.size, x, y, 150, 20);
	    platforms.add(newPlatform);
	    collisionManager.addEntity(newPlatform);
	    lastPlatformX = x;
    }

    private void spawnEnemy() {
        if (enemies.size < 2) {
            float x = MathUtils.random(camera.position.x - 400, camera.position.x + 400);
            float y = MathUtils.random(100, 480);
            enemies.add(new enemy(enemies.size, "enemy.png", x, y));
        }
    }
    
    private void checkPlatformCollisions() {
        boolean onPlatform = false;
        for (platform platform : platforms) {
            if (player.getBounds().overlaps(platform.getBounds()) && velocityY < 0) {
                player.landOnPlatform(platform.getBounds());
                velocityY = 0;
                isJumping = false;
                onPlatform = true;
                break;
            }
        }
        if (!onPlatform && player.getY() < fallThreshold) {
            gameState = GameState.GAME_OVER;
            gameOverTimer = gameOverDuration;
        }
    }
    
    private void checkPlayerEnemyCollisions() {
        for (enemy e : enemies) {
            if (player.getBounds().overlaps(e.getBounds())) {
                gameState = GameState.GAME_OVER;
                gameOverTimer = gameOverDuration;
                break;
            }
        }
    }

    @Override
    protected void update() {
        inputManager.updateInput();

        if (gameState == GameState.GAME_OVER) {
            gameOverTimer -= Gdx.graphics.getDeltaTime();
            if (gameOverTimer <= 0) {
                resetPlayer();
                gameState = GameState.PLAYING;
            }
            return;
        }

        updateEnemies();
        updatePlayer();
        updateCamera();
        updatePlatforms();
        
        checkPlatformCollisions();
        checkPlayerEnemyCollisions();
        collisionManager.checkCollisions();

        if (MathUtils.randomBoolean(0.01f)) {
            spawnEnemy();
        }
    }

    
    private void updateEnemies() {
        for (int i = enemies.size - 1; i >= 0; i--) {
            enemy e = enemies.get(i);
            e.update();
            
            /*
            if (checkCollision(player, new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight()))) {
                resetEnemyPosition(e);
            }*/

            if (e.getY() < 0) {
                resetEnemyPosition(e);
            }
        }
    }

    private void updatePlayer() {
    	if (inputManager.isMovingLeft()) player.setX(player.getX() - speed * Gdx.graphics.getDeltaTime());
        if (inputManager.isMovingRight()) player.setX(player.getX() + speed * Gdx.graphics.getDeltaTime());
        
        //if (inputManager.isMovingLeft()) setPlayerX(getPlayerX() - speed * Gdx.graphics.getDeltaTime());
        //if (inputManager.isMovingRight()) setPlayerX(getPlayerX() + speed * Gdx.graphics.getDeltaTime());

        if (inputManager.isJumping() && !isJumping) {
            velocityY = jumpPower;
            isJumping = true;
        }

        velocityY += gravity * Gdx.graphics.getDeltaTime();
        player.setY(player.getY() + velocityY * Gdx.graphics.getDeltaTime());
        //setPlayerY(getPlayerY() + velocityY * Gdx.graphics.getDeltaTime());

        onPlatform = false;
        /*
        for (Rectangle platform : platforms) {
            if (checkCollision(player, platform) && velocityY < 0) {
                setPlayerY(platform.y + platform.height);
                velocityY = 0;
                isJumping = false;
                onPlatform = true;
            }
        }
        */

        if (player.getY() < fallThreshold) {
            gameState = GameState.GAME_OVER;
            gameOverTimer = gameOverDuration;
        }
    }

    private void updateCamera() {
        camera.position.x = player.getX() + player.getWidth() / 2;
        camera.update();
    }

    private void updatePlatforms() {
        if (player.getX() > lastPlatformX - 400) {
            addPlatform();
        }
    }

    private void resetEnemyPosition(enemy e) {
        float randomX = MathUtils.random(camera.position.x - 400, camera.position.x + 400);
        e.setX(randomX);
        e.setY(480);
    }

    @Override
    protected void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(backgroundTexture, camera.position.x - 400, 0);
        batch.draw(playerTexture, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for (enemy e : enemies) {
            e.draw(batch);
        }
        /*
        for (Rectangle platform : platforms) {
            batch.draw(platformTexture, platform.x, platform.y, platform.width, platform.height);
        }*/
        
        for (platform platform : platforms) {
            batch.draw(platformTexture, platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }

        if (gameState == GameState.GAME_OVER) {
            float gameOverWidth = gameOverTexture.getWidth();
            float gameOverHeight = gameOverTexture.getHeight();
            float centerX = camera.position.x - gameOverWidth / 2;
            float centerY = camera.position.y - gameOverHeight / 2;
            batch.draw(gameOverTexture, centerX, centerY);
        }

        batch.end();
    }

    private void resetPlayer() {
        platform firstPlatform = platforms.first();
        //setPlayerX(firstPlatform.x + firstPlatform.width / 2 - 25);
        //setPlayerY(firstPlatform.y + firstPlatform.height);
        player.setX(firstPlatform.getX() + firstPlatform.getWidth() / 2 - 25);
        player.setY(firstPlatform.getY() + firstPlatform.getHeight());
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

    // Encapsulation: Getters and Setters for player position
    //private float getPlayerX() { return player.x; }
    //private void setPlayerX(float x) { player.x = x; }
    //private float getPlayerY() { return player.y; }
    //private void setPlayerY(float y) { player.y = y; }
    //private void setPlayer(Rectangle rect) { player = rect; }

    private boolean checkCollision(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }
}
