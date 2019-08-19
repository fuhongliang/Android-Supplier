package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class DeliverGoodsDataBean {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * deliver_id : 2
         * huodan_id : 4
         * is_send : 0
         * addtime : 1565844179
         * send_no : S2019081512425964650
         * total_price : 1899.20
         * num : 1
         * goods_attr_count : 1
         * share_id : 1
         * name : 刘燕家
         * mobile : 13728202087
         * district : 广东省深圳宝安海关大厦
         * goods_info : {"num":1,"total_price":"1899.20","attr":[{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}],"name":"黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室","pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png"}
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
        private GoodsInfoBean goods_info;

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

        public GoodsInfoBean getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(GoodsInfoBean goods_info) {
            this.goods_info = goods_info;
        }

        public static class GoodsInfoBean {
            /**
             * num : 1
             * total_price : 1899.20
             * attr : [{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}]
             * name : 黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室
             * pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png
             */

            private int num;
            private String total_price;
            private String name;
            private String pic;
            private List<AttrBean> attr;

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
    }
//    private List<ListBean> list;
//
//    public List<ListBean> getList() {
//        return list;
//    }
//
//    public void setList(List<ListBean> list) {
//        this.list = list;
//    }
//
//    public static class ListBean {
//        private int deliver_id;
//        private int huodan_mch_id;
//        private int is_send;
//        private int addtime;
//        private String total_price;
//        private int num;
//        private int goods_attr_count;
//        private int share_id;
//        private String name;
//        private String mobile;
//        private String district;
//
//        private GoodsInfoBean goods_info;
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
//            return huodan_mch_id;
//        }
//
//        public void setHuodan_mch_id(int huodan_mch_id) {
//            this.huodan_mch_id = huodan_mch_id;
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
//        public String getTotal_price() {
//            return total_price;
//        }
//
//        public void setTotal_price(String total_price) {
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
//
//        public GoodsInfoBean getGoods_info() {
//            return goods_info;
//        }
//
//        public void setGoods_info(GoodsInfoBean goods_info) {
//            this.goods_info = goods_info;
//        }
//
//        public static class GoodsInfoBean {
//            private String name;
//            private int total_price;
//            private String pic;
//            private int num;
//            private List<AttrBean> attr;
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public int getTotal_price() {
//                return total_price;
//            }
//
//            public void setTotal_price(int total_price) {
//                this.total_price = total_price;
//            }
//
//            public String getPic() {
//                return pic;
//            }
//
//            public void setPic(String pic) {
//                this.pic = pic;
//            }
//
//            public int getNum() {
//                return num;
//            }
//
//            public void setNum(int num) {
//                this.num = num;
//            }
//
//            public List<AttrBean> getAttr() {
//                return attr;
//            }
//
//            public void setAttr(List<AttrBean> attr) {
//                this.attr = attr;
//            }
//
//            public static class AttrBean {
//
//                private int attr_id;
//                private String attr_group_name;
//                private String attr_name;
//                private String no;
//
//                public int getAttr_id() {
//                    return attr_id;
//                }
//
//                public void setAttr_id(int attr_id) {
//                    this.attr_id = attr_id;
//                }
//
//                public String getAttr_group_name() {
//                    return attr_group_name;
//                }
//
//                public void setAttr_group_name(String attr_group_name) {
//                    this.attr_group_name = attr_group_name;
//                }
//
//                public String getAttr_name() {
//                    return attr_name;
//                }
//
//                public void setAttr_name(String attr_name) {
//                    this.attr_name = attr_name;
//                }
//
//                public String getNo() {
//                    return no;
//                }
//
//                public void setNo(String no) {
//                    this.no = no;
//                }
//            }
//        }
//    }

}
