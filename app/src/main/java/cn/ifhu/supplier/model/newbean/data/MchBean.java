package cn.ifhu.supplier.model.newbean.data;

public class MchBean {


    /**
     * mch_info : {"mch_id":4,"access_token":"n084MHmcebwsm-VtnD_CxoCd0c3D4Wb8","store_id":4,"is_open":1,"is_lock":0,"review_status":1,"review_result":null,"review_time":1560567706,"realname":"箭牌客服"}
     */

    private MchInfoBean mch_info;

    public MchInfoBean getMch_info() {
        return mch_info;
    }

    public void setMch_info(MchInfoBean mch_info) {
        this.mch_info = mch_info;
    }

    public static class MchInfoBean {

        /**
         * mch_id : 6
         * access_token : _cFGnGIwvP77mTRvkYvmtm2FQTrLtyIu
         * store_id : 4
         * is_open : 1
         * is_lock : 0
         * review_status : 1
         * review_result : null
         * review_time : 1561527762
         * realname : test3
         * tel : 18319111111
         * mch_name : test3
         * service_tel : 8888888
         * logo : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/d0/d0ae0d192d6f0d6c54dff2bbbe1935732d1205f5.jpg
         */

        private int mch_id;
        private String access_token;
        private int store_id;
        private int is_open;
        private int is_lock;
        private int review_status;
        private Object review_result;
        private int review_time;
        private String realname;
        private String tel;
        private String mch_name;
        private String service_tel;
        private String logo;
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getMch_id() {
            return mch_id;
        }

        public void setMch_id(int mch_id) {
            this.mch_id = mch_id;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getIs_open() {
            return is_open;
        }

        public void setIs_open(int is_open) {
            this.is_open = is_open;
        }

        public int getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(int is_lock) {
            this.is_lock = is_lock;
        }

        public int getReview_status() {
            return review_status;
        }

        public void setReview_status(int review_status) {
            this.review_status = review_status;
        }

        public Object getReview_result() {
            return review_result;
        }

        public void setReview_result(Object review_result) {
            this.review_result = review_result;
        }

        public int getReview_time() {
            return review_time;
        }

        public void setReview_time(int review_time) {
            this.review_time = review_time;
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

        public String getMch_name() {
            return mch_name;
        }

        public void setMch_name(String mch_name) {
            this.mch_name = mch_name;
        }

        public String getService_tel() {
            return service_tel;
        }

        public void setService_tel(String service_tel) {
            this.service_tel = service_tel;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}