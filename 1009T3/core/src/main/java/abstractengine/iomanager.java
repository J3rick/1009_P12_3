package abstractengine;

import abstractengine.interfaces.iinputhandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class iomanager implements iinputhandler {
    private boolean moveLeft;
    private boolean moveRight;
    private boolean jump;

    @Override
    public void updateInput() {
        this.moveLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        this.moveRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        this.jump = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    @Override
    public boolean isMovingLeft() {
        return this.moveLeft;
    }

    @Override
    public boolean isMovingRight() {
        return this.moveRight;
    }

    @Override
    public boolean isJumping() {
        return this.jump;
    }
}
