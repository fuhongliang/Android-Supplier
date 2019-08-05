package cn.ifhu.supplier.model.bean;

import java.util.List;

public class DiscountPackageInfoBean {

    /**
     * bl_id : 14
     * bl_name : 发的广泛地
     * bl_price : 2343.00
     * bl_state : 1
     * goods_list : [{"goods_id":100058,"goods_name":"中国电信（China Telecom） 电信流量卡全国不限量4G手机卡无限流量卡大王电话卡永久上网卡","goods_image":"4_06076248561458549.jpg","goods_price":"333.00"},{"goods_id":100066,"goods_name":"","goods_image":"","goods_price":"200.00"}]
     */

    private int bl_id;
    private String bl_name;
    private String bl_price;
    private int bl_state;
    private String img_path;
    private List<GoodsListBean> goods_list;

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public int getBl_id() {
        return bl_id;
    }

    public void setBl_id(int bl_id) {
        this.bl_id = bl_id;
    }

    public String getBl_name() {
        return bl_name;
    }

    public void setBl_name(String bl_name) {
        this.bl_name = bl_name;
    }

    public String getBl_price() {
        return bl_price;
    }

    public void setBl_price(String bl_price) {
        this.bl_price = bl_price;
    }

    public int getBl_state() {
        return bl_state;
    }

    public void setBl_state(int bl_state) {
        this.bl_state = bl_state;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * goods_id : 100058
         * goods_name : 中国电信（China Telecom） 电信流量卡全国不限量4G手机卡无限流量卡大王电话卡永久上网卡
         * goods_image : 4_06076248561458549.jpg
         * goods_price : 333.00
         */

        private int goods_id;
        private String goods_name;
        private String img_name;
        private String goods_price;
        private String goods_origin_price;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_origin_price() {
            return goods_origin_price;
        }

        public void setGoods_origin_price(String goods_origin_price) {
            this.goods_origin_price = goods_origin_price;
        }
    }
}
