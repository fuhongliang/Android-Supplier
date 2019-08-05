package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

/**
 * Created by KevinFu on 2019-07-01.
 * Copyright (c) 2019 KevinFu
 */
public class SystemClassBean {

    private List<PtCatsBean> pt_cats;

    public List<PtCatsBean> getPt_cats() {
        return pt_cats;
    }

    public void setPt_cats(List<PtCatsBean> pt_cats) {
        this.pt_cats = pt_cats;
    }

    public static class PtCatsBean {
        /**
         * id : 3
         * store_id : 4
         * parent_id : 1
         * name : 淋浴房
         * pic_url : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/e8/e8512bbf8f4e05397f59322e528ffd41694aaf37.png
         * sort : 6
         * addtime : 1560506549
         * is_delete : 0
         * big_pic_url :
         * advert_pic :
         * advert_url :
         * is_show : 1
         */

        private int id;
        private int store_id;
        private int parent_id;
        private String name;
        private String pic_url;
        private int sort;
        private int addtime;
        private int is_delete;
        private String big_pic_url;
        private String advert_pic;
        private String advert_url;
        private int is_show;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public String getBig_pic_url() {
            return big_pic_url;
        }

        public void setBig_pic_url(String big_pic_url) {
            this.big_pic_url = big_pic_url;
        }

        public String getAdvert_pic() {
            return advert_pic;
        }

        public void setAdvert_pic(String advert_pic) {
            this.advert_pic = advert_pic;
        }

        public String getAdvert_url() {
            return advert_url;
        }

        public void setAdvert_url(String advert_url) {
            this.advert_url = advert_url;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }
    }
}
