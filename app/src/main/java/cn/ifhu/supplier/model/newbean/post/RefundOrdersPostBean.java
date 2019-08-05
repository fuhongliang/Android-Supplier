package cn.ifhu.supplier.model.newbean.post;

/**
 * @author fuhongliang
 */
public class RefundOrdersPostBean extends BasePostBean {

    private int refund_status;
    private int page;
    private int limit;

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
