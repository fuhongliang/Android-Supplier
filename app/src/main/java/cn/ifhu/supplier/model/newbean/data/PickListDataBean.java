package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class PickListDataBean {

    /**
     * pick_list : {"huodan_id":4,"total_pay_price":"26689.00","goods_num":11,"create_time":1565768328,"pick_status":0,"goods_info":{"num":1,"total_price":"3520.00","is_pick":0,"attr":[{"attr_id":73,"attr_group_name":"颜色","attr_name":"K2指纹锁【左右开向】","no":""}],"name":"安全防盗门 智能门家庭进户门 入户门指纹密码锁门 莱茵河系列","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/aa/aa86fc4feb44657a80f92c6dc5b705c56cdbebd9.png"}}
     */

    private List<PickListBean> pick_list;

    public List<PickListBean> getPick_list() {
        return pick_list;
    }

    public void setPick_list(List<PickListBean> pick_list) {
        this.pick_list = pick_list;
    }

    public static class PickListBean {
        /**
         * huodan_id : 4
         * total_pay_price : 26689.00
         * goods_num : 11
         * create_time : 1565768328
         * pick_status : 0
         * goods_info : {"num":1,"total_price":"3520.00","is_pick":0,"attr":[{"attr_id":73,"attr_group_name":"颜色","attr_name":"K2指纹锁【左右开向】","no":""}],"name":"安全防盗门 智能门家庭进户门 入户门指纹密码锁门 莱茵河系列","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/aa/aa86fc4feb44657a80f92c6dc5b705c56cdbebd9.png"}
         */

        private int huodan_id;
        private String total_pay_price;
        private int goods_num;
        private int goods_count;
        private String pick_no;

        public String getPick_no() {
            return pick_no;
        }

        public void setPick_no(String pick_no) {
            this.pick_no = pick_no;
        }

        public int getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(int goods_count) {
            this.goods_count = goods_count;
        }

        private int create_time;
        private int pick_status;
        private GoodsInfoBean goods_info;

        public int getHuodan_id() {
            return huodan_id;
        }

        public void setHuodan_id(int huodan_id) {
            this.huodan_id = huodan_id;
        }

        public String getTotal_pay_price() {
            return total_pay_price;
        }

        public void setTotal_pay_price(String total_pay_price) {
            this.total_pay_price = total_pay_price;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getPick_status() {
            return pick_status;
        }

        public void setPick_status(int pick_status) {
            this.pick_status = pick_status;
        }

        public GoodsInfoBean getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(GoodsInfoBean goods_info) {
            this.goods_info = goods_info;
        }

        public static class GoodsInfoBean {
            /**
             * num : 1
             * total_price : 3520.00
             * is_pick : 0
             * attr : [{"attr_id":73,"attr_group_name":"颜色","attr_name":"K2指纹锁【左右开向】","no":""}]
             * name : 安全防盗门 智能门家庭进户门 入户门指纹密码锁门 莱茵河系列
             * pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/aa/aa86fc4feb44657a80f92c6dc5b705c56cdbebd9.png
             */

            private int num;
            private String total_price;
            private int is_pick;
            private String name;
            private String pic;
            private List<AttrBean> attr;

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

            public int getIs_pick() {
                return is_pick;
            }

            public void setIs_pick(int is_pick) {
                this.is_pick = is_pick;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
                 * attr_id : 73
                 * attr_group_name : 颜色
                 * attr_name : K2指纹锁【左右开向】
                 * no :
                 */

                private int attr_id;
                private String attr_group_name;
                private String attr_name;
                private String no;

                public int getAttr_id() {
                    return attr_id;
                }

                public void setAttr_id(int attr_id) {
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
