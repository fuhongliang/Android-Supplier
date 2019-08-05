package cn.ifhu.supplier.model.bean;

import java.util.List;

/**
 * @author fuhongliang
 */
public class DiscountPostBean {
    String store_id;
    String xianshi_id;
    String xianshi_name;
    String xianshi_title;
    String xianshi_explain;
    String start_time;
    String end_time;
    String lower_limit;
    List<DiscountGoods>  goods_list;

    public List<DiscountGoods> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<DiscountGoods> goods_list) {
        this.goods_list = goods_list;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getXianshi_id() {
        return xianshi_id;
    }

    public void setXianshi_id(String xianshi_id) {
        this.xianshi_id = xianshi_id;
    }

    public String getXianshi_name() {
        return xianshi_name;
    }

    public void setXianshi_name(String xianshi_name) {
        this.xianshi_name = xianshi_name;
    }

    public String getXianshi_title() {
        return xianshi_title;
    }

    public void setXianshi_title(String xianshi_title) {
        this.xianshi_title = xianshi_title;
    }

    public String getXianshi_explain() {
        return xianshi_explain;
    }

    public void setXianshi_explain(String xianshi_explain) {
        this.xianshi_explain = xianshi_explain;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(String lower_limit) {
        this.lower_limit = lower_limit;
    }


}
