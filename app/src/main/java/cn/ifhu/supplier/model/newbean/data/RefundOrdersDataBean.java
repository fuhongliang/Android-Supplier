package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

/**
 * Created by KevinFu on 2019-06-29.
 * Copyright (c) 2019 KevinFu
 */
public class RefundOrdersDataBean {

    private List<RefundOrderBean> refund_order;

    public List<RefundOrderBean> getRefund_order() {
        return refund_order;
    }

    public void setRefund_order(List<RefundOrderBean> refund_order) {
        this.refund_order = refund_order;
    }

    public static class RefundOrderBean {
        /**
         * order_refund_id : 7
         * name : 张三
         * mobile : 020-81167888
         * address : 广东省广州市海珠区新港中路397号
         * order_refund_no : 20190713154735998786
         * addtime : 1563004055
         * type : 1
         * status : 3
         * refuse_desc : 卖家已拒绝退货退款申请
         * is_agree : 2
         * desc : 对方更多的是而同仁堂
         * pic_list : []
         * refund_price : 1096.00
         * is_user_send : 0
         * user_send_express :
         * user_send_express_no :
         * goods_name : 简约室内门实木复合门 房间门套装门 定制卧室门
         * goods_pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/5b/5bec7bb154baa3d414248604410927c04164932f.png
         * attr : [{"attr_id":"68","attr_group_name":"颜色","attr_name":"珍珠白","no":""}]
         * num : 1
         * total_price : 1096.00
         */

        private int order_refund_id;
        private String name;
        private String mobile;
        private String address;
        private String order_refund_no;
        private int addtime;
        private int type;
        private int status;
        private String refuse_desc;
        private int is_agree;
        private String desc;
        private String refund_price;
        private int is_user_send;
        private String user_send_express;
        private String user_send_express_no;
        private String goods_name;
        private String goods_pic;
        private int num;
        private String total_price;
        private List<?> pic_list;
        private List<AttrBean> attr;

        public int getOrder_refund_id() {
            return order_refund_id;
        }

        public void setOrder_refund_id(int order_refund_id) {
            this.order_refund_id = order_refund_id;
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

        public String getOrder_refund_no() {
            return order_refund_no;
        }

        public void setOrder_refund_no(String order_refund_no) {
            this.order_refund_no = order_refund_no;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefuse_desc() {
            return refuse_desc;
        }

        public void setRefuse_desc(String refuse_desc) {
            this.refuse_desc = refuse_desc;
        }

        public int getIs_agree() {
            return is_agree;
        }

        public void setIs_agree(int is_agree) {
            this.is_agree = is_agree;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getRefund_price() {
            return refund_price;
        }

        public void setRefund_price(String refund_price) {
            this.refund_price = refund_price;
        }

        public int getIs_user_send() {
            return is_user_send;
        }

        public void setIs_user_send(int is_user_send) {
            this.is_user_send = is_user_send;
        }

        public String getUser_send_express() {
            return user_send_express;
        }

        public void setUser_send_express(String user_send_express) {
            this.user_send_express = user_send_express;
        }

        public String getUser_send_express_no() {
            return user_send_express_no;
        }

        public void setUser_send_express_no(String user_send_express_no) {
            this.user_send_express_no = user_send_express_no;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public List<?> getPic_list() {
            return pic_list;
        }

        public void setPic_list(List<?> pic_list) {
            this.pic_list = pic_list;
        }

        public List<AttrBean> getAttr() {
            return attr;
        }

        public void setAttr(List<AttrBean> attr) {
            this.attr = attr;
        }

        public static class AttrBean {
            /**
             * attr_id : 68
             * attr_group_name : 颜色
             * attr_name : 珍珠白
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
