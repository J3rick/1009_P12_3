package io.github.some_example_name.lwjgl3;

import abstractengine.abstractengine;
import abstractengine.movementmanager;
import abstractengine.entitymanager;
import abstractengine.exceptionhandler;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import abstractengine.shutdown.simpleshutdownstrategy;
import abstractengine.scenetransitionmanager;
import abstractengine.scenelifecyclemanager;
import abstractengine.inmemoryscenerepository;

public class gamemaster extends abstractengine {
    private SpriteBatch batch;
    private movementmanager movementManager;
    private entitymanager entityManager;
    private iomanager inputManager;
    private OrthographicCamera worldCamera;
    private FitViewport viewport;
    private OrthographicCamera uiCamera; // Fixed camera for UI elements
    private collisionmanager collisionManager;
    private exceptionhandler exceptionHandler;
    private audiomanager audioManager;
    private Texture playerTexture, platformTexture, backgroundTexture, gameOverTexture, mainmenuBackgroundTexture, factsBg;
    // Instead of a single enemy texture, we now use an array of enemy texture file names.
    private EnemyFactory enemyFactory;
    private String[] enemyTextureFiles;
    private String[] collectibleTextureFiles;

    private player player;
    private Array<enemy> enemies;
    private Array<collectibles> collectible;
    private Array<platform> platforms;
    private platformerscene platformerScene;
    private gameoverscene gameOverScene;
    private factsScene factScene;
    private mainmenuscene mainMenuScene;
    
    private boolean lifeLostRecently = false;
    private boolean horizontalEnemyDespawned = true;
    
    private scenetransitionmanager sceneTransitionManager;
    private scenelifecyclemanager sceneLifecycleManager;
    
    private inmemoryscenerepository memoryscenerepository;

    private float velocityY = 0;
    private final float gravity = -700;
    private final float jumpPower = 400;
    // Replace the final speed with a mutable field for the player's speed.
    private float playerSpeed = 200;
    private boolean isJumping = false;
    private float lastPlatformX = 100;
    private boolean onPlatform;
    private final float startX = 100, startY = 150;
    private final float fallThreshold = -100;
    private final float heightThreshold = 480;
    private gametimer gameTimer;

    // Virtual resolution constants
    private final float VIRTUAL_WIDTH = 800;
    private final float VIRTUAL_HEIGHT = 480;

    // Player lives and game over timer
    private int lives = 5;
    private float gameOverTimer = 0;
    private final float gameOverDuration = 3;
    
    // Score tracking
    private int score = 0;
    
    public enum gamestate { PLAYING, GAME_OVER, RESPAWNING, PAUSED, MAIN_MENU }
    private gamestate gameState = gamestate.MAIN_MENU;
    
    // BitmapFont to display lives and score
    private BitmapFont font;

