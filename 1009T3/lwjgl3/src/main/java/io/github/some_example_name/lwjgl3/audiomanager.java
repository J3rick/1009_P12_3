package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * audiomanager is responsible for handling background music and sound effects.
 */
public class audiomanager {
    private AssetManager assetManager;
    private Music backgroundMusic;
    private Sound collisionSound;
    private Sound collectibleSound;
    private Sound fallSound;

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
        
        // Set background music to loop and lower volume (softer).
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);  // Lower volume (range 0.0f to 1.0f)
        
        // Prime the sound effects (optional)
        collisionSound.play(0f);
        collectibleSound.play(0f);
        fallSound.play(0f);
    }
    
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
