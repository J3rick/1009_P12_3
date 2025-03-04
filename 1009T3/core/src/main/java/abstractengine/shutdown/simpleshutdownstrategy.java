package abstractengine.shutdown;

import com.badlogic.gdx.Gdx;
import abstractengine.interfaces.ishutdownstrategy;

public class simpleshutdownstrategy implements ishutdownstrategy {
    @Override
    public void shutdown(String errorMessage) {
        // Log the shutdown error (and optionally show a crash screen)
        Gdx.app.error("Shutdown", errorMessage);
        System.out.println("Shutting down: " + errorMessage);
        // Optionally exit the application:
        // Gdx.app.exit();
    }
}
