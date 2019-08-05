package cn.ifhu.supplier.model.bean;

/**
 * @author fuhongliang
 */
public class VouCherInfoBean {

    /**
     * voucher_id : 4
     * voucher_title : 11111
     * voucher_price : 1
     * voucher_limit : 1.00
     * voucher_end_date : 1970-01-01 06:27:24
     * voucher_total : 5555
     * voucher_eachlimit : 5
     * voucher_desc : 11111
     */

    private int voucher_id;
    private String voucher_title;
    private int voucher_price;
    private String voucher_limit;
    private String voucher_end_date;
    private int voucher_total;
    private int voucher_eachlimit;
    private String voucher_desc;

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getVoucher_title() {
        return voucher_title;
    }

    public void setVoucher_title(String voucher_title) {
        this.voucher_title = voucher_title;
    }

    public int getVoucher_price() {
        return voucher_price;
    }

    public void setVoucher_price(int voucher_price) {
        this.voucher_price = voucher_price;
    }

    public String getVoucher_limit() {
        return voucher_limit;
    }

    public void setVoucher_limit(String voucher_limit) {
        this.voucher_limit = voucher_limit;
    }

    public String getVoucher_end_date() {
        return voucher_end_date;
    }

    public void setVoucher_end_date(String voucher_end_date) {
        this.voucher_end_date = voucher_end_date;
    }

    public int getVoucher_total() {
        return voucher_total;
    }

    public void setVoucher_total(int voucher_total) {
        this.voucher_total = voucher_total;
    }

    public int getVoucher_eachlimit() {
        return voucher_eachlimit;
    }

    public void setVoucher_eachlimit(int voucher_eachlimit) {
        this.voucher_eachlimit = voucher_eachlimit;
    }

    public String getVoucher_desc() {
        return voucher_desc;
    }

    public void setVoucher_desc(String voucher_desc) {
        this.voucher_desc = voucher_desc;
    }
}
