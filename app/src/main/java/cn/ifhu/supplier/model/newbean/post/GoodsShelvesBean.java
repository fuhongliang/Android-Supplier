package cn.ifhu.supplier.model.newbean.post;

public class GoodsShelvesBean extends BasePostBean {

    private int status;//商品上架下架状态
    private int goods_id;//商品ID
    private String cat_id;//商品分类ID

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
}
