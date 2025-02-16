package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class entity {
    private final int id;
    private final String name;
    private float x, y;
    private float width, height;

    public entity(int id, String name, float x, float y, float width, float height) {
        if (id < 0) throw new IllegalArgumentException("ID cannot be negative.");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty.");
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("Width and height must be greater than zero.");

        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Getters (Encapsulation: Read-only ID & Name)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Setters with validation
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        if (width <= 0) throw new IllegalArgumentException("Width must be greater than zero.");
        this.width = width;
    }

    public void setHeight(float height) {
        if (height <= 0) throw new IllegalArgumentException("Height must be greater than zero.");
        this.height = height;
    }

    // Abstract methods (Must be implemented by subclasses)
    public abstract void update();
    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
