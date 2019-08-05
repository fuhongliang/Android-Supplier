package cn.ifhu.supplier.model.bean;

public class NoticeDetailBean {


    /**
     * sm_id : 15
     * sm_content : 您有订单需要处理，订单编号：2000000000004201。
     * sm_addtime : 2019-04-01 19:03:08
     * sm_title : 这是标题
     */

    private int sm_id;
    private String sm_content;
    private String sm_addtime;
    private String sm_title;

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }

    public String getSm_content() {
        return sm_content;
    }

    public void setSm_content(String sm_content) {
        this.sm_content = sm_content;
    }

    public String getSm_addtime() {
        return sm_addtime;
    }

    public void setSm_addtime(String sm_addtime) {
        this.sm_addtime = sm_addtime;
    }

    public String getSm_title() {
        return sm_title;
    }

    public void setSm_title(String sm_title) {
        this.sm_title = sm_title;
    }
}
