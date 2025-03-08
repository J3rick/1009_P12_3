package abstractengine;

import abstractengine.interfaces.ierrorhandler;
import abstractengine.interfaces.ilogger;
import abstractengine.interfaces.ishutdownstrategy;

public class exceptionhandler implements ierrorhandler {
    private String userErrorMsg = "The following error has occurred: ";
    private ilogger logger;
    private ishutdownstrategy shutdownStrategy;

    public exceptionhandler(ilogger logger, ishutdownstrategy shutdownStrategy) {
        this.logger = logger;
        this.shutdownStrategy = shutdownStrategy;
    }

    @Override
    public String getUserErrorMsg() {
        return userErrorMsg;
    }

    @Override
    public void setUserErrorMsg(String msg) {
        this.userErrorMsg = msg;
    }

    // No error state is stored; these methods return empty or do nothing.
    @Override
    public String getExceptionMsg() {
        return "";
    }

    @Override
    public void setExceptionMsg(Exception ex) {
        // Do nothing – error state is managed externally if needed.
    }

    @Override
    public void exceptionOccurred(Exception ex) {
        logger.logError(userErrorMsg + ex.getMessage());
        // Delegate shutdown behavior to the injected strategy.
        shutdownStrategy.shutdown(userErrorMsg + ex.getMessage());
    }
}
