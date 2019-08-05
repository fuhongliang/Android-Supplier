package cn.ifhu.supplier.model.bean;

import com.google.gson.annotations.SerializedName;

public class OperationBean {

    /**
     * today_ordernum : 0
     * today_orderamount : 0.00
     * 30_ordernum : 0
     * 30_orderamount : 0.00
     * store_collect : 0
     * goods_num : 0
     * jingying_url : https://api.shennongmall.com/store_jingying/6
     */

    private int today_ordernum;
    private String today_orderamount;
    @SerializedName("30_ordernum")
    private int _$30_ordernum;
    @SerializedName("30_orderamount")
    private String _$30_orderamount;
    private int store_collect;
    private int goods_num;
    private String jingying_url;

    public int getToday_ordernum() {
        return today_ordernum;
    }

    public void setToday_ordernum(int today_ordernum) {
        this.today_ordernum = today_ordernum;
    }

    public String getToday_orderamount() {
        return today_orderamount;
    }

    public void setToday_orderamount(String today_orderamount) {
        this.today_orderamount = today_orderamount;
    }

    public int get_$30_ordernum() {
        return _$30_ordernum;
    }

    public void set_$30_ordernum(int _$30_ordernum) {
        this._$30_ordernum = _$30_ordernum;
    }

    public String get_$30_orderamount() {
        return _$30_orderamount;
    }

    public void set_$30_orderamount(String _$30_orderamount) {
        this._$30_orderamount = _$30_orderamount;
    }

    public int getStore_collect() {
        return store_collect;
    }

    public void setStore_collect(int store_collect) {
        this.store_collect = store_collect;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getJingying_url() {
        return jingying_url;
    }

    public void setJingying_url(String jingying_url) {
        this.jingying_url = jingying_url;
    }
}
