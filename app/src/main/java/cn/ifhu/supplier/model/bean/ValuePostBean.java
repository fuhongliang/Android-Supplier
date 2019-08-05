package cn.ifhu.supplier.model.bean;

public class ValuePostBean {
    String voucher_id;
    int store_id;
    String title;
    String mianzhi;
    String limit_price;

    String describe;
    String end_time;

    int total_nums;
    int each_limit;

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMianzhi() {
        return mianzhi;
    }

    public void setMianzhi(String mianzhi) {
        this.mianzhi = mianzhi;
    }

    public String getLimit_price() {
        return limit_price;
    }

    public void setLimit_price(String limit_price) {
        this.limit_price = limit_price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getTotal_nums() {
        return total_nums;
    }

    public void setTotal_nums(int total_nums) {
        this.total_nums = total_nums;
    }

    public int getEach_limit() {
        return each_limit;
    }

    public void setEach_limit(int each_limit) {
        this.each_limit = each_limit;
    }
}
