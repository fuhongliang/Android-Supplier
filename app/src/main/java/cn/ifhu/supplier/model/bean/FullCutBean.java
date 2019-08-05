package cn.ifhu.supplier.model.bean;

import java.util.List;

public class FullCutBean {

    /**
     * mansong_name : 9999999999
     * mansong_id : 1
     * start_time : 0
     * end_time : 0
     * state : 2
     * rule : [{"price":100058,"discount":333}]
     */

    private String mansong_name;
    private int mansong_id;
    private long start_time;
    private long end_time;
    private int state;
    private List<RuleBean> rule;

    public String getMansong_name() {
        return mansong_name;
    }

    public void setMansong_name(String mansong_name) {
        this.mansong_name = mansong_name;
    }

    public int getMansong_id() {
        return mansong_id;
    }

    public void setMansong_id(int mansong_id) {
        this.mansong_id = mansong_id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<RuleBean> getRule() {
        return rule;
    }

    public void setRule(List<RuleBean> rule) {
        this.rule = rule;
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
