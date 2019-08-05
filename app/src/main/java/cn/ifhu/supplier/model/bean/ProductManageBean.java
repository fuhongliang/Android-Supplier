package cn.ifhu.supplier.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author fuhongliang
 */
public class ProductManageBean {


    private List<ClassListBean> class_list;
    private List<GoodsListBean> goods_list;

    public List<ClassListBean> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<ClassListBean> class_list) {
        this.class_list = class_list;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class ClassListBean {
        /**
         * stc_id : 1
         * stc_name : 美食
         */

        private int stc_id;
        private String stc_name;

        public int getStc_id() {
            return stc_id;
        }

        public void setStc_id(int stc_id) {
            this.stc_id = stc_id;
        }

        public String getStc_name() {
            return stc_name;
        }

        public void setStc_name(String stc_name) {
            this.stc_name = stc_name;
        }
    }

    public static class GoodsListBean implements Serializable {

        private int goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_marketprice;
        private String goods_dicountprice;
        private String goods_desc;
        private int goods_state;
        private int goods_storage;
        private String img_name;
        private String img_path;
        private int is_much;

        public int getIs_much() {
            return is_much;
        }

        public void setIs_much(int is_much) {
            this.is_much = is_much;
        }

        public String getGoods_dicountprice() {
            return goods_dicountprice;
        }

        public void setGoods_dicountprice(String goods_dicountprice) {
            this.goods_dicountprice = goods_dicountprice;
        }

        private List<GoodsSaleTimeBean> goods_sale_time;

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

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public int getGoods_state() {
            return goods_state;
        }

        public void setGoods_state(int goods_state) {
            this.goods_state = goods_state;
        }

        public int getGoods_storage() {
            return goods_storage;
        }

        public void setGoods_storage(int goods_storage) {
            this.goods_storage = goods_storage;
        }

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public List<GoodsSaleTimeBean> getGoods_sale_time() {
            return goods_sale_time;
        }

        public void setGoods_sale_time(List<GoodsSaleTimeBean> goods_sale_time) {
            this.goods_sale_time = goods_sale_time;
        }

        public static class GoodsSaleTimeBean implements Serializable{
            /**
             * start_time : 00:00
             * end_time : 23:59
             */

            private String start_time;
            private String end_time;

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }
        }
    }
}
