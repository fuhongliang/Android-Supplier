package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class ShareListDataBean {
    /**
     * row_count : 2
     * page_count : 1
     * list : [{"id":1,"name":"刘燕家","avatar_url":"https://wx.qlogo.cn/mmopen/vi_32/ZCzOGJETuzHg8K4djZ3mUqdKtaS9CZ3Ij6kicfIF5u4CrrbOJ7MJMsyyCVHZealHTkouBcGiarPOQOyu0ZyUNGMw/132","address":"浙江省温州市乐清市海关大厦"},{"id":2,"name":"我是团长","avatar_url":"https://wx.qlogo.cn/mmopen/vi_32/mDvEsaANsJyqK245wWkf9Yqq5rVldsSibLayCUMYiacB4DQJsO1DVn46oPFy2OPROyxByegvakRUvVBR1CYRicREw/132","address":"北京市北京市东城区这是我的小区"}]
     */

    private int row_count;
    private int page_count;
    private List<ListBean> list;

    public int getRow_count() {
        return row_count;
    }

    public void setRow_count(int row_count) {
        this.row_count = row_count;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * name : 刘燕家
         * avatar_url : https://wx.qlogo.cn/mmopen/vi_32/ZCzOGJETuzHg8K4djZ3mUqdKtaS9CZ3Ij6kicfIF5u4CrrbOJ7MJMsyyCVHZealHTkouBcGiarPOQOyu0ZyUNGMw/132
         * address : 浙江省温州市乐清市海关大厦
         */

        private int id;
        private String name;
        private String avatar_url;
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

//    /**
//     * row_count : 0
//     * page_count : 0
//     * list : []
//     */
//
//    private int row_count;
//    private int page_count;
//    private List<ShareBean> list;
//
//    public int getRow_count() {
//        return row_count;
//    }
//
//    public void setRow_count(int row_count) {
//        this.row_count = row_count;
//    }
//
//    public int getPage_count() {
//        return page_count;
//    }
//
//    public void setPage_count(int page_count) {
//        this.page_count = page_count;
//    }
//
//    public List<ShareBean> getList() {
//        return list;
//    }
//
//    public void setList(List<ShareBean> list) {
//        this.list = list;
//    }
//
//    public static class ShareBean {
//        private String name;
//        private String address;
//        private int id;
//        private String avatar_url;
//
//        public String getAvatar_url() {
//            return avatar_url;
//        }
//
//        public void setAvatar_url(String avatar_url) {
//            this.avatar_url = avatar_url;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//    }

}
