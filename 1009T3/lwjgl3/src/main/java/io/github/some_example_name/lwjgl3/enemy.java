package io.github.some_example_name.lwjgl3;

import abstractengine.entity;
import abstractengine.interfaces.imovable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class enemy extends entity implements imovable {
    private Texture texture;
    private static final float FALL_SPEED = 150; // Constant for controlled downward movement
    private static final float HORIZONTAL_SPEED = 150; //Controlled horizontal movement
    
    public enum MovementType {
    	VERTICAL,
    	HORIZONTAL
    }
    
    private MovementType movementType;
    private boolean movingRight = true; 
    
    public enemy(int id, String textureFile, float x, float y, MovementType movementType) {
        super(id, "enemy", x, y, 50, 50); // Use parent constructor
        this.texture = new Texture(Gdx.files.internal(textureFile));
        this.movementType = movementType;
    }
    
    //Backwards compatiblity for vertical movements
    public enemy(int id,String textureFile, float x, float y) {
    	this(id, textureFile, x, y, MovementType.VERTICAL);
    }
    
    @Override
    public void update() {
        // You may choose to call updateMovement here or leave it empty if handled elsewhere.
    }

    @Override
    public void updateMovement(float deltaTime) {
    	switch (movementType) {
    	case VERTICAL:
    		setY(getY() - FALL_SPEED * deltaTime);
    		break;
    	
    	case HORIZONTAL:
    		if (movingRight) {
    			setX(getX() + HORIZONTAL_SPEED * deltaTime);
    		}
    		else {
    			setX(getX() - HORIZONTAL_SPEED * deltaTime);
    		}
    		break;
    	}
    }
    
    // Method to set movement direction for horizontal enemies
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
    
    // Method to get the movement type
    public MovementType getMovementType() {
        return movementType;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
