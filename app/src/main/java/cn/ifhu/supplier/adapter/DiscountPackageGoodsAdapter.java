package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.bean.ProductManageBean;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * @author fuhongliang
 */
public class DiscountPackageGoodsAdapter extends BaseAdapter {
    public List<ProductManageBean.GoodsListBean> mDataList;
    public Context mContext;
    onClickItem onClickItem;

    public void setmDataList(List<ProductManageBean.GoodsListBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public DiscountPackageGoodsAdapter(List<ProductManageBean.GoodsListBean> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    public void changeProductState(int position) {
        ProductManageBean.GoodsListBean goodsListBean = mDataList.get(position);
        mDataList.get(position).setGoods_state(goodsListBean.getGoods_state() == 0 ? 1 : 0);
        notifyDataSetChanged();
    }

    public void setOnClickItem(DiscountPackageGoodsAdapter.onClickItem onClickItem) {
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
            convertView = inflater.inflate(R.layout.item_discount_package, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivProductImage.load(mDataList.get(position).getImg_name());
        viewHolder.tvName.setText(mDataList.get(position).getGoods_name());

        viewHolder.tvPrice.setText("原价:￥" + mDataList.get(position).getGoods_price());
        viewHolder.tvDiscountPackagePrice.setText(mDataList.get(position).getGoods_dicountprice());

        if (onClickItem != null) {
            viewHolder.ivDelete.setOnClickListener(v -> {
                onClickItem.deleteGoods(position);
            });
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_product_image)
        GlideImageView ivProductImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_discount_package_price)
        TextView tvDiscountPackagePrice;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickItem {

        void deleteGoods(int position);
    }

}
