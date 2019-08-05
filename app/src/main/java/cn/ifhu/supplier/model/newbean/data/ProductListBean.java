package cn.ifhu.supplier.model.newbean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductListBean {

    private List<GoodsBean> goods;

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean implements Parcelable {
        /**
         * goods_id : 1
         * cover_pic : http://yiwuyimei.oss-cn-beijing.aliyuncs.com/web/uploads/image/ed/ed23d6fd902402b213873b98279225fe84f15fcc.png
         * name : ARROW箭牌卫浴节水静音坐便器喷射虹吸马桶
         * price : 1.00
         * goods_num : 600
         * original_price : 2.00
         * status : 1  0->下架 1->上架
         */

        private int goods_id;
        private String cover_pic;
        private String name;
        private String price;
        private String original_price;
        private int goods_num;
        private int status;

        protected GoodsBean(Parcel in) {
            goods_id = in.readInt();
            cover_pic = in.readString();
            name = in.readString();
            price = in.readString();
            original_price = in.readString();
            goods_num = in.readInt();
            status = in.readInt();
        }

        public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
            @Override
            public GoodsBean createFromParcel(Parcel in) {
                return new GoodsBean(in);
            }

            @Override
            public GoodsBean[] newArray(int size) {
                return new GoodsBean[size];
            }
        };

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getCover_pic() {
            return cover_pic;
        }

        public void setCover_pic(String cover_pic) {
            this.cover_pic = cover_pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(goods_id);
            parcel.writeString(cover_pic);
            parcel.writeString(name);
            parcel.writeString(price);
            parcel.writeString(original_price);
            parcel.writeInt(goods_num);
            parcel.writeInt(status);
        }
    }
}
