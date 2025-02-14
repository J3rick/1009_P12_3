package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class entity {
    protected int id;
    protected String name;
    protected float x, y;
    protected float width, height;

    public entity(int id, String name, float x, float y, float width, float height) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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

    public abstract void update();
    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();
}
