package cn.ifhu.supplier.model.bean;

import com.orhanobut.logger.Logger;

/**
 * @author fuhongliang
 */
public class BaseEntity<T> {

    public static int SUCCESS_CODE = 0;
    public static int TOKENMISSION_CODE = 3001;
    public static int TOKENTFAKE_CODE = 3000;
    public static int TOKENTIMEOUT_CODE = 3002;
    public static int TOKENINVALID_CODE = 10000;

    public int code = 0;
    public String msg;
    public T data;



    public boolean isSuccess() {
        if (code == SUCCESS_CODE) {
            return true;
        }
        return false;
    }

    public boolean isTokenTimeOut() {
        if (code == TOKENTIMEOUT_CODE || code == TOKENMISSION_CODE || code == TOKENTFAKE_CODE || code == TOKENINVALID_CODE ) {
            return true;
        }
        return false;
    }

    public void logToMessage(){
        Logger.d(msg);
    }

    public void logToCode(){
        Logger.d(code);
    }

    public void logToData(){
        Logger.d(data);
    }

    @Override
    public String toString() {
        return "code: "+code+"\nmsg:"+msg+"\ndata:"+data.toString();
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

