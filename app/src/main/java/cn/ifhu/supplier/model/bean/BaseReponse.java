package cn.ifhu.supplier.model.bean;

/**
 * @author fuhongliang
 */
public class BaseReponse {

    public static int SUCCESS_CODE = 200;
    public static int TOKENMISSION_CODE = 3001;
    public static int TOKENTFAKE_CODE = 3000;
    public static int TOKENTIMEOUT_CODE = 3002;

    public int code = 0;
    public String msg;




    public boolean isSuccess() {
        if (code == SUCCESS_CODE) {
            return true;
        }
        return false;
    }

    public boolean isTokenTimeOut() {
        if (code == TOKENTIMEOUT_CODE || code == TOKENMISSION_CODE || code == TOKENTFAKE_CODE) {
            return true;
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }
}

