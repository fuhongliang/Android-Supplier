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
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * @author fuhongliang
 */
public class DiscountProductAdapter extends BaseAdapter {
    public List<ProductManageBean.GoodsListBean> mDataList;
    public Context mContext;
    onClickItem onClickItem;
    DecimalFormat df = new DecimalFormat("0.00");

    public void setmDataList(List<ProductManageBean.GoodsListBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public DiscountProductAdapter(List<ProductManageBean.GoodsListBean> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    public void changeProductState(int position) {
        ProductManageBean.GoodsListBean goodsListBean = mDataList.get(position);
        mDataList.get(position).setGoods_state(goodsListBean.getGoods_state() == 0 ? 1 : 0);
        notifyDataSetChanged();
    }

    public void setOnClickItem(DiscountProductAdapter.onClickItem onClickItem) {
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
            convertView = inflater.inflate(R.layout.item_discount, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivProductImage.load(mDataList.get(position).getImg_name(),R.drawable.bnt_photo_moren);
        viewHolder.tvName.setText(mDataList.get(position).getGoods_name());

        if (isSelected(mDataList.get(position))) {
            viewHolder.ivDiscount.setVisibility(View.VISIBLE);
            viewHolder.tvDiscount.setVisibility(View.VISIBLE);
            viewHolder.ivChoose.setSelected(true);
            viewHolder.tvPrice.setText(Constants.unit + mDataList.get(position).getGoods_dicountprice());
            viewHolder.tvOriginalPrice.setText(Constants.unit + mDataList.get(position).getGoods_price());
            viewHolder.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            viewHolder.tvDiscount.setText("折扣价：" + df.format(StringUtils.stringToDouble(mDataList.get(position).getGoods_dicountprice()) / StringUtils.stringToDouble(mDataList.get(position).getGoods_price()) * 10) + " 折");
        } else {
            viewHolder.ivChoose.setSelected(false);
            viewHolder.tvPrice.setText(Constants.unit + mDataList.get(position).getGoods_price());
            viewHolder.ivDiscount.setVisibility(View.INVISIBLE);
            viewHolder.tvDiscount.setVisibility(View.INVISIBLE);
            viewHolder.tvOriginalPrice.setText("");
        }

        if (onClickItem != null) {
            viewHolder.ivChoose.setOnClickListener(v -> {
                if (viewHolder.ivChoose.isSelected()) {
                    viewHolder.ivChoose.setSelected(false);
                    onClickItem.unselectedGoods(position);
                } else {
                    onClickItem.setDiscountPrice(position);
                }
            });
        }
        return convertView;
    }

    public boolean isSelected(ProductManageBean.GoodsListBean goodsListBean) {
        try {
            List<ProductManageBean.GoodsListBean> mSelectedList = ProductLogic.getDiscountGoods();
            for (ProductManageBean.GoodsListBean bean : mSelectedList) {
                if (bean.getGoods_id() == goodsListBean.getGoods_id()) {
                    goodsListBean.setGoods_dicountprice(bean.getGoods_dicountprice());
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    static class ViewHolder {
        @BindView(R.id.iv_product_image)
        GlideImageView ivProductImage;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_original_price)
        TextView tvOriginalPrice;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        @BindView(R.id.iv_discount)
        ImageView ivDiscount;
        @BindView(R.id.tv_discount)
        TextView tvDiscount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickItem {
        void setDiscountPrice(int position);

        void unselectedGoods(int position);
    }

}
