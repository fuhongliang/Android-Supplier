package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

/**
 * 商品详情bean类
 */
public class ProductDetailBean {


    /**
     * goods : {"goods_id":47,"goods_name":"床头柜","price":"20.00","original_price":"50.00","detail":"粉红床头柜","goods_cat_id":36,"service":"新品特惠","virtual_sales":555,"cover_pic":"http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/30/ece2127537e68d3af4e54eae7e80974a..jpg","video_url":"","unit":"件","weight":555,"goods_num":99999,"full_cut":{"pieces":1,"forehead":1},"pt_cat_id":25,"goods_pic":["http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/uo/15a5a76d47be22fcbbc5bc7101d3e6db..jpg"]}
     */

    private GoodsBean goods;

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goods_id : 47
         * goods_name : 床头柜
         * price : 20.00
         * original_price : 50.00
         * detail : 粉红床头柜
         * goods_cat_id : 36
         * service : 新品特惠
         * virtual_sales : 555
         * cover_pic : http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/30/ece2127537e68d3af4e54eae7e80974a..jpg
         * video_url :
         * unit : 件
         * weight : 555
         * goods_num : 99999
         * full_cut : {"pieces":1,"forehead":1}
         * pt_cat_id : 25
         * goods_pic : ["http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/uo/15a5a76d47be22fcbbc5bc7101d3e6db..jpg"]
         */

        private int goods_id;
        private String goods_name;
        private String price;
        private String original_price;
        private String detail;
        private int goods_cat_id;
        private String service;
        private int virtual_sales;
        private String cover_pic;
        private String video_url;
        private String unit;
        private int weight;
        private int goods_num;
        private FullCutBean full_cut;
        private int pt_cat_id;
        private List<String> goods_pic;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getGoods_cat_id() {
            return goods_cat_id;
        }

        public void setGoods_cat_id(int goods_cat_id) {
            this.goods_cat_id = goods_cat_id;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public int getVirtual_sales() {
            return virtual_sales;
        }

        public void setVirtual_sales(int virtual_sales) {
            this.virtual_sales = virtual_sales;
        }

        public String getCover_pic() {
            return cover_pic;
        }

        public void setCover_pic(String cover_pic) {
            this.cover_pic = cover_pic;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public FullCutBean getFull_cut() {
            return full_cut;
        }

        public void setFull_cut(FullCutBean full_cut) {
            this.full_cut = full_cut;
        }

        public int getPt_cat_id() {
            return pt_cat_id;
        }

        public void setPt_cat_id(int pt_cat_id) {
            this.pt_cat_id = pt_cat_id;
        }

        public List<String> getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(List<String> goods_pic) {
            this.goods_pic = goods_pic;
        }

        public static class FullCutBean {
            /**
             * pieces : 1
             * forehead : 1
             */

            private String pieces;
            private String forehead;

            public String getPieces() {
                return pieces;
            }

            public void setPieces(String pieces) {
                this.pieces = pieces;
            }

            public String getForehead() {
                return forehead;
            }

            public void setForehead(String forehead) {
                this.forehead = forehead;
            }
        }
    }
}
