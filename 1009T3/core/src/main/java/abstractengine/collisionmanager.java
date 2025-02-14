package abstractengine;

import com.badlogic.gdx.math.Rectangle;

public class collisionmanager {
    public static boolean detectCollision(entity obj1, entity obj2) {
        Rectangle rect1 = new Rectangle(obj1.getX(), obj1.getY(), obj1.getWidth(), obj1.getHeight());
        Rectangle rect2 = new Rectangle(obj2.getX(), obj2.getY(), obj2.getWidth(), obj2.getHeight());
        return rect1.overlaps(rect2);
    }
}
