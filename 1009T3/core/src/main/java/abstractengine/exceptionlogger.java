package abstractengine;

import abstractengine.interfaces.ilogger;
import abstractengine.logging.gdxlogger;

public class exceptionlogger implements ilogger{

    // gdxlogger and exceptionlogger seem to do the same thing, necessary?

    private gdxlogger gdxLogger;
    private String userErrorMsg = "The following error has occurred: ";

    public exceptionlogger(gdxlogger in_gdxlogger) {
        this.gdxLogger = in_gdxlogger;
        return;
    }

    public String getUserErrorMsg() {
        return userErrorMsg;
    }

    public void setUserErrorMsg(String msg) {
        this.userErrorMsg = msg;
    }

    // No error state is stored; these methods return empty or do nothing.
    public String getExceptionMsg() {
        return "";
    }

    public void setExceptionMsg(Exception ex) {
        // Do nothing – error state is managed externally if needed.
    }

    @Override
    public void logError(String message) {
        System.out.println("Error: " + message);
    }

    @Override
    public void logWarning(String message) {
        System.out.println("Warning: " + message);
    }
}
