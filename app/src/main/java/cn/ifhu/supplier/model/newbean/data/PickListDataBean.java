package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class PickListDataBean {
    private List<PickListBean> pick_list;

    public List<PickListBean> getPick_list() {
        return pick_list;
    }

    public void setPick_list(List<PickListBean> pick_list) {
        this.pick_list = pick_list;
    }

    public static class PickListBean {
        /**
         * total_pay_price : 78362.14
         * goods_num : 50
         * goods_count : 16
         * create_time : 2019-08-06 15:35:33
         * pick_status : 0
         * goods_info : {"is_pick":0,"num":1,"pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png","attr":[{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}],"name":"黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室"}
         */

        private String total_pay_price;
        private int goods_num;
        private int goods_count;
        private String create_time;
        private int pick_status;
        private String huodan_id;

        public String getHuodan_id() {
            return huodan_id;
        }

        public void setHuodan_id(String huodan_id) {
            this.huodan_id = huodan_id;
        }

        private GoodsInfoBean goods_info;

        public String getTotal_pay_price() {
            return total_pay_price;
        }

        public void setTotal_pay_price(String total_pay_price) {
            this.total_pay_price = total_pay_price;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(int goods_count) {
            this.goods_count = goods_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getPick_status() {
            return pick_status;
        }

        public void setPick_status(int pick_status) {
            this.pick_status = pick_status;
        }

        public GoodsInfoBean getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(GoodsInfoBean goods_info) {
            this.goods_info = goods_info;
        }

        public static class GoodsInfoBean {
            /**
             * is_pick : 0
             * num : 1
             * pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png
             * attr : [{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}]
             * name : 黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室
             */

            private int is_pick;
            private int num;
            private String pic;
            private String name;
            private String total_price;

            public String getTotal_price() {
                return total_price;
            }

            public void setTotal_price(String total_price) {
                this.total_price = total_price;
            }

            private List<AttrBean> attr;

            public int getIs_pick() {
                return is_pick;
            }

            public void setIs_pick(int is_pick) {
                this.is_pick = is_pick;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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


//    private List<PickListBean> pick_list;
//
//    public List<PickListBean> getPick_list() {
//        return pick_list;
//    }
//
//    public void setPick_list(List<PickListBean> pick_list) {
//        this.pick_list = pick_list;
//    }
//
//    public static class PickListBean {
//        /**
//         * total_pay_price : 78362.14
//         * goods_num : 50
//         * goods_count : 16
//         * create_time : 2019-08-06 15:35:33
//         * pick_status : 0
//         * goods_info : {"is_pick":0,"num":1,"pic":"http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png","attr":[{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}],"name":"黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室"}
//         */
//
//        private String total_pay_price;
//        private int huodan_id;
//        private int goods_num;
//        private int goods_count;
//        private String create_time;
//        private int pick_status;
//        private GoodsInfoBean goods_info;
//
//        public String getTotal_pay_price() {
//            return total_pay_price;
//        }
//
//        public void setTotal_pay_price(String total_pay_price) {
//            this.total_pay_price = total_pay_price;
//        }
//        public int getHuodan_id() {
//            return huodan_id;
//        }
//
//        public void setHuodan_id(int huodan_id) {
//            this.huodan_id = huodan_id;
//        }
//
//        public int getGoods_num() {
//            return goods_num;
//        }
//
//        public void setGoods_num(int goods_num) {
//            this.goods_num = goods_num;
//        }
//
//        public int getGoods_count() {
//            return goods_count;
//        }
//
//        public void setGoods_count(int goods_count) {
//            this.goods_count = goods_count;
//        }
//
//        public String getCreate_time() {
//            return create_time;
//        }
//
//        public void setCreate_time(String create_time) {
//            this.create_time = create_time;
//        }
//
//        public int getPick_status() {
//            return pick_status;
//        }
//
//        public void setPick_status(int pick_status) {
//            this.pick_status = pick_status;
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
//            /**
//             * is_pick : 0
//             * num : 1
//             * pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/30/302c4262c1e7e2d546048b9e77b634effd1907ca.png
//             * attr : [{"attr_id":1,"attr_group_name":"规格","attr_name":"默认","no":""}]
//             * name : 黑色弧扇形淋浴房整体扇形浴屏卫生间隔断玻璃门简易干湿分离浴室
//             */
//
//            private int is_pick;
//            private int num;
//            private String pic;
//            private String name;
//            private List<AttrBean> attr;
//            private String total_price;
//
//            public String getTotal_price() {
//                return total_price;
//            }
//
//            public void setTotal_price(String total_price) {
//                this.total_price = total_price;
//            }
//
//            public int getIs_pick() {
//                return is_pick;
//            }
//
//            public void setIs_pick(int is_pick) {
//                this.is_pick = is_pick;
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
//            public String getPic() {
//                return pic;
//            }
//
//            public void setPic(String pic) {
//                this.pic = pic;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
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
//                /**
//                 * attr_id : 1
//                 * attr_group_name : 规格
//                 * attr_name : 默认
//                 * no :
//                 */
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
