package cn.ifhu.supplier.model.bean;

import java.util.List;

public class JSBean {

    /**
     * list : [{"amount":0,"state":"1","ob_no":2019024,"os_month":"02"},{"amount":0,"state":"1","ob_no":2019034,"os_month":"03"}]
     * total_amount : 0
     */

    private int y_jiesuan;
    private int d_jiesuan;

    private List<ListBean> list;



    public int getD_jiesuan() {
        return d_jiesuan;
    }

    public void setD_jiesuan(int d_jiesuan) {
        this.d_jiesuan = d_jiesuan;
    }

    public int getY_jiesuan() {
        return y_jiesuan;
    }

    public void setY_jiesuan(int y_jiesuan) {
        this.y_jiesuan = y_jiesuan;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * amount : 0
         * state : 1
         * ob_no : 2019024
         * os_month : 02
         */

        private double amount;
        private String state;
        private int ob_no;
        private String os_month;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getOb_no() {
            return ob_no;
        }

        public void setOb_no(int ob_no) {
            this.ob_no = ob_no;
        }

        public String getOs_month() {
            return os_month;
        }

        public void setOs_month(String os_month) {
            this.os_month = os_month;
        }
    }
}
