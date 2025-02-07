package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class gamemaster extends ApplicationAdapter {
    private Texture playerImage;
    private Texture platformImage;
    private SpriteBatch batch;
    private Rectangle player;
    private List<Rectangle> platforms;
    private Vector2 velocity;
    private float gravity = -0.5f;
    private boolean isJumping;

    @Override
    public void create() {
        playerImage = new Texture(Gdx.files.internal("bucket.png"));
        platformImage = new Texture(Gdx.files.internal("droplet.png"));
        batch = new SpriteBatch();

        player = new Rectangle();
        player.x = 100;
        player.y = 150;
        player.width = playerImage.getWidth();
        player.height = playerImage.getHeight();

        platforms = new ArrayList<>();
        Rectangle ground = new Rectangle(0, 50, 800, 20);
        platforms.add(ground);

        velocity = new Vector2(0, 0);
        isJumping = false;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 0); //Black background
        batch.begin();
        batch.draw(playerImage, player.x, player.y, player.width, player.height);
        for (Rectangle platform : platforms) {
            batch.draw(platformImage, platform.x, platform.y, platform.width, platform.height);
        }
        batch.end();

        // Apply gravity
        velocity.y += gravity;
        player.y += velocity.y;

        // Check collision with platforms
        for (Rectangle platform : platforms) {
            if (player.overlaps(platform)) {
                player.y = platform.y + platform.height;
                velocity.y = 0;
                isJumping = false;
            }
        }

        // Input handling
        if (Gdx.input.isKeyPressed(Keys.LEFT)) player.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) player.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.SPACE) && !isJumping) {
            velocity.y = 10;
            isJumping = true;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerImage.dispose();
        platformImage.dispose();
    }
}