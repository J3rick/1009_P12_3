package abstractengine.collision;

import abstractengine.interfaces.icollidable;

public interface icollisionstrategy {
    boolean detectCollision(icollidable a, icollidable b);
}
