package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * audiomanager is responsible for handling background music and sound effects.
 * It uses AssetManager to preload assets so that playback is immediate.
 * 
 * Note: Although Java convention is to use PascalCase for class names,
 * this class is in lowercase per your request.
 */
public class audiomanager {
    private AssetManager assetManager;
    private Music backgroundMusic;
    private Sound collisionSound;
    private Sound collectibleSound;
    private Sound fallSound;

    /**
     * Loads audio assets using AssetManager.
     *
     * @param backgroundMusicFilePath  file path for the background music.
     * @param collisionSoundFilePath   file path for the enemy collision sound effect.
     * @param collectibleSoundFilePath file path for the collectible pickup sound effect.
     * @param fallSoundFilePath        file path for the fall-off-platform sound effect.
     */
    public audiomanager(String backgroundMusicFilePath, String collisionSoundFilePath, String collectibleSoundFilePath, String fallSoundFilePath) {
        assetManager = new AssetManager();
        
        // Preload all audio assets.
        assetManager.load(backgroundMusicFilePath, Music.class);
        assetManager.load(collisionSoundFilePath, Sound.class);
        assetManager.load(collectibleSoundFilePath, Sound.class);
        assetManager.load(fallSoundFilePath, Sound.class);
        
        // Block until all assets are loaded.
        assetManager.finishLoading();
        
        // Retrieve assets.
        backgroundMusic = assetManager.get(backgroundMusicFilePath, Music.class);
        collisionSound = assetManager.get(collisionSoundFilePath, Sound.class);
        collectibleSound = assetManager.get(collectibleSoundFilePath, Sound.class);
        fallSound = assetManager.get(fallSoundFilePath, Sound.class);
        
        // Configure background music.
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        
        // Prime the sound effects (optional but can reduce first-play latency).
        collisionSound.play(0f);
        collectibleSound.play(0f);
        fallSound.play(0f);
    }
    
    // --- Background Music Controls ---
    public void playBackgroundMusic() {
        backgroundMusic.play();
    }
    
    public void pauseBackgroundMusic() {
        backgroundMusic.pause();
    }
    
    public void resumeBackgroundMusic() {
        backgroundMusic.play();
    }
    
    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }
    
    // --- Sound Effect Controls ---
    public void playCollisionSound() {
        collisionSound.play(1.0f);
    }
    
    public void playCollectibleSound() {
        collectibleSound.play(1.0f);
    }
    
    public void playFallSound() {
        fallSound.play(1.0f);
    }
    
    public void dispose() {
        backgroundMusic.dispose();
        collisionSound.dispose();
        collectibleSound.dispose();
        fallSound.dispose();
        assetManager.dispose();
    }
}
