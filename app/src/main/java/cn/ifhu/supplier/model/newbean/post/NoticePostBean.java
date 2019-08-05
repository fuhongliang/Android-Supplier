package cn.ifhu.supplier.model.newbean.post;

/**
 * 请求的基类bean
 */
public class NoticePostBean extends BasePostBean{

  int limit;
  int page;

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
