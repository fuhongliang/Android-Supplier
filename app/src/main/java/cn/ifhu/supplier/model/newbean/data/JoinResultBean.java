package cn.ifhu.supplier.model.newbean.data;

/**
 * Created by KevinFu on 2019-07-02.
 * Copyright (c) 2019 KevinFu
 */
public class JoinResultBean {

    /**
     * apply_info : {"address":"新安三路28号海关大厦","city":"深圳市","district":"宝安区","header_bg":"http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/w6/5389e49fa99dfa2335914cf8344ac655..jpg","latitude":"22.572282","logo":"http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/ti/0ffa0dc8b5701b37a017942bc3622813..jpg","longitude":"113.913745","mch_common_cat_id":"1","name":"卓诗尼女装","password":"145544","province":"广东省","realname":"朱伟超","service_tel":"15920145544","tel":"15920145544","username":"15920145544","access_token":"","is_debug":1,"mch_id":0}
     * apply_result : {"apply_status":1,"apply_result":""}
     */

    private ApplyInfoBean apply_info;
    private ApplyResultBean apply_result;

    public ApplyInfoBean getApply_info() {
        return apply_info;
    }

    public void setApply_info(ApplyInfoBean apply_info) {
        this.apply_info = apply_info;
    }

    public ApplyResultBean getApply_result() {
        return apply_result;
    }

    public void setApply_result(ApplyResultBean apply_result) {
        this.apply_result = apply_result;
    }

    public static class ApplyInfoBean {
        /**
         * address : 新安三路28号海关大厦
         * city : 深圳市
         * district : 宝安区
         * header_bg : http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/w6/5389e49fa99dfa2335914cf8344ac655..jpg
         * latitude : 22.572282
         * logo : http://yiwuyimei-test.oss-cn-beijing.aliyuncs.com/app/upload/coverpic/ti/0ffa0dc8b5701b37a017942bc3622813..jpg
         * longitude : 113.913745
         * mch_common_cat_id : 1
         * name : 卓诗尼女装
         * password : 145544
         * province : 广东省
         * realname : 朱伟超
         * service_tel : 15920145544
         * tel : 15920145544
         * username : 15920145544
         * access_token :
         * is_debug : 1
         * mch_id : 0
         */

        private String address;
        private String city;
        private String district;
        private String header_bg;
        private String latitude;
        private String logo;
        private String longitude;
        private String mch_common_cat_id;
        private String name;
        private String password;
        private String province;
        private String realname;
        private String service_tel;
        private String tel;
        private String username;
        private String access_token;
        private int is_debug;
        private int mch_id;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getHeader_bg() {
            return header_bg;
        }

        public void setHeader_bg(String header_bg) {
            this.header_bg = header_bg;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getMch_common_cat_id() {
            return mch_common_cat_id;
        }

        public void setMch_common_cat_id(String mch_common_cat_id) {
            this.mch_common_cat_id = mch_common_cat_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getService_tel() {
            return service_tel;
        }

        public void setService_tel(String service_tel) {
            this.service_tel = service_tel;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getIs_debug() {
            return is_debug;
        }

        public void setIs_debug(int is_debug) {
            this.is_debug = is_debug;
        }

        public int getMch_id() {
            return mch_id;
        }

        public void setMch_id(int mch_id) {
            this.mch_id = mch_id;
        }
    }

    public static class ApplyResultBean {
        /**
         * apply_status : 1
         * apply_result :
         */

        private int apply_status;
        private String apply_result;

        public int getApply_status() {
            return apply_status;
        }

        public void setApply_status(int apply_status) {
            this.apply_status = apply_status;
        }

        public String getApply_result() {
            return apply_result;
        }

        public void setApply_result(String apply_result) {
            this.apply_result = apply_result;
        }
    }
}