    @Override
    protected void init() {
        try {
        	// Inside your game initialization class
            factsBg = new Texture("facts_background.png");  // Load background texture
            
            exceptionHandler = new exceptionhandler(new gdxlogger(), new simpleshutdownstrategy());
            batch = new SpriteBatch();
            movementManager = new movementmanager(new fallingmovementstrategy(150));
            entityManager = new entitymanager();
            inputManager = new iomanager();
          
            // Initialize world camera and viewport
            worldCamera = new OrthographicCamera();
            viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, worldCamera);
            viewport.apply();
            worldCamera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
            worldCamera.update();
            
            // Initialize a separate UI camera (fixed)
            uiCamera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
            uiCamera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
            uiCamera.update();
            
            collisionManager = new collisionmanager(new boundingboxcollisionstrategy());

            // Load textures for the game objects.
            playerTexture = new Texture("player.png");
            platformTexture = new Texture("platform.png");
            backgroundTexture = new Texture("background.png");
            gameOverTexture = new Texture("gameover.png");
            mainmenuBackgroundTexture = new Texture("menu_background.png");
            
            // Define an array of enemy texture file names.
            enemyTextureFiles = new String[] {"enemy1.png", "enemy2.png", "enemy3.png"};
            enemyFactory = new EnemyFactory(enemyTextureFiles);
            
            // Define an array of collectible texture file names.
            collectibleTextureFiles = new String[] {"collectible1.png", "collectible2.png", "collectible3.png"};

            // Initialize BitmapFont for displaying lives and set its color to black.
            font = new BitmapFont();
            font.setColor(Color.BLACK);

            platforms = new Array<>();
            enemies = new Array<>();
            collectible = new Array<>();
            generatePlatforms();

            player = new player(1, "player.png", startX, startY);
            collisionManager.addCollidable(player);

            // Position the player on the first platform.
            platform firstPlatform = platforms.first();
            player.setPosition(firstPlatform.getX() + firstPlatform.getWidth() / 2 - 25,
                                 firstPlatform.getY() + firstPlatform.getHeight());

            // Create scenes and add necessary entities.
            mainMenuScene = new mainmenuscene("main menu",mainmenuBackgroundTexture, Color.GREEN, worldCamera);
            platformerScene = new platformerscene("main", backgroundTexture, Color.BLUE, worldCamera);
            platformerScene.addEntityToList(player);
            gameOverScene = new gameoverscene("game over", gameOverTexture, Color.GREEN, worldCamera);
            factScene = new factsScene("facts", factsBg, worldCamera, sceneTransitionManager);
            
            memoryscenerepository = new inmemoryscenerepository();
            
            sceneTransitionManager = new scenetransitionmanager(memoryscenerepository);
            sceneTransitionManager.addScene(mainMenuScene);
            sceneTransitionManager.addScene(platformerScene);
            sceneTransitionManager.addScene(gameOverScene);
            sceneTransitionManager.addScene(factScene);
            sceneTransitionManager.loadScene("main menu"); // load first scene
            
            sceneLifecycleManager = new scenelifecyclemanager();
            sceneLifecycleManager.setCurrentScene(sceneTransitionManager.getCurrentScene());
            sceneLifecycleManager.update();
            
            spawnEnemy();
            for (int i = 0; i < enemies.size; i++) {
                collisionManager.addCollidable(enemies.get(i));
            }

            gameTimer = new gametimer();
            
            // Initialize audio manager with all four audio files.
            // Ensure that "background_music.mp3", "collision.mp3", "collectible.mp3", and "fall.mp3" are in your assets.
            audioManager = audiomanager.getInstance("background_music.mp3", "collision.mp3", "collectible.mp3", "fall.mp3");
            audiomanager audioManager = audiomanager.getInstance();
            audioManager.playBackgroundMusic();
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
        // Increase horizontal gap so platforms are further apart.
        float x = lastPlatformX + MathUtils.random(200, 350);
        // Vertical position remains the same.
        float y = MathUtils.random(100, 300);
        // Use the slightly shorter platform width (180 pixels).
        platform newPlatform = new platform(platforms.size, x, y, 180, 20);
        platforms.add(newPlatform);
        collisionManager.addCollidable(newPlatform);
        lastPlatformX = x;
    }

    // Spawn a vertical enemy.
    private void spawnEnemy() {
        if (enemies.size < 2) {
            float x = MathUtils.random(worldCamera.position.x - 400, worldCamera.position.x + 400);
            
            enemy newEnemy = enemyFactory.createEnemy(EnemyFactory.EnemyType.VERTICAL, enemies.size, x, heightThreshold);
            enemies.add(newEnemy);
            collisionManager.addCollidable(newEnemy);
        }
    }
    
