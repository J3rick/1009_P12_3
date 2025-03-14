	package abstractengine.interfaces;
	
	/**
	 * An interface representing an object that can move.
	 */
	public interface imovable {
	    /**
	     * Updates the movement behavior of an object.
	     * @param deltaTime The time elapsed since the last update.
	     */
	    void updateMovement(float deltaTime);
	}
