package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class ShareListDataBean {

    /**
     * row_count : 0
     * page_count : 0
     * list : []
     */

    private int row_count;
    private int page_count;
    private List<ShareBean> list;

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

    public List<ShareBean> getList() {
        return list;
    }

    public void setList(List<ShareBean> list) {
        this.list = list;
    }

    public static class ShareBean {
        private String name;
        private String address;
        private int id;
        private String avatar_url;

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
