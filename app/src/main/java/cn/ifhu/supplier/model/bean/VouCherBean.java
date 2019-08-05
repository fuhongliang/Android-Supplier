package cn.ifhu.supplier.model.bean;

/**
 * @author fuhongliang
 */
public class VouCherBean {

    /**
     * voucher_id : 1
     * voucher_title : 代金券
     * voucher_eachlimit : 1
     * voucher_start_date : 2019-04-07 03:18:32
     * voucher_end_date : 2019-04-17 16:00:00
     * voucher_surplus : 100
     * voucher_used : 0
     * voucher_giveout : 0
     * voucher_limit : 2000.00
     * voucher_state : 1
     */

    private int voucher_id;
    private String voucher_title;
    private int voucher_eachlimit;
    private String voucher_start_date;
    private String voucher_end_date;
    private int voucher_surplus;
    private int voucher_used;
    private int voucher_giveout;
    private String voucher_limit;
    private int voucher_state;

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

    public int getVoucher_eachlimit() {
        return voucher_eachlimit;
    }

    public void setVoucher_eachlimit(int voucher_eachlimit) {
        this.voucher_eachlimit = voucher_eachlimit;
    }

    public String getVoucher_start_date() {
        return voucher_start_date;
    }

    public void setVoucher_start_date(String voucher_start_date) {
        this.voucher_start_date = voucher_start_date;
    }

    public String getVoucher_end_date() {
        return voucher_end_date;
    }

    public void setVoucher_end_date(String voucher_end_date) {
        this.voucher_end_date = voucher_end_date;
    }

    public int getVoucher_surplus() {
        return voucher_surplus;
    }

    public void setVoucher_surplus(int voucher_surplus) {
        this.voucher_surplus = voucher_surplus;
    }

    public int getVoucher_used() {
        return voucher_used;
    }

    public void setVoucher_used(int voucher_used) {
        this.voucher_used = voucher_used;
    }

    public int getVoucher_giveout() {
        return voucher_giveout;
    }

    public void setVoucher_giveout(int voucher_giveout) {
        this.voucher_giveout = voucher_giveout;
    }

    public String getVoucher_limit() {
        return voucher_limit;
    }

    public void setVoucher_limit(String voucher_limit) {
        this.voucher_limit = voucher_limit;
    }

    public int getVoucher_state() {
        return voucher_state;
    }

    public void setVoucher_state(int voucher_state) {
        this.voucher_state = voucher_state;
    }
}
