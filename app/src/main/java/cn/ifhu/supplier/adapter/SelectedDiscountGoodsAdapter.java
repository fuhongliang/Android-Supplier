package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.bean.ProductManageBean;
import cn.ifhu.supplier.utils.Constants;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * @author fuhongliang
 */
public class SelectedDiscountGoodsAdapter extends BaseAdapter {
    public List<ProductManageBean.GoodsListBean> mDataList;
    public Context mContext;
    onClickItem onClickItem;
    DecimalFormat df = new DecimalFormat("0.00");

    public void setmDataList(List<ProductManageBean.GoodsListBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public SelectedDiscountGoodsAdapter(List<ProductManageBean.GoodsListBean> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    public void changeProductState(int position) {
        ProductManageBean.GoodsListBean goodsListBean = mDataList.get(position);
        mDataList.get(position).setGoods_state(goodsListBean.getGoods_state() == 0 ? 1 : 0);
        notifyDataSetChanged();
    }

    public void setOnClickItem(SelectedDiscountGoodsAdapter.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_modify_discount, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivProductImage.load(mDataList.get(position).getImg_name());
        viewHolder.tvName.setText(mDataList.get(position).getGoods_name());

        viewHolder.tvPrice.setText(Constants.unit + mDataList.get(position).getGoods_dicountprice());
        viewHolder.tvOriginalPrice.setText(Constants.unit + mDataList.get(position).getGoods_price());
        viewHolder.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        viewHolder.tvDiscountRate.setText("折扣价：" + df.format(StringUtils.stringToDouble(mDataList.get(position).getGoods_dicountprice()) / StringUtils.stringToDouble(mDataList.get(position).getGoods_price()) * 10) + " 折");


        if (onClickItem != null) {
            viewHolder.delete.setOnClickListener(v -> {
                onClickItem.unselectedGoods(position);
            });
        }
        return convertView;
    }


    public void clearGoodsDiscountPrice(int position) {
        mDataList.get(position).setGoods_dicountprice("");
        notifyDataSetChanged();
    }

    public void setGoodsDiscountPrice(int position, String price) {
        mDataList.get(position).setGoods_dicountprice(price);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.iv_product_image)
        GlideImageView ivProductImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_zhekou)
        ImageView ivZhekou;
        @BindView(R.id.tv_discount_rate)
        TextView tvDiscountRate;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_original_price)
        TextView tvOriginalPrice;
        @BindView(R.id.delete)
        ImageView delete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickItem {
        void setDiscountPrice(int position);

        void unselectedGoods(int position);
    }

}
