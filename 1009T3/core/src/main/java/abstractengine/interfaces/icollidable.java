package abstractengine.interfaces;

import com.badlogic.gdx.math.Rectangle;

/**
 * An interface representing an object that can be collided with.
 */
public interface icollidable {
    /**
     * Returns the bounding rectangle for collision detection.
     */
    Rectangle getBounds();
}
