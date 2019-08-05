package cn.ifhu.supplier.model.newbean.post;

public class AllEvaluationDeletePostBean extends BasePostBean {
    private String id;
    private int hide;
    private int delete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHide() {
        return hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}