    // Spawning horizontal enemy.
    private void spawnHorizontalEnemy() {
        boolean hasHorizontalEnemy = false;
        for (enemy e : enemies) {
            if (e.getMovementType() == enemy.MovementType.HORIZONTAL) {
                hasHorizontalEnemy = true;
                break;
            }
        }
        if (!hasHorizontalEnemy && horizontalEnemyDespawned) {
            horizontalEnemyDespawned = false;
            
            float leftEdgeOfScreen = worldCamera.position.x - (worldCamera.viewportWidth / 2);
            float x = leftEdgeOfScreen - 50;
            float y = player.getY() + (player.getHeight() / 4);
                        
            enemy horizontalEnemy = enemyFactory.createEnemy(EnemyFactory.EnemyType.HORIZONTAL, enemies.size, x, y);
            
            enemies.add(horizontalEnemy);
            collisionManager.addCollidable(horizontalEnemy);
        }
    }
    
    private void spawnCollectibles() {
        if (collectible.size < 2) {
            float x = MathUtils.random(worldCamera.position.x - 400, worldCamera.position.x + 400);
            int randomIndex = MathUtils.random(0, collectibleTextureFiles.length - 1);
            String chosenCollectibleTextureFile = collectibleTextureFiles[randomIndex];
            collectible.add(new collectibles(collectible.size, chosenCollectibleTextureFile, x, heightThreshold));
        }
    }

    private void loseLife() {
        if (!lifeLostRecently) {  // Only process if not already triggered
            collectible.clear();
            enemies.clear();
            horizontalEnemyDespawned = true;
            lives--;
            lifeLostRecently = true; // Mark that we've lost a life for this event
            // If the player's speed is still at the default (200), apply the penalty.
            if (playerSpeed == 200) {
                playerSpeed = 150;  // Reduce speed as a penalty.
            }
            gameState = gamestate.RESPAWNING;
            gameOverTimer = gameOverDuration;
            gameTimer.pause(); // Pause the timer.
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
            // Play fall sound before handling life loss.
            audiomanager.getInstance().playFallSound();
            loseLife();
        }
        
        for (enemy e : enemies) {
            if (player.getBounds().overlaps(e.getBounds())) {
                audiomanager.getInstance().playCollisionSound();
                loseLife();
                break;
            }
        }
        
    }

    private void checkPlayerEnemyCollisions() {
        for (enemy e : enemies) {
            if (player.getBounds().overlaps(e.getBounds())) {
                loseLife();
                break;
            }
        }
    }
    
    private void checkPlayerCollectibleCollisions() {
        for (int i = collectible.size - 1; i >= 0; i--) {
            collectibles e = collectible.get(i);
            if (player.getBounds().overlaps(e.getBounds())) {
                if (!e.getCollect()) {
                    e.setCollect();

                    // Generate a unique name using the current time.
                    String uniqueName = "facts" + System.currentTimeMillis();
                    // Create a new factsScene instance, which in its constructor calls generateRandomFact()
                    factsScene newFactScene = new factsScene(uniqueName, factsBg, worldCamera, sceneTransitionManager);
                    sceneTransitionManager.addScene(newFactScene);
                    sceneTransitionManager.loadScene(uniqueName);
                    sceneLifecycleManager.setCurrentScene(sceneTransitionManager.getCurrentScene());
                    sceneLifecycleManager.render(batch);
                    gameState = gamemaster.gamestate.PAUSED;
                }

                audiomanager.getInstance().playCollectibleSound();
                score += 10;
                collisionManager.removeCollidable(e);
                collectible.removeIndex(i);
                System.out.println("Collected item! Score: " + score);
                break;
            }
        }
    }

    public void setGameState(gamestate newState) {
    	System.out.println("Game state changing from " + this.gameState + " to " + newState);
        this.gameState = newState;
        
        if (newState == gamestate.PLAYING) {
            // ✅ Reload the background to ensure it renders

            backgroundTexture = new Texture("background.png");
            System.out.println("Background texture reloaded after pause.");
            
        }
    }

