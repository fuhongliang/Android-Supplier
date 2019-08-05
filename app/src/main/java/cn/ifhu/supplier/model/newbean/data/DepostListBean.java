package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

/**
 * Created by KevinFu on 2019-07-08.
 * Copyright (c) 2019 KevinFu
 */
public class DepostListBean {

    private List<PassBean> pass;
    private List<ReviewBean> review;

    public List<PassBean> getPass() {
        return pass;
    }

    public void setPass(List<PassBean> pass) {
        this.pass = pass;
    }

    public List<ReviewBean> getReview() {
        return review;
    }

    public void setReview(List<ReviewBean> review) {
        this.review = review;
    }

    public static class PassBean {
        /**
         * mch_id : 9
         * price : 2000
         * image_url : http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/tb/8c8856b0f456781f26d8b2cccc92cc46..jpg
         * store_id : 4
         * status : 0
         */

        private int mch_id;
        private String price;
        private String image_url;
        private int store_id;
        private int status;

        public int getMch_id() {
            return mch_id;
        }

        public void setMch_id(int mch_id) {
            this.mch_id = mch_id;
        }

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

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class ReviewBean {
        /**
         * mch_id : 9
         * price : 2000
         * image_url : http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/tb/8c8856b0f456781f26d8b2cccc92cc46..jpg
         * store_id : 4
         * status : 0
         */

        private int mch_id;
        private String price;
        private String image_url;
        private int store_id;
        private int status;

        public int getMch_id() {
            return mch_id;
        }

        public void setMch_id(int mch_id) {
            this.mch_id = mch_id;
        }

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

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
