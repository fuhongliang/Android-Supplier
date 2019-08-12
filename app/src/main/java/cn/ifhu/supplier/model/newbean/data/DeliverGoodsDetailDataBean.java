package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class DeliverGoodsDetailDataBean {
    private DeliverBean deliver;

    public DeliverBean getDeliver() {
        return deliver;
    }

    public void setDeliver(DeliverBean deliver) {
        this.deliver = deliver;
    }

    public static class DeliverBean {
        private int deliver_id;
        private int huodan_id;
        private int is_send;
        private int addtime;
        private int total_price;
        private int num;
        private int goods_attr_count;
        private int share_id;
        private String name;
        private String mobile;
        private String district;

        public int getDeliver_id() {
            return deliver_id;
        }

        public void setDeliver_id(int deliver_id) {
            this.deliver_id = deliver_id;
        }

        public int getHuodan_mch_id() {
            return huodan_id;
        }

        public void setHuodan_mch_id(int huodan_mch_id) {
            this.huodan_id = huodan_id;
        }

        public int getIs_send() {
            return is_send;
        }

        public void setIs_send(int is_send) {
            this.is_send = is_send;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getGoods_attr_count() {
            return goods_attr_count;
        }

        public void setGoods_attr_count(int goods_attr_count) {
            this.goods_attr_count = goods_attr_count;
        }

        public int getShare_id() {
            return share_id;
        }

        public void setShare_id(int share_id) {
            this.share_id = share_id;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

    private List<GoodsListBean> goods_list;

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        private int goods_id;
        private int num;
        private int total_price;
        private String name;
        private String pic;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
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

        public AttrBean getAttr() {
            return attr;
        }

        public void setAttr(AttrBean attr) {
            this.attr = attr;
        }

        private AttrBean attr;

        public static class AttrBean {
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
