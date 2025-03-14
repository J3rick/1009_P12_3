package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * audiomanager is responsible for handling background music and sound effects.
 */
public class audiomanager {
    private Music backgroundMusic;
    private Sound collisionSound;
    private Sound collectibleSound;

    public audiomanager(String backgroundMusicFilePath, String collisionSoundFilePath, String collectibleSoundFilePath) {
        // Load background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(backgroundMusicFilePath));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);

        // Load sound effects
        collisionSound = Gdx.audio.newSound(Gdx.files.internal(collisionSoundFilePath));
        collectibleSound = Gdx.audio.newSound(Gdx.files.internal(collectibleSoundFilePath));

        // Prime the sound effects to reduce latency on first playback:
        collisionSound.play(0f);
        collectibleSound.play(0f);
    }

    // Background music controls
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

    // Sound effect controls
    public void playCollisionSound() {
        collisionSound.play(1.0f);
    }

    public void playCollectibleSound() {
        collectibleSound.play(1.0f);
    }

    public void dispose() {
        backgroundMusic.dispose();
        collisionSound.dispose();
        collectibleSound.dispose();
    }
}
