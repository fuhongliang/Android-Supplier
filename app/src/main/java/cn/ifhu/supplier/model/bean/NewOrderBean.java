package cn.ifhu.supplier.model.bean;

import java.util.List;

public class NewOrderBean {


    private List<ListBean> list;
    private List<MsgBean> msg;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class ListBean {
        /**
         * order_id : 64
         * order_sn : 2000000000008401
         * buyer_id : 17
         * add_time : 2019-04-30 17:24:59
         * extend_order_common : {"phone":"13152414784","address":"河北\t唐山市\t乐亭县 214214","reciver_name":"33"}
         * extend_order_goods : [{"goods_name":"蠢","goods_price":"12.00","goods_num":1,"commis_rate":5,"goods_pay_price":"12.00"}]
         * delivery : {"name":"三爷","phone":"13124154747"}
         * order_state : 配送中
         * total_price : 12
         * commis_price : 0.6
         * goods_pay_price : 11.4
         */
        public boolean isExpendOrder = false;

        public boolean isExpendOrder() {
            return isExpendOrder;
        }

        public void setExpendOrder(boolean expendOrder) {
            isExpendOrder = expendOrder;
        }

        private int order_id;
        private long order_sn;
        private int buyer_id;
        private String add_time;
        private ExtendOrderCommonBean extend_order_common;
        private DeliveryBean delivery;
        private String order_state;
        private int total_price;
        private double commis_price;
        private double goods_pay_price;
        private List<ExtendOrderGoodsBean> extend_order_goods;


        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public long getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(long order_sn) {
            this.order_sn = order_sn;
        }

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public ExtendOrderCommonBean getExtend_order_common() {
            return extend_order_common;
        }

        public void setExtend_order_common(ExtendOrderCommonBean extend_order_common) {
            this.extend_order_common = extend_order_common;
        }

        public DeliveryBean getDelivery() {
            return delivery;
        }

        public void setDelivery(DeliveryBean delivery) {
            this.delivery = delivery;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public double getCommis_price() {
            return commis_price;
        }

        public void setCommis_price(double commis_price) {
            this.commis_price = commis_price;
        }

        public double getGoods_pay_price() {
            return goods_pay_price;
        }

        public void setGoods_pay_price(double goods_pay_price) {
            this.goods_pay_price = goods_pay_price;
        }

        public List<ExtendOrderGoodsBean> getExtend_order_goods() {
            return extend_order_goods;
        }

        public void setExtend_order_goods(List<ExtendOrderGoodsBean> extend_order_goods) {
            this.extend_order_goods = extend_order_goods;
        }

        public static class ExtendOrderCommonBean {
            /**
             * phone : 13152414784
             * address : 河北	唐山市	乐亭县 214214
             * reciver_name : 33
             */

            private String phone;
            private String address;
            private String reciver_name;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getReciver_name() {
                return reciver_name;
            }

            public void setReciver_name(String reciver_name) {
                this.reciver_name = reciver_name;
            }
        }

        public static class DeliveryBean {
            /**
             * name : 三爷
             * phone : 13124154747
             */

            private String name;
            private String phone;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class ExtendOrderGoodsBean {
            /**
             * goods_name : 蠢
             * goods_price : 12.00
             * goods_num : 1
             * commis_rate : 5
             * goods_pay_price : 12.00
             */

            private String goods_name;
            private String goods_price;
            private int goods_num;
            private int commis_rate;
            private String goods_pay_price;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public int getCommis_rate() {
                return commis_rate;
            }

            public void setCommis_rate(int commis_rate) {
                this.commis_rate = commis_rate;
            }

            public String getGoods_pay_price() {
                return goods_pay_price;
            }

            public void setGoods_pay_price(String goods_pay_price) {
                this.goods_pay_price = goods_pay_price;
            }
        }
    }

    public static class MsgBean {
        /**
         * sm_id : 1
         * sm_content : 您有订单需要处理，订单编号：2000000000002801。
         */

        private int sm_id;
        private String sm_content;

        public int getSm_id() {
            return sm_id;
        }

        public void setSm_id(int sm_id) {
            this.sm_id = sm_id;
        }

        public String getSm_content() {
            return sm_content;
        }

        public void setSm_content(String sm_content) {
            this.sm_content = sm_content;
        }
    }
}
