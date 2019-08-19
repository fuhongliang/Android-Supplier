package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class DeliverGoodsDetailDataBean {
    /**
     * deliver : {"deliver_id":2,"huodan_id":2,"is_send":0,"addtime":1565942003,"send_no":"S2019081615532315617","total_price":"21055.20","num":10,"goods_attr_count":10,"share_id":2,"name":"我是团长","mobile":"18319699581","district":"北京市北京市东城区这是我的小区"}
     * goods_list : [{"goods_id":102,"num":1,"total_price":"1899.20","attr":[{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}],"name":"黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png"},{"goods_id":77,"num":1,"total_price":"3520.00","attr":[{"attr_id":73,"attr_group_name":"颜色","attr_name":"K2指纹锁【左右开向】","no":""}],"name":"安全防盗门 智能门家庭进户门 入户门指纹密码锁门 莱茵河系列","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/aa/aa86fc4feb44657a80f92c6dc5b705c56cdbebd9.png"},{"goods_id":76,"num":1,"total_price":"2299.00","attr":[{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}],"name":"现代简约门静音门欧式房门室内门卧室门实木复合门定制木门油漆门AC020-J 多色可选","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/22/2273ecdd59835646c4652b36cbb549bbfb2fc8b7.png"},{"goods_id":75,"num":1,"total_price":"1096.00","attr":[{"attr_id":68,"attr_group_name":"颜色","attr_name":"珍珠白","no":""}],"name":"简约室内门实木复合门 房间门套装门 定制卧室门","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/5b/5bec7bb154baa3d414248604410927c04164932f.png"},{"goods_id":69,"num":1,"total_price":"2799.00","attr":[{"attr_id":43,"attr_group_name":"尺寸","attr_name":"1.8米衣柜","no":""}],"name":"北欧推拉门衣柜 现代简约卧室组合大衣柜 迪洛系列 1.8米衣柜 3门","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/c6/c6d27786a0e8490d08c124460f695c923076b098.png"},{"goods_id":68,"num":1,"total_price":"1889.00","attr":[{"attr_id":42,"attr_group_name":"尺寸","attr_name":"衣柜+顶柜+边柜","no":""}],"name":"苏菲洛克 衣柜 北欧简约平开门组合大衣柜衣橱 迪洛系列 4门 单衣柜","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/c6/c6d27786a0e8490d08c124460f695c923076b098.png"},{"goods_id":67,"num":1,"total_price":"168.00","attr":[{"attr_id":38,"attr_group_name":"颜色","attr_name":"升级款","no":""}],"name":"ColesHome衣柜简易收纳可折叠塑料非布柜子组合组装现代简约","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/0d/0d6c40c938dd605606c07841ba0fc374f249b73b.png"},{"goods_id":66,"num":1,"total_price":"2298.00","attr":[{"attr_id":20,"attr_group_name":"尺码","attr_name":"1.8m","no":"1"}],"name":"木月衣柜北欧现代简约推拉门衣柜 移门衣柜卧室趟门大衣橱雅致2.0m单衣柜","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/08/081543448cc2c2f860848643f306af828e747c83.png"},{"goods_id":59,"num":1,"total_price":"2399.00","attr":[{"attr_id":23,"attr_group_name":"颜色","attr_name":"不锈钢淋浴房","no":""}],"name":"箭牌卫浴（ARROW） 整体淋浴房弧扇形钢化玻璃简易淋浴房","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/23/23282cc6bb9f5ae3d976066146dbdbd8266575fc.png"},{"goods_id":58,"num":1,"total_price":"2688.00","attr":[{"attr_id":28,"attr_group_name":"尺寸","attr_name":"1000*1000*1900","no":""}],"name":"黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png"}]
     */

    private DeliverBean deliver;
    private List<GoodsListBean> goods_list;

    public DeliverBean getDeliver() {
        return deliver;
    }

    public void setDeliver(DeliverBean deliver) {
        this.deliver = deliver;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class DeliverBean {
        /**
         * deliver_id : 2
         * huodan_id : 2
         * is_send : 0
         * addtime : 1565942003
         * send_no : S2019081615532315617
         * total_price : 21055.20
         * num : 10
         * goods_attr_count : 10
         * share_id : 2
         * name : 我是团长
         * mobile : 18319699581
         * district : 北京市北京市东城区这是我的小区
         */

        private int deliver_id;
        private int huodan_id;
        private int is_send;
        private int addtime;
        private String send_no;
        private String total_price;
        private int num;
        private int goods_attr_count;
        private int share_id;
        private String name;
        private String mobile;
        private String district;

        public int getDeliver_id() {
            return deliver_id;
        }

        public void setDeliver_id(int deliver_id) {
            this.deliver_id = deliver_id;
        }

        public int getHuodan_id() {
            return huodan_id;
        }

        public void setHuodan_id(int huodan_id) {
            this.huodan_id = huodan_id;
        }

        public int getIs_send() {
            return is_send;
        }

        public void setIs_send(int is_send) {
            this.is_send = is_send;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getSend_no() {
            return send_no;
        }

        public void setSend_no(String send_no) {
            this.send_no = send_no;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getGoods_attr_count() {
            return goods_attr_count;
        }

        public void setGoods_attr_count(int goods_attr_count) {
            this.goods_attr_count = goods_attr_count;
        }

        public int getShare_id() {
            return share_id;
        }

        public void setShare_id(int share_id) {
            this.share_id = share_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

    public static class GoodsListBean {
        /**
         * goods_id : 102
         * num : 1
         * total_price : 1899.20
         * attr : [{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}]
         * name : 黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室
         * pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png
         */

        private int goods_id;
        private int num;
        private String total_price;
        private String name;
        private String pic;
        private List<AttrBean> attr;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public List<AttrBean> getAttr() {
            return attr;
        }

        public void setAttr(List<AttrBean> attr) {
            this.attr = attr;
        }

        public static class AttrBean {
            /**
             * attr_id : 1
             * attr_group_name : 规格
             * attr_name : 默认
             * no :
             */

            private int attr_id;
            private String attr_group_name;
            private String attr_name;
            private String no;

            public int getAttr_id() {
                return attr_id;
            }

            public void setAttr_id(int attr_id) {
                this.attr_id = attr_id;
            }

            public String getAttr_group_name() {
                return attr_group_name;
            }

            public void setAttr_group_name(String attr_group_name) {
                this.attr_group_name = attr_group_name;
            }

            public String getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }
        }
    }
//    private DeliverBean deliver;
//
//    public DeliverBean getDeliver() {
//        return deliver;
//    }
//
//    public void setDeliver(DeliverBean deliver) {
//        this.deliver = deliver;
//    }
//
//    public static class DeliverBean {
//        private int deliver_id;
//        private int huodan_id;
//        private int is_send;
//        private int addtime;
//        private int total_price;
//        private int num;
//        private int goods_attr_count;
//        private int share_id;
//        private String name;
//        private String mobile;
//        private String district;
//
//        public int getDeliver_id() {
//            return deliver_id;
//        }
//
//        public void setDeliver_id(int deliver_id) {
//            this.deliver_id = deliver_id;
//        }
//
//        public int getHuodan_mch_id() {
//            return huodan_id;
//        }
//
//        public void setHuodan_mch_id(int huodan_mch_id) {
//            this.huodan_id = huodan_id;
//        }
//
//        public int getIs_send() {
//            return is_send;
//        }
//
//        public void setIs_send(int is_send) {
//            this.is_send = is_send;
//        }
//
//        public int getAddtime() {
//            return addtime;
//        }
//
//        public void setAddtime(int addtime) {
//            this.addtime = addtime;
//        }
//
//        public int getTotal_price() {
//            return total_price;
//        }
//
//        public void setTotal_price(int total_price) {
//            this.total_price = total_price;
//        }
//
//        public int getNum() {
//            return num;
//        }
//
//        public void setNum(int num) {
//            this.num = num;
//        }
//
//        public int getGoods_attr_count() {
//            return goods_attr_count;
//        }
//
//        public void setGoods_attr_count(int goods_attr_count) {
//            this.goods_attr_count = goods_attr_count;
//        }
//
//        public int getShare_id() {
//            return share_id;
//        }
//
//        public void setShare_id(int share_id) {
//            this.share_id = share_id;
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
//        public String getMobile() {
//            return mobile;
//        }
//
//        public void setMobile(String mobile) {
//            this.mobile = mobile;
//        }
//
//        public String getDistrict() {
//            return district;
//        }
//
//        public void setDistrict(String district) {
//            this.district = district;
//        }
//    }
//
//    private List<GoodsListBean> goods_list;
//
//    public List<GoodsListBean> getGoods_list() {
//        return goods_list;
//    }
//
//    public void setGoods_list(List<GoodsListBean> goods_list) {
//        this.goods_list = goods_list;
//    }
//
//    public static class GoodsListBean {
//        private int goods_id;
//        private int num;
//        private int total_price;
//        private String name;
//        private String pic;
//
//        public int getGoods_id() {
//            return goods_id;
//        }
//
//        public void setGoods_id(int goods_id) {
//            this.goods_id = goods_id;
//        }
//
//        public int getNum() {
//            return num;
//        }
//
//        public void setNum(int num) {
//            this.num = num;
//        }
//
//        public int getTotal_price() {
//            return total_price;
//        }
//
//        public void setTotal_price(int total_price) {
//            this.total_price = total_price;
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
//        public String getPic() {
//            return pic;
//        }
//
//        public void setPic(String pic) {
//            this.pic = pic;
//        }
//
//        public AttrBean getAttr() {
//            return attr;
//        }
//
//        public void setAttr(AttrBean attr) {
//            this.attr = attr;
//        }
//
//        private AttrBean attr;
//
//        public static class AttrBean {
//        private String attr_id;
//        private String attr_group_name;
//        private String attr_name;
//        private String no;
//
//            public String getAttr_id() {
//                return attr_id;
//            }
//
//            public void setAttr_id(String attr_id) {
//                this.attr_id = attr_id;
//            }
//
//            public String getAttr_group_name() {
//                return attr_group_name;
//            }
//
//            public void setAttr_group_name(String attr_group_name) {
//                this.attr_group_name = attr_group_name;
//            }
//
//            public String getAttr_name() {
//                return attr_name;
//            }
//
//            public void setAttr_name(String attr_name) {
//                this.attr_name = attr_name;
//            }
//
//            public String getNo() {
//                return no;
//            }
//
//            public void setNo(String no) {
//                this.no = no;
//            }
//        }
//    }

}
