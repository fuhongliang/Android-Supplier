package cn.ifhu.supplier.model.newbean.post;

/**
 * @author fuhongliang
 */
public class OrdersPostBean extends BasePostBean {

    private int status;
    private int page;
    private int per_page;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }
}
