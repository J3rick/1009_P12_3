package abstractengine;

import com.badlogic.gdx.Gdx;

public class exceptionhandler {
    private String userErrorMsg = "The following error has occured: ";
    private String exceptionMsg = "";

    public exceptionhandler(){
        return;
    }

    public String getUserErrorMsg(){
        return userErrorMsg;
    }
    public void setUserErrorMsg(String userErrorMsg_in){
        userErrorMsg = userErrorMsg_in;
    }
    public String getExceptionMsg(){
        return exceptionMsg;
    }
    public void setExceptionMsg(Exception ex_in){
        exceptionMsg = ex_in.getMessage();
    }

    public void exceptionOccured(Exception ex_in){
        Gdx.app.error("Error", userErrorMsg + ex_in.getMessage());
        Gdx.app.exit();
    }
}

