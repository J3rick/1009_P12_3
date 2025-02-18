package abstractengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class iomanager {
    private boolean moveLeft;
    private boolean moveRight;
    private boolean jump;

    public void updateInput() {
        this.moveLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        this.moveRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        this.jump = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    // Getters to provide controlled access
    public boolean isMovingLeft() {
        return this.moveLeft;
    }

    public boolean isMovingRight() {
        return this.moveRight;
    }

    public boolean isJumping() {
        return this.jump;
    }
}
