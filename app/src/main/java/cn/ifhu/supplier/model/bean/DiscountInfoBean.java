package cn.ifhu.supplier.model.bean;

import java.util.List;

/**
 * @author fuhongliang
 */
public class DiscountInfoBean {

    /**
     * xianshi_id : 7
     * xianshi_name : 2015年款 清仓促销
     * xianshi_title : 月末折扣
     * xianshi_explain : 挥泪大甩卖
     * start_time : 1389167520
     * end_time : 1577721600
     * lower_limit : 2
     * goods_list : [{"goods_id":49,"goods_name":"春装 披肩式 超短款 针织 衫开衫 女装 青鸟 黑色","goods_image":"1_04418240955916042.jpg","xianshi_price":"100.00"},{"goods_id":52,"goods_name":"新款 女款 拼接 不规则摆 长袖针织衫开衫 杏雨 白色","goods_image":"1_04418253240878850.jpg","xianshi_price":"58.00"},{"goods_id":38,"goods_name":"正品 2015春装新款 女 绣花针织衫 开衫外套浮桑初 蓝色","goods_image":"1_04418207207476705.jpg","xianshi_price":"158.00"}]
     */

    private int xianshi_id;
    private String xianshi_name;
    private String xianshi_title;
    private String xianshi_explain;
    private long start_time;
    private long end_time;
    private int lower_limit;
    private String img_path;

    private List<GoodsListBean> goods_list;

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public int getXianshi_id() {
        return xianshi_id;
    }

    public void setXianshi_id(int xianshi_id) {
        this.xianshi_id = xianshi_id;
    }

    public String getXianshi_name() {
        return xianshi_name;
    }

    public void setXianshi_name(String xianshi_name) {
        this.xianshi_name = xianshi_name;
    }

    public String getXianshi_title() {
        return xianshi_title;
    }

    public void setXianshi_title(String xianshi_title) {
        this.xianshi_title = xianshi_title;
    }

    public String getXianshi_explain() {
        return xianshi_explain;
    }

    public void setXianshi_explain(String xianshi_explain) {
        this.xianshi_explain = xianshi_explain;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(int lower_limit) {
        this.lower_limit = lower_limit;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {


        private int goods_id;
        private String goods_name;
        private String img_name;
        private String goods_price;
        private String xianshi_price;
        private String img_path;

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

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

        public String getXianshi_price() {
            return xianshi_price;
        }

        public void setXianshi_price(String xianshi_price) {
            this.xianshi_price = xianshi_price;
        }
    }
}
