package abstractengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class iomanager {
    private boolean moveLeft, moveRight, jump;

    public void updateInput() {
        moveLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        moveRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        jump = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    public boolean isMovingLeft() {
        return moveLeft;
    }

    public boolean isMovingRight() {
        return moveRight;
    }

    public boolean isJumping() {
        return jump;
    }
}
