package cn.ifhu.supplier.model.newbean.post;

public class PaginationPostBean extends BasePostBean {
    private int limit;
    private int page;

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
