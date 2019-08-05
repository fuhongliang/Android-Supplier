package cn.ifhu.supplier.model.newbean.data;

/**
 * Created by KevinFu on 2019-06-28.
 * Copyright (c) 2019 KevinFu
 */
public class StoreInfoBean {

    /**
     * mch : {"name":"陆丰箭牌卫浴旗舰店","cat_name":"全屋定制","logo":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/84/84b2b6f4b990b43b10279f8e01329900cbc28ffc.png","province_id":1941,"city_id":2040,"district_id":2044,"realname":"箭牌客服001","tel":"18319699581","service_tel":"18319699581","header_bg":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/bc/bceaae7cd92c68f2be5173a8dfec1ca158fc89a5.jpg","address":"国美家具城88号 66路城中村","region":"广东省汕尾市陆丰市"}
     */

    private MchBean mch;

    public MchBean getMch() {
        return mch;
    }

    public void setMch(MchBean mch) {
        this.mch = mch;
    }

    public static class MchBean {
        /**
         * name : 陆丰箭牌卫浴旗舰店
         * cat_name : 全屋定制
         * logo : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/84/84b2b6f4b990b43b10279f8e01329900cbc28ffc.png
         * province_id : 1941
         * city_id : 2040
         * district_id : 2044
         * realname : 箭牌客服001
         * tel : 18319699581
         * service_tel : 18319699581
         * header_bg : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/bc/bceaae7cd92c68f2be5173a8dfec1ca158fc89a5.jpg
         * address : 国美家具城88号 66路城中村
         * region : 广东省汕尾市陆丰市
         */

        private String name;
        private String cat_name;
        private String logo;
        private int province_id;
        private int city_id;
        private int district_id;
        private String realname;
        private String tel;
        private String service_tel;
        private String header_bg;
        private String address;
        private String region;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(int district_id) {
            this.district_id = district_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getService_tel() {
            return service_tel;
        }

        public void setService_tel(String service_tel) {
            this.service_tel = service_tel;
        }

        public String getHeader_bg() {
            return header_bg;
        }

        public void setHeader_bg(String header_bg) {
            this.header_bg = header_bg;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }
}