    @Override
    protected void update() {
        inputManager.updateInput();
        if (gameState == gamestate.GAME_OVER) {
            gameOverTimer -= Gdx.graphics.getDeltaTime();
//            if (gameOverTimer <= 0) {
//                // Handle final game over (e.g., show game over screen).
//                gameTimer.reset();
//            }
            return;
        }

        if (gameState == gamestate.RESPAWNING) {
            gameOverTimer -= Gdx.graphics.getDeltaTime();
            if (gameOverTimer <= 0) {
                if (lives > 0) {
                    resetPlayer();
                    gameState = gamestate.PLAYING;
                    gameTimer.resume();
                } else {
                    gameState = gamestate.GAME_OVER;
                }
            }
            return;
        }

        if (gameState == gamestate.PLAYING) {
            gameTimer.update();
            sceneLifecycleManager.update();
        }
        if (gameState == gamestate.PAUSED) {
            if (sceneTransitionManager.getCurrentScene() != null) {
            	sceneLifecycleManager.update();
            } else {
                System.out.println("WARNING: No active scene to update");
            }
            return;  // Stop updating game logic while paused
        }
        
        if (gameState == gamestate.MAIN_MENU) {
        	return;  // Stop updating game logic while paused
        }

        updateEnemies();
        updateCollectibles();
        updatePlayer();
        updateCamera();
        updatePlatforms();

        checkPlatformCollisions();
        checkPlayerEnemyCollisions();
        checkPlayerCollectibleCollisions();
        collisionManager.checkCollisions();
        

        if (MathUtils.randomBoolean(0.01f)) {
            spawnEnemy();
            spawnCollectibles();
        }
        
        if (MathUtils.randomBoolean(0.01f)) {
            spawnHorizontalEnemy();
        }

        gameTimer.update();
    }

    private void updateEnemies() {
        for (int i = enemies.size - 1; i >= 0; i--) {
            enemy e = enemies.get(i);
            e.update();
            if (e.getMovementType() == enemy.MovementType.VERTICAL) {
                movementManager.updateEnemyMovement(e, Gdx.graphics.getDeltaTime());
                if (e.getY() < 0) {
                    resetEnemyPosition(e);
                }
            } else if (e.getMovementType() == enemy.MovementType.HORIZONTAL) {
                movementManager.updateHorizontalEnemyMovement(e, Gdx.graphics.getDeltaTime());
                if (e.getX() > worldCamera.position.x + 600) {
                    collisionManager.removeCollidable(e);
                    enemies.removeIndex(i);
                    horizontalEnemyDespawned = true;
                }
            }
        }
    }
    
    private void updateCollectibles() {
        for (int i = collectible.size - 1; i >= 0; i--) {
            collectibles e = collectible.get(i);
            e.update();
            movementManager.updateCollectibleMovement(e, Gdx.graphics.getDeltaTime());
            if (e.getY() < 0) {
                resetCollectiblePosition(e);
            }
        }
    }

    private void updatePlayer() {
        if (inputManager.isMovingLeft()) {
            player.setX(player.getX() - playerSpeed * Gdx.graphics.getDeltaTime());
        }
        if (inputManager.isMovingRight()) {
            player.setX(player.getX() + playerSpeed * Gdx.graphics.getDeltaTime());
        }
        if (inputManager.isJumping() && !isJumping) {
            velocityY = jumpPower;
            isJumping = true;
        }
        velocityY += gravity * Gdx.graphics.getDeltaTime();
        player.setY(player.getY() + velocityY * Gdx.graphics.getDeltaTime());

        onPlatform = false;
        if (player.getY() < fallThreshold) {
            loseLife();
        }
    }

    // Update the world camera to follow the player horizontally.
    private void updateCamera() {
        worldCamera.position.x = player.getX() + player.getWidth() / 2;
        worldCamera.update();
    }

    private void updatePlatforms() {
        if (player.getX() > lastPlatformX - 400) {
            addPlatform();
        }
    }

