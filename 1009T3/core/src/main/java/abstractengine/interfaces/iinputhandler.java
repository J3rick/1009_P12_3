package abstractengine.interfaces;

/**
 * An interface representing a generic input handler.
 */
public interface iinputhandler {
    /**
     * Polls and updates the current input state.
     */
    void updateInput();

    /**
     * @return true if the left movement input is active, otherwise false.
     */
    boolean isMovingLeft();

    /**
     * @return true if the right movement input is active, otherwise false.
     */
    boolean isMovingRight();

    /**
     * @return true if the jump input is active, otherwise false.
     */
    boolean isJumping();
}
