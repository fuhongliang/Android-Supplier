package cn.ifhu.supplier.model.newbean.data;

public class NewOperationBean {

    /**
     * store : {"order_num_day":0,"order_num_thirty":4,"total_income_day":"0.00","total_income_thirty":"-4.67","total_goods_count":"11890","total_order_count":4}
     */

    private StoreBean store;

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public static class StoreBean {
        /**
         * order_num_day : 0
         * order_num_thirty : 4
         * total_income_day : 0.00
         * total_income_thirty : -4.67
         * total_goods_count : 11890
         * total_order_count : 4
         */

        private int order_num_day;
        private int order_num_thirty;
        private String total_income_day;
        private String total_income_thirty;
        private String total_goods_count;
        private int total_order_count;

        public int getOrder_num_day() {
            return order_num_day;
        }

        public void setOrder_num_day(int order_num_day) {
            this.order_num_day = order_num_day;
        }

        public int getOrder_num_thirty() {
            return order_num_thirty;
        }

        public void setOrder_num_thirty(int order_num_thirty) {
            this.order_num_thirty = order_num_thirty;
        }

        public String getTotal_income_day() {
            return total_income_day;
        }

        public void setTotal_income_day(String total_income_day) {
            this.total_income_day = total_income_day;
        }

        public String getTotal_income_thirty() {
            return total_income_thirty;
        }

        public void setTotal_income_thirty(String total_income_thirty) {
            this.total_income_thirty = total_income_thirty;
        }

        public String getTotal_goods_count() {
            return total_goods_count;
        }

        public void setTotal_goods_count(String total_goods_count) {
            this.total_goods_count = total_goods_count;
        }

        public int getTotal_order_count() {
            return total_order_count;
        }

        public void setTotal_order_count(int total_order_count) {
            this.total_order_count = total_order_count;
        }
    }
}
