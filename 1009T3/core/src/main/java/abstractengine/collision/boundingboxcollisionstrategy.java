package abstractengine.collision;

import abstractengine.interfaces.icollidable;
import com.badlogic.gdx.math.Rectangle;

public class boundingboxcollisionstrategy implements icollisionstrategy {
    @Override
    public boolean detectCollision(icollidable a, icollidable b) {
        Rectangle r1 = a.getBounds();
        Rectangle r2 = b.getBounds();
        return r1.overlaps(r2);
    }
}
