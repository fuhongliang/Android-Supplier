package cn.ifhu.supplier.model.newbean.post;

/**
 * @author fuhongliang
 */
public class ModifyPricePostBean extends BasePostBean {

    private int order_id;
    private String update_price;
    private String update_express;
    private int type;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getUpdate_price() {
        return update_price;
    }

    public void setUpdate_price(String update_price) {
        this.update_price = update_price;
    }

    public String getUpdate_express() {
        return update_express;
    }

    public void setUpdate_express(String update_express) {
        this.update_express = update_express;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
