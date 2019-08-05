package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

/**
 * 商品分类数据类
 */
public class ProductCatsBean {

    private List<CatsBean> cats;

    public List<CatsBean> getCats() {
        return cats;
    }

    public void setCats(List<CatsBean> cats) {
        this.cats = cats;
    }

    public static class CatsBean {
        /**
         * id : 1
         * parent_id : 0
         * mch_id : 4
         * name : 浴室柜
         * icon :
         * sort : 100
         * is_delete : 0
         * addtime : 1560769477
         */

        private int id;
        private int parent_id;
        private int mch_id;
        private String name;
        private String icon;
        private int sort;
        private int is_delete;
        private int addtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getMch_id() {
            return mch_id;
        }

        public void setMch_id(int mch_id) {
            this.mch_id = mch_id;
        }

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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }
    }
}
