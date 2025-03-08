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
<<<<<<< Updated upstream
=======
import abstractengine.shutdown.simpleshutdownstrategy;
import abstractengine.interfaces.ishutdownstrategy;
import abstractengine.scenetransitionmanager;
import abstractengine.error.basicerrormessagestore;
import abstractengine.shutdown.simpleshutdownstrategy;
import abstractengine.scenelifecyclemanager;
import abstractengine.inmemoryscenerepository;

>>>>>>> Stashed changes

public class gamemaster extends abstractengine {
    private SpriteBatch batch;
    private entitymanager entityManager;
    private scenemanager sceneManager;
    private iomanager inputManager;
    private OrthographicCamera camera;
<<<<<<< Updated upstream
=======
    private collisionmanager collisionManager;
    private exceptionhandler exceptionHandler;
>>>>>>> Stashed changes

    private Texture playerTexture, enemyTexture, platformTexture, backgroundTexture, gameOverTexture;
    private Rectangle player;
    private Array<enemy> enemies;
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
<<<<<<< Updated upstream
        batch = new SpriteBatch();
        entityManager = new entitymanager();
        sceneManager = new scenemanager();
        inputManager = new iomanager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
=======
        try {
            // Initialize exception handler with a gdxlogger
        	// In gamemaster, for example:
        	exceptionHandler = new exceptionhandler(new gdxlogger(), new simpleshutdownstrategy());
            batch = new SpriteBatch();
            movementManager = new movementmanager(new fallingmovementstrategy(150));
            entityManager = new entitymanager();
            // Create a scene repository and pass it to the scene manager
            scenemanager sceneManager = new scenemanager(new inmemoryscenerepository());
            sceneManager.addScene(platformerScene);
            sceneManager.addScene(gameOverScene);
            sceneManager.loadScene("main");
>>>>>>> Stashed changes

        playerTexture = new Texture("player.png");
        enemyTexture = new Texture("enemy.png");
        platformTexture = new Texture("platform.png");
        backgroundTexture = new Texture("background.png");
        gameOverTexture = new Texture("gameover.png");

        platforms = new Array<>();
        enemies = new Array<>();
        generatePlatforms();

        // Place player on the first platform
        Rectangle firstPlatform = platforms.first();
        player = new Rectangle(firstPlatform.x + firstPlatform.width / 2 - 25, firstPlatform.y + firstPlatform.height, 50, 50);

<<<<<<< Updated upstream
        // Spawn the initial enemy
        spawnEnemy();
=======
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
            scenelifecyclemanager lifecycleManager = new scenelifecyclemanager(new inmemoryscenerepository());
            lifecycleManager.loadScene("main");

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
>>>>>>> Stashed changes
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

    private void spawnEnemy() {
        if (enemies.size < 2) {
            float x = MathUtils.random(camera.position.x - 400, camera.position.x + 400);
            float y = MathUtils.random(100, 480);
            enemies.add(new enemy(enemies.size, "enemy.png", x, y));
        }
    }

    @Override
    protected void update() {
        inputManager.updateInput();

        // GameState stuff
        if (gameState == GameState.GAME_OVER) {
            gameOverTimer -= Gdx.graphics.getDeltaTime();
            if (gameOverTimer <= 0) {
                resetPlayer();
                gameState = GameState.PLAYING;
            }
            return; // Stop updates during game over
        }

        // Update enemies
        for (int i = enemies.size - 1; i >= 0; i--) {
            enemy e = enemies.get(i);
            e.update();

            // If the enemy hits the player, reset its position
            if (player.overlaps(new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight()))) {
                resetEnemyPosition(e);
            }

            // If the enemy goes below the screen, reset its position
            if (e.getY() < 0) {
                resetEnemyPosition(e);
            }
        }

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

        // Spawn new enemies if there are less than 2
        if (MathUtils.randomBoolean(0.01f)) {
            spawnEnemy();
        }
    }

    private void resetEnemyPosition(enemy e) {
        float randomX = MathUtils.random(camera.position.x - 400, camera.position.x + 400); // Random X position
        e.setX(randomX); // Set new X
        e.setY(480); // Set Y to the top of the screen
    }

    @Override
    protected void draw() {
        // Clear screen to remove image residue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Black background

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Drawing game elements
        batch.draw(backgroundTexture, camera.position.x - 400, 0);
        batch.draw(playerTexture, player.x, player.y, player.width, player.height);
        for (enemy e : enemies) {
            e.draw(batch); // Enemy element
        }
        for (Rectangle platform : platforms) {
            batch.draw(platformTexture, platform.x, platform.y, platform.width, platform.height);
        }

        // Displaying "Game Over"
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
