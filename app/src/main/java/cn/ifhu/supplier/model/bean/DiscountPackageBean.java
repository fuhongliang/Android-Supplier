package cn.ifhu.supplier.model.bean;

public class DiscountPackageBean {

    /**
     * bl_id : 13
     * bl_name : 发的广泛地
     * bl_state : 1
     * price : 533.00
     */

    private int bl_id;
    private String bl_name;
    private int bl_state;
    private String price;

    public int getBl_id() {
        return bl_id;
    }

    public void setBl_id(int bl_id) {
        this.bl_id = bl_id;
    }

    public String getBl_name() {
        return bl_name;
    }

    public void setBl_name(String bl_name) {
        this.bl_name = bl_name;
    }

    public int getBl_state() {
        return bl_state;
    }

    public void setBl_state(int bl_state) {
        this.bl_state = bl_state;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
