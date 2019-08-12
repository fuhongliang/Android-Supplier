package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class PickingDetailsDataBean {

    private List<GoodsListBean> goods_list;

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * order_id : 248
         * is_pick : 0
         * order_detail_id : 220
         * num : 3
         * pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png
         * attr : [{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}]
         * total_price : 5697.6
         * name : 黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室
         * goods_id : 102
         */

        private int order_id;
        private int is_pick;
        private int order_detail_id;
        private int num;
        private String pic;
        private double total_price;
        private String name;
        private int goods_id;
        private List<AttrBean> attr;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getIs_pick() {
            return is_pick;
        }

        public void setIs_pick(int is_pick) {
            this.is_pick = is_pick;
        }

        public int getOrder_detail_id() {
            return order_detail_id;
        }

        public void setOrder_detail_id(int order_detail_id) {
            this.order_detail_id = order_detail_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
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
