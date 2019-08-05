package cn.ifhu.supplier.model.newbean.post;

import java.util.List;

public class ProductAdditionBean extends BasePostBean {
    private String goods_id;
    private String name;
    private String detail;
    private String cover_pic;
    private List<String> goods_pic;
    private String video_url;
    private String pt_cat_id;
    private String goods_cat_id;
    private String unit;
    private String virtual_sales;
    private String weight;
    private String original_price;
    private String price;
    private String goods_num;
    private String pieces;
    private String forehead;
    private String service;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public List<String> getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(List<String> goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getPt_cat_id() {
        return pt_cat_id;
    }

    public void setPt_cat_id(String pt_cat_id) {
        this.pt_cat_id = pt_cat_id;
    }

    public String getGoods_cat_id() {
        return goods_cat_id;
    }

    public void setGoods_cat_id(String goods_cat_id) {
        this.goods_cat_id = goods_cat_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVirtual_sales() {
        return virtual_sales;
    }

    public void setVirtual_sales(String virtual_sales) {
        this.virtual_sales = virtual_sales;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
