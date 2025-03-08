package io.github.some_example_name.lwjgl3;

import abstractengine.abstractengine;
import abstractengine.movementmanager;
import abstractengine.entitymanager;
import abstractengine.exceptionhandler;
import abstractengine.scenemanager;
import abstractengine.iomanager;
import abstractengine.collisionmanager;
import abstractengine.logging.gdxlogger;
import abstractengine.collision.boundingboxcollisionstrategy;
import abstractengine.movement.fallingmovementstrategy;
import abstractengine.inmemoryscenerepository;

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
import abstractengine.shutdown.simpleshutdownstrategy;
import abstractengine.interfaces.ishutdownstrategy;
import abstractengine.scenetransitionmanager;
import abstractengine.error.basicerrormessagestore;
import abstractengine.shutdown.simpleshutdownstrategy;
import abstractengine.scenelifecyclemanager;
import abstractengine.inmemoryscenerepository;
import abstractengine.exceptionlogger;


public class gamemaster extends abstractengine {
    private SpriteBatch batch;
    private movementmanager movementManager;
    private entitymanager entityManager;
    private scenemanager sceneManager;
    private iomanager inputManager;
    private OrthographicCamera camera;
    private collisionmanager collisionManager;
    private exceptionhandler exceptionHandler;
    private exceptionlogger exceptionLogger;

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
    private final float heightThreshold = 480;

    public enum gamestate { PLAYING, GAME_OVER, RESPAWNING }
    private gamestate gameState = gamestate.PLAYING;
    private float gameOverTimer = 0;
    private final float gameOverDuration = 3;

    @Override
    protected void init() {
        try {
            // Initialize exception handler with a gdxlogger
        	// In gamemaster, for example:
        	exceptionHandler = new exceptionhandler(new simpleshutdownstrategy());
            exceptionLogger = new exceptionlogger(new gdxlogger());
            batch = new SpriteBatch();
            movementManager = new movementmanager(new fallingmovementstrategy(150));
            entityManager = new entitymanager();
            // Create a scene repository and pass it to the scene manager
            scenemanager sceneManager = new scenemanager(new inmemoryscenerepository());
            sceneManager.addScene(platformerScene);
            sceneManager.addScene(gameOverScene);
            sceneManager.loadScene("main");

            // Then in your update and render methods, call:
            sceneManager.update();
            sceneManager.render(batch);          
            inputManager = new iomanager();
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            collisionManager = new collisionmanager(new boundingboxcollisionstrategy());

            playerTexture = new Texture("player.png");
            enemyTexture = new Texture("enemy.png");
            platformTexture = new Texture("platform.png");
            backgroundTexture = new Texture("background.png");
            gameOverTexture = new Texture("gameover.png");

            platforms = new Array<>();
            enemies = new Array<>();
            generatePlatforms();

            player = new player(1, "player.png", startX, startY);
            collisionManager.addCollidable(player);

            platform firstPlatform = platforms.first();
            player.setPosition(firstPlatform.getX() + firstPlatform.getWidth() / 2 - 25,
                                 firstPlatform.getY() + firstPlatform.getHeight());

            // Create scenes and add necessary entities
            platformerScene = new platformerscene("main", backgroundTexture, Color.BLUE, camera);
            platformerScene.addEntityToList(player);

            gameOverScene = new gameoverscene("game over", gameOverTexture, Color.BLACK, camera);

            // Add scenes to the repository via scene manager
            sceneManager.addScene(platformerScene);
            sceneManager.addScene(gameOverScene);
           
            
            scenetransitionmanager sceneTransitionManager = new scenetransitionmanager(new inmemoryscenerepository());
            sceneTransitionManager.addScene(platformerScene); // If needed, you can add a delegate method in repository.
            sceneTransitionManager.addScene(gameOverScene);
            sceneTransitionManager.loadScene("main");
         
         // In your init() method:
            scenelifecyclemanager lifecycleManager = new scenelifecyclemanager();
            scenetransitionmanager transitionManager = new scenetransitionmanager(new inmemoryscenerepository());
            transitionManager.loadScene("main");
            lifecycleManager.setCurrentScene(transitionManager.getCurrentScene());

            // In your update() method, call:
            lifecycleManager.update();

            // In your render() method, call:
            lifecycleManager.render(batch);


            spawnEnemy();

            for (int i = 0; i < enemies.size; i++) {
                collisionManager.addCollidable(enemies.get(i));
            }
        } catch (GdxRuntimeException ex) {
            exceptionHandler.exceptionOccurred(ex);
            cleanup();
        }
    }

    private void generatePlatforms() {
        for (int i = 0; i < 5; i++) {
            addPlatform();
        }
    }

    private void addPlatform() {
        float x = lastPlatformX + MathUtils.random(200, 400);
        float y = MathUtils.random(100, 300);
        platform newPlatform = new platform(platforms.size, x, y, 150, 20);
        platforms.add(newPlatform);
        collisionManager.addCollidable(newPlatform);
        lastPlatformX = x;
    }

    private void spawnEnemy() {
        if (enemies.size < 2) {
            float x = MathUtils.random(camera.position.x - 400, camera.position.x + 400);
            enemies.add(new enemy(enemies.size, "enemy.png", x, heightThreshold));
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
            gameState = gamestate.GAME_OVER;
            gameOverTimer = gameOverDuration;
        }
    }

    private void checkPlayerEnemyCollisions() {
        for (enemy e : enemies) {
            if (player.getBounds().overlaps(e.getBounds())) {
                gameState = gamestate.GAME_OVER;
                gameOverTimer = gameOverDuration;
                break;
            }
        }
    }

    @Override
    protected void update() {
        inputManager.updateInput();

        if (gameState == gamestate.GAME_OVER) {
            gameOverTimer -= Gdx.graphics.getDeltaTime();
            if (gameOverTimer <= 0) {
                resetPlayer();
                enemies.clear();
                gameState = gamestate.PLAYING;
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
            movementManager.updateEnemyMovement(e, Gdx.graphics.getDeltaTime());
            if (e.getY() < 0) {
                resetEnemyPosition(e);
            }
        }
    }

    private void updatePlayer() {
        if (inputManager.isMovingLeft()) {
            player.setX(player.getX() - speed * Gdx.graphics.getDeltaTime());
        }
        if (inputManager.isMovingRight()) {
            player.setX(player.getX() + speed * Gdx.graphics.getDeltaTime());
        }
        if (inputManager.isJumping() && !isJumping) {
            velocityY = jumpPower;
            isJumping = true;
        }
        velocityY += gravity * Gdx.graphics.getDeltaTime();
        player.setY(player.getY() + velocityY * Gdx.graphics.getDeltaTime());

        onPlatform = false;
        if (player.getY() < fallThreshold) {
            gameState = gamestate.GAME_OVER;
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
      
        batch.draw(backgroundTexture, camera.position.x - 400, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(playerTexture, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for (enemy e : enemies) {
            e.draw(batch);
        }
        for (platform platform : platforms) {
            batch.draw(platformTexture, platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }

        if (gameState == gamestate.GAME_OVER) {
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
}
