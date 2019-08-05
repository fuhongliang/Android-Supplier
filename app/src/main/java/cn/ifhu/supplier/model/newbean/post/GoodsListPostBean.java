package cn.ifhu.supplier.model.newbean.post;

/**
 * 获取制定商品类别的商品列表bean
 */
public class GoodsListPostBean extends BasePostBean {

    private int cat_id;

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
