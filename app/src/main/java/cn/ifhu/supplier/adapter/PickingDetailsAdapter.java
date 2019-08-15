package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Attr;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.newbean.data.PickingDetailsDataBean;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class PickingDetailsAdapter extends BaseAdapter {
    List<PickingDetailsDataBean.GoodsListBean> mData;
    Context mContext;

    public PickingDetailsAdapter(List<PickingDetailsDataBean.GoodsListBean> data, Context mContext) {
        this.mData = data;
        this.mContext = mContext;
    }

    public void setmData(List<PickingDetailsDataBean.GoodsListBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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
            convertView = layoutInflater.inflate(R.layout.item_picking_detalis, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivPhoto.load(mData.get(position).getPic());
        viewHolder.tvProductName.setText(mData.get(position).getName());
//        String s = "";
//        for (PickingDetailsDataBean.GoodsListBean.AttrBean bean){
//             s += bean.getAttr_group_name();
//        }
        viewHolder.tvSpecification.setText(mData.get(position).getAttr().get(0).getAttr_group_name());
        viewHolder.tvDefault.setText(mData.get(position).getAttr().get(0).getAttr_name());
        viewHolder.tvPrice.setText("￥" + mData.get(position).getTotal_price()+"");
        viewHolder.tvAmount.setText("x" + mData.get(position).getNum()+"");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_photo)
        GlideImageView ivPhoto;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_specification)
        TextView tvSpecification;
        @BindView(R.id.tv_default)
        TextView tvDefault;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_amount)
        TextView tvAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
