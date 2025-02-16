package abstractengine;

import com.badlogic.gdx.math.Rectangle;

public final class collisionmanager {

    // Private constructor to prevent instantiation
    private collisionmanager() {
        throw new UnsupportedOperationException("Utility class - instantiation not allowed");
    }

    // Public static method for collision detection
    public static boolean detectCollision(entity obj1, entity obj2) {
        if (obj1 == null || obj2 == null) {
            throw new IllegalArgumentException("Collision check failed: One or both entities are null.");
        }

        Rectangle rect1 = new Rectangle(obj1.getX(), obj1.getY(), obj1.getWidth(), obj1.getHeight());
        Rectangle rect2 = new Rectangle(obj2.getX(), obj2.getY(), obj2.getWidth(), obj2.getHeight());

        return rect1.overlaps(rect2);
    }
}
