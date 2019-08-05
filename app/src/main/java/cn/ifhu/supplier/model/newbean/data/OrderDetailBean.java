package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class OrderDetailBean {


    /**
     * order : {"order_id":1,"name":"符宗棠","mobile":"18319699581","address":"广东省广州市越秀区西关西门口","remark":"","seller_comments":null,"express":"","express_no":"","order_no":"20190618143636187968","addtime":1560839796,"express_price":"0.00","total_price":"10.00","pay_price":"0.11","before_update_price":null,"coupon_sub_price":"0.00","is_pay":1,"pay_type":1,"pay_time":1560839803,"is_send":0,"send_time":0,"is_confirm":0,"confirm_time":0,"goodsList":[{"name":"ARROW箭牌卫浴节水静音坐便器喷射虹吸马桶","total_price":"0.11","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/e0/e0b11eeac966f2cc2cf3fda8d1fee9cf9dc594f1.png","attr":[{"attr_id":"1","attr_group_name":"规格","attr_name":"默认","no":""}]}]}
     */

    private OrderBean order;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * order_id : 1
         * name : 符宗棠
         * mobile : 18319699581
         * address : 广东省广州市越秀区西关西门口
         * remark :
         * seller_comments : null
         * express :
         * express_no :
         * order_no : 20190618143636187968
         * addtime : 1560839796
         * express_price : 0.00
         * total_price : 10.00
         * pay_price : 0.11
         * before_update_price : null
         * coupon_sub_price : 0.00
         * is_pay : 1
         * pay_type : 1
         * pay_time : 1560839803
         * is_send : 0
         * send_time : 0
         * is_confirm : 0
         * confirm_time : 0
         * goodsList : [{"name":"ARROW箭牌卫浴节水静音坐便器喷射虹吸马桶","total_price":"0.11","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/e0/e0b11eeac966f2cc2cf3fda8d1fee9cf9dc594f1.png","attr":[{"attr_id":"1","attr_group_name":"规格","attr_name":"默认","no":""}]}]
         */

        private int order_id;
        private String name;
        private String mobile;
        private String address;
        private String remark;
        private Object seller_comments;
        private String express;
        private String express_no;
        private String order_no;
        private int addtime;
        private String express_price;
        private String total_price;
        private String pay_price;
        private Object before_update_price;
        private String coupon_sub_price;
        private int is_pay;
        private int pay_type;
        private int pay_time;
        private int is_send;
        private int send_time;
        private int is_confirm;
        private int confirm_time;
        private List<GoodsListBean> goodsList;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getSeller_comments() {
            return seller_comments;
        }

        public void setSeller_comments(Object seller_comments) {
            this.seller_comments = seller_comments;
        }

        public String getExpress() {
            return express;
        }

        public void setExpress(String express) {
            this.express = express;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getExpress_price() {
            return express_price;
        }

        public void setExpress_price(String express_price) {
            this.express_price = express_price;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public Object getBefore_update_price() {
            return before_update_price;
        }

        public void setBefore_update_price(Object before_update_price) {
            this.before_update_price = before_update_price;
        }

        public String getCoupon_sub_price() {
            return coupon_sub_price;
        }

        public void setCoupon_sub_price(String coupon_sub_price) {
            this.coupon_sub_price = coupon_sub_price;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public int getIs_send() {
            return is_send;
        }

        public void setIs_send(int is_send) {
            this.is_send = is_send;
        }

        public int getSend_time() {
            return send_time;
        }

        public void setSend_time(int send_time) {
            this.send_time = send_time;
        }

        public int getIs_confirm() {
            return is_confirm;
        }

        public void setIs_confirm(int is_confirm) {
            this.is_confirm = is_confirm;
        }

        public int getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(int confirm_time) {
            this.confirm_time = confirm_time;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * name : ARROW箭牌卫浴节水静音坐便器喷射虹吸马桶
             * total_price : 0.11
             * pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/e0/e0b11eeac966f2cc2cf3fda8d1fee9cf9dc594f1.png
             * attr : [{"attr_id":"1","attr_group_name":"规格","attr_name":"默认","no":""}]
             */

            private String name;
            private String total_price;
            private String pic;
            private List<AttrBean> attr;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTotal_price() {
                return total_price;
            }

            public void setTotal_price(String total_price) {
                this.total_price = total_price;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public List<AttrBean> getAttr() {
                return attr;
            }

            public void setAttr(List<AttrBean> attr) {
                this.attr = attr;
            }

            public static class AttrBean {
                /**
                 * attr_id : 1
                 * attr_group_name : 规格
                 * attr_name : 默认
                 * no :
                 */

                private String attr_id;
                private String attr_group_name;
                private String attr_name;
                private String no;

                public String getAttr_id() {
                    return attr_id;
                }

                public void setAttr_id(String attr_id) {
                    this.attr_id = attr_id;
                }

                public String getAttr_group_name() {
                    return attr_group_name;
                }

                public void setAttr_group_name(String attr_group_name) {
                    this.attr_group_name = attr_group_name;
                }

                public String getAttr_name() {
                    return attr_name;
                }

                public void setAttr_name(String attr_name) {
                    this.attr_name = attr_name;
                }

                public String getNo() {
                    return no;
                }

                public void setNo(String no) {
                    this.no = no;
                }
            }
        }
    }
}
