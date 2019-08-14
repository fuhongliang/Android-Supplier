package cn.ifhu.supplier.model.newbean.post;

public class ShareListPostBean extends BasePostBean {
    private int type;
    private int limit;
    private int page;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
