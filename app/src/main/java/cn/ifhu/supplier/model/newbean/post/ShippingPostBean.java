package cn.ifhu.supplier.model.newbean.post;

public class ShippingPostBean extends BasePostBean {

    private String express;
    private String express_no;
    private String words;
    private int order_id;
    private int is_express;

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getIs_express() {
        return is_express;
    }

    public void setIs_express(int is_express) {
        this.is_express = is_express;
    }
}
