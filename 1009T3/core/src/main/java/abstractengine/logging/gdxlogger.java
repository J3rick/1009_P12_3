package abstractengine.logging;

import com.badlogic.gdx.Gdx;
import abstractengine.interfaces.ilogger;

public class gdxlogger implements ilogger {
    @Override
    public void logError(String message) {
        Gdx.app.error("Error", message);
    }

    @Override
    public void logWarning(String message) {
        Gdx.app.log("Warning", message);
    }
}
