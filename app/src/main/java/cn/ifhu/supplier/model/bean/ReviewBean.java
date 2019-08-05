package cn.ifhu.supplier.model.bean;

import java.util.List;

public class ReviewBean {

    /**
     * haoping : {"all":1,"haoping":1,"zhongping":0,"chaping":0,"rate":1}
     * com_list : [{"com_id":5,"content":"还可以","haoping":1,"kouwei":1,"baozhuang":1,"peisong":1,"add_time":"1970-01-01 08:00:00","member_avatar":"","member_name":"lijing","replay":""}]
     */

    public HaopingBean haoping;
    public List<ComListBean> com_list;

    public HaopingBean getHaoping() {
        return haoping;
    }

    public void setHaoping(HaopingBean haoping) {
        this.haoping = haoping;
    }

    public List<ComListBean> getCom_list() {
        return com_list;
    }

    public void setCom_list(List<ComListBean> com_list) {
        this.com_list = com_list;
    }

    public static class HaopingBean {
        /**
         * all : 1
         * haoping : 1
         * zhongping : 0
         * chaping : 0
         * rate : 1
         */

        private int all;
        private int haoping;
        private int zhongping;
        private int chaping;
        private int rate;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public int getHaoping() {
            return haoping;
        }

        public void setHaoping(int haoping) {
            this.haoping = haoping;
        }

        public int getZhongping() {
            return zhongping;
        }

        public void setZhongping(int zhongping) {
            this.zhongping = zhongping;
        }

        public int getChaping() {
            return chaping;
        }

        public void setChaping(int chaping) {
            this.chaping = chaping;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }
    }

    public static class ComListBean {
        /**
         * com_id : 5
         * content : 还可以
         * haoping : 1
         * kouwei : 1
         * baozhuang : 1
         * peisong : 1
         * add_time : 1970-01-01 08:00:00
         * member_avatar :
         * member_name : lijing
         * replay :
         */

        private int com_id;
        private String content;
        private int haoping;
        private int kouwei;
        private int baozhuang;
        private int peisong;
        private String add_time;
        private String member_avatar;
        private String member_name;
        private String replay;

        public int getCom_id() {
            return com_id;
        }

        public void setCom_id(int com_id) {
            this.com_id = com_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getHaoping() {
            return haoping;
        }

        public void setHaoping(int haoping) {
            this.haoping = haoping;
        }

        public int getKouwei() {
            return kouwei;
        }

        public void setKouwei(int kouwei) {
            this.kouwei = kouwei;
        }

        public int getBaozhuang() {
            return baozhuang;
        }

        public void setBaozhuang(int baozhuang) {
            this.baozhuang = baozhuang;
        }

        public int getPeisong() {
            return peisong;
        }

        public void setPeisong(int peisong) {
            this.peisong = peisong;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getReplay() {
            return replay;
        }

        public void setReplay(String replay) {
            this.replay = replay;
        }
    }
}
