package cn.ifhu.supplier.model.newbean.post;

/**
 * @author fuhongliang
 */
public class RefundHandlePostBean extends BasePostBean {

    private int order_refund_id;
    private int option;
    private String refuse_desc;

    public int getOrder_refund_id() {
        return order_refund_id;
    }

    public void setOrder_refund_id(int order_refund_id) {
        this.order_refund_id = order_refund_id;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getRefuse_desc() {
        return refuse_desc;
    }

    public void setRefuse_desc(String refuse_desc) {
        this.refuse_desc = refuse_desc;
    }
}
