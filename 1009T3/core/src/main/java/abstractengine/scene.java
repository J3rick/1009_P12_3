package abstractengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class scene {
    protected String name;

    public scene(String name) {
        this.name = name;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}
