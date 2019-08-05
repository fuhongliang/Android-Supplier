package cn.ifhu.supplier.model.bean;

import java.util.List;

/**
 * @author fuhongliang
 */
public class DiscountPackagePostBean {
    String store_id;
    String bundling_id;
    String bundling_name;
    String discount_price;
    int bl_state;
    List<DiscountPackageGoods>  goods_list;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public int getBl_state() {
        return bl_state;
    }

    public void setBl_state(int bl_state) {
        this.bl_state = bl_state;
    }

    public String getBundling_id() {
        return bundling_id;
    }

    public void setBundling_id(String bundling_id) {
        this.bundling_id = bundling_id;
    }

    public String getBundling_name() {
        return bundling_name;
    }

    public void setBundling_name(String bundling_name) {
        this.bundling_name = bundling_name;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public List<DiscountPackageGoods> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<DiscountPackageGoods> goods_list) {
        this.goods_list = goods_list;
    }
}
