package cn.ifhu.supplier.model.newbean.post;

/**
 * 添加商品分类数据bean
 */
public class GoodsCatsAddBean extends BasePostBean {

    private String name;
    private String icon = "";
    private String sort = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
