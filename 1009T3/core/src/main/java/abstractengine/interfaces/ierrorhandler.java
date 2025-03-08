package abstractengine.interfaces;

/**
 * An interface representing a generic error handler.
 */
public interface ierrorhandler {
    /**
     * Sets a user-friendly error message.
     * @param msg The error message.
     */
    void setUserErrorMsg(String msg);

    /**
     * @return The current user-friendly error message.
     */
    String getUserErrorMsg();

    /**
     * Sets the exception message based on the given Exception.
     * @param ex The exception to extract a message from.
     */
    void setExceptionMsg(Exception ex);

    /**
     * @return The currently stored exception message.
     */
    String getExceptionMsg();

    /**
     * Handles the occurrence of an exception, e.g., logging or application shutdown.
     * @param ex The exception that occurred.
     */
    void exceptionOccurred(Exception ex);
}
