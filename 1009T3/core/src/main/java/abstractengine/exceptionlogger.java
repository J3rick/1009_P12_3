package abstractengine;

import abstractengine.interfaces.ilogger;

public class exceptionlogger implements ilogger{
    private String userErrorMsg = "The following error has occurred: ";

    public exceptionlogger() {
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
