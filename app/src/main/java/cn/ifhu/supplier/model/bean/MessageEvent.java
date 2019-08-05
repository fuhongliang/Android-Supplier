package cn.ifhu.supplier.model.bean;

public class MessageEvent {
    private String message;
    private String data;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, String data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
