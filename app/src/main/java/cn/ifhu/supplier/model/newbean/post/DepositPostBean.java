package cn.ifhu.supplier.model.newbean.post;

/**
 * Created by KevinFu on 2019-07-08.
 * Copyright (c) 2019 KevinFu
 */
public class DepositPostBean extends BasePostBean{

    private String price;
    private String image_url;
    private String store_id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
