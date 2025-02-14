package abstractengine;

import com.badlogic.gdx.ApplicationAdapter;

public abstract class abstractengine extends ApplicationAdapter {
    protected abstract void init();
    protected abstract void update();
    protected abstract void draw();
    protected abstract void cleanup();

    @Override
    public void create() {
        init();
    }

    @Override
    public void render() {
        update();
        draw();
    }

    @Override
    public void dispose() {
        cleanup();
    }
}
