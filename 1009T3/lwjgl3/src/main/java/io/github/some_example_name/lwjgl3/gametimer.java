package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.utils.TimeUtils;

public class gametimer {
    private long startTime;
    private long elapsedTime;
    private boolean paused;

    public gametimer() {
        startTime = TimeUtils.millis();
        paused = false;
    }

    public void update() {
        if (!paused) {
            elapsedTime = TimeUtils.timeSinceMillis(startTime);
        }
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void reset() {
        startTime = TimeUtils.millis();
        elapsedTime = 0;
        paused = false;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        startTime = TimeUtils.millis() - elapsedTime;
    }
}