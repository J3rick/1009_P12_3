package abstractengine;

import abstractengine.interfaces.icollidable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class entity implements icollidable {
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

    public int getId() { return id; }
    public String getName() { return name; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(float width) {
        if (width <= 0) throw new IllegalArgumentException("Width must be greater than zero.");
        this.width = width;
    }
    public void setHeight(float height) {
        if (height <= 0) throw new IllegalArgumentException("Height must be greater than zero.");
        this.height = height;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
