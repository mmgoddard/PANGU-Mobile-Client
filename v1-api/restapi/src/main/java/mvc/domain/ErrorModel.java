package mvc.domain;

/**
 * Created by Mark on 02/04/15.
 */
public class ErrorModel {
    private String errorCode;
    private String errorDesc;

    public ErrorModel(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