    private void resetEnemyPosition(enemy e) {
        float randomX = MathUtils.random(worldCamera.position.x - 400, worldCamera.position.x + 400);
        e.setX(randomX);
        e.setY(480);
    }
    
    private void resetCollectiblePosition(collectibles e) {
        float randomX = MathUtils.random(worldCamera.position.x - 400, worldCamera.position.x + 400);
        e.setX(randomX);
        e.setY(480);
    }

    @Override
    protected void draw() {
        // Set the world camera
        viewport.apply();
        batch.setProjectionMatrix(worldCamera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // If the game is paused (i.e. fact scene is active), let the fact scene render itself.
        if (gameState == gamestate.PAUSED) {
            if (batch.isDrawing()) {
                batch.end();
            }
            sceneLifecycleManager.render(batch);
        } else if (gameState == gamestate.GAME_OVER) {
        	if (sceneTransitionManager.getCurrentScene().getName() != "game over") {
        		sceneTransitionManager.loadScene("game over");
        		sceneLifecycleManager.setCurrentScene(sceneTransitionManager.getCurrentScene());
                sceneLifecycleManager.render(batch);
        	}
        	// draw the background game over img
        	batch.begin();
        	sceneLifecycleManager.render(batch, worldCamera.position.x - VIRTUAL_WIDTH / 2,
                    worldCamera.position.y - VIRTUAL_HEIGHT / 2, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    		batch.end();
                    
        } else if (gameState == gamestate.MAIN_MENU){
        	if (sceneTransitionManager.getCurrentScene().getName() != "main menu") {
        		sceneTransitionManager.loadScene("main menu");
        		sceneLifecycleManager.setCurrentScene(sceneTransitionManager.getCurrentScene());
                sceneLifecycleManager.render(batch);
        	}
        	// draw the background game over img
        	batch.begin();
        	sceneLifecycleManager.render(batch, worldCamera.position.x - VIRTUAL_WIDTH / 2,
                    worldCamera.position.y - VIRTUAL_HEIGHT / 2, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    		batch.end();
        
        }
        else{
            // Assumes that this is main scene
        	batch.begin();
            batch.draw(backgroundTexture, worldCamera.position.x - VIRTUAL_WIDTH / 2,
                       worldCamera.position.y - VIRTUAL_HEIGHT / 2, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
            batch.draw(playerTexture, player.getX(), player.getY(), player.getWidth(), player.getHeight());
            for (enemy e : enemies) {
                e.draw(batch);
            }
            for (collectibles e : collectible) {
                e.draw(batch);
            }
            for (platform platform : platforms) {
                batch.draw(platformTexture, platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
            }
            
         // Render UI elements using the fixed UI camera
            batch.setProjectionMatrix(uiCamera.combined);
            font.draw(batch, "Lives: " + lives, 10, VIRTUAL_HEIGHT - 10);
            font.draw(batch, "Score: " + score, 10, VIRTUAL_HEIGHT - 30);
            font.draw(batch, "Time: " + gameTimer.getElapsedTime() / 1000, 10, VIRTUAL_HEIGHT - 50);
            batch.end();
        } 
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiCamera.update();
    }

    private void resetPlayer() {
        platform firstPlatform = platforms.first();
        player.setX(firstPlatform.getX() + firstPlatform.getWidth() / 2 - 25);
        player.setY(firstPlatform.getY() + firstPlatform.getHeight());
        velocityY = 0;
        isJumping = false;
        // Reset the flag so that future collisions can cause a life loss.
        lifeLostRecently = false;
        horizontalEnemyDespawned = true;
        // Note: We do NOT reset playerSpeed here so the penalty persists into the next game.
    }
    
    @Override
    protected void cleanup() {
        batch.dispose();
        playerTexture.dispose();
        platformTexture.dispose();
        backgroundTexture.dispose();
        gameOverTexture.dispose();
        font.dispose();
        audioManager.dispose();
    }
}
