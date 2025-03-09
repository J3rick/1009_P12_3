package abstractengine.error;

import abstractengine.interfaces.ierrormessagestore;

public class basicerrormessagestore implements ierrormessagestore {
    private String errorMessage = "";
    
    @Override
    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }
    
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
