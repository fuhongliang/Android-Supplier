package cn.ifhu.supplier.model.bean;


import cn.ifhu.supplier.model.newbean.post.BasePostBean;

/**
 * @author fuhongliang
 */
public interface UserServiceBean  {

    /**
     * 登录上传的信息
     */
    class LoginForm extends BasePostBean {
        public String username;
        public String password;
        public String captcha;

        public String getUsername() {
            return username;
        }

        public void setUsername(String member_mobile) {
            this.username = member_mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String member_passwd) {
            this.password = member_passwd;
        }

        public String getCaptcha() {
            return captcha;
        }

        public void setCaptcha(String captcha) {
            this.captcha = captcha;
        }
    }

    class LoginResponse {
        /**
         * store_id : 7
         * store_name : test002
         * store_phone : 13166668888
         * store_avatar :
         * area_info : 河北 唐山市 丰润区
         * store_address : 21213
         * work_start_time : 08:00
         * work_end_time : 22:00
         * store_state : 1
         * store_description :
         * business_licence_number_electronic : 06062332464779218.jpg
         * member_id : 12
         * member_mobile :
         * member_name : test
         * token : cb3ee81a339e755fc0ece10665f13b1c
         */

        private int store_id;
        private String store_name;
        private String store_phone;
        private String store_avatar;
        private String area_info;
        private String store_address;
        private String work_start_time;
        private String work_end_time;
        private int store_state;
        private String store_description;
        private String business_licence_number_electronic;
        private int member_id;
        private String member_mobile;
        private String member_name;
        private String token;
        private String joinin_url;

        public String getJoinin_url() {
            return joinin_url;
        }

        public void setJoinin_url(String joinin_url) {
            this.joinin_url = joinin_url;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        public String getArea_info() {
            return area_info;
        }

        public void setArea_info(String area_info) {
            this.area_info = area_info;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getWork_start_time() {
            return work_start_time;
        }

        public void setWork_start_time(String work_start_time) {
            this.work_start_time = work_start_time;
        }

        public String getWork_end_time() {
            return work_end_time;
        }

        public void setWork_end_time(String work_end_time) {
            this.work_end_time = work_end_time;
        }

        public int getStore_state() {
            return store_state;
        }

        public void setStore_state(int store_state) {
            this.store_state = store_state;
        }

        public String getStore_description() {
            return store_description;
        }

        public void setStore_description(String store_description) {
            this.store_description = store_description;
        }

        public String getBusiness_licence_number_electronic() {
            return business_licence_number_electronic;
        }

        public void setBusiness_licence_number_electronic(String business_licence_number_electronic) {
            this.business_licence_number_electronic = business_licence_number_electronic;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public String getMember_mobile() {
            return member_mobile;
        }

        public void setMember_mobile(String member_mobile) {
            this.member_mobile = member_mobile;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
