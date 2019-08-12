package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.newbean.data.DeliverGoodsDetailDataBean;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class DeliverDetailAdapter extends BaseAdapter {
    private List<DeliverGoodsDetailDataBean.GoodsListBean> mDatas;
    private Context mContext;

    public DeliverDetailAdapter(List<DeliverGoodsDetailDataBean.GoodsListBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDatas.size();
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
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_order_product, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivPhoto.load(mDatas.get(position).getPic());
        viewHolder.tvProductName.setText(mDatas.get(position).getName());
        viewHolder.tvSpecification.setText(mDatas.get(position).getAttr().getAttr_name());
        viewHolder.tvPrice.setText(mDatas.get(position).getTotal_price());
        viewHolder.tvAmount.setText(mDatas.get(position).getNum());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_photo)
        GlideImageView ivPhoto;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_specification)
        TextView tvSpecification;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_amount)
        TextView tvAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
