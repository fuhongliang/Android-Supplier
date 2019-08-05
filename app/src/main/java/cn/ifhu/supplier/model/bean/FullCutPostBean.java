package cn.ifhu.supplier.model.bean;

import java.util.List;

/**
 * @author fuhongliang
 */
public class FullCutPostBean {


    private String store_id;
    private String mansong_id;
    private String mansong_name;
    private String start_time;
    private String end_time;

    private String remark;
    private List<RuleBean> rules;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getMansong_id() {
        return mansong_id;
    }

    public void setMansong_id(String mansong_id) {
        this.mansong_id = mansong_id;
    }

    public String getMansong_name() {
        return mansong_name;
    }

    public void setMansong_name(String mansong_name) {
        this.mansong_name = mansong_name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<RuleBean> getRules() {
        return rules;
    }

    public void setRules(List<RuleBean> rules) {
        this.rules = rules;
    }

    public static class RuleBean {
        /**
         * price : 100058
         * discount : 333
         */

        private int price;
        private int discount;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }
    }
}
