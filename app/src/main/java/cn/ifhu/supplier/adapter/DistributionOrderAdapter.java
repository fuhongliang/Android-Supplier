package cn.ifhu.supplier.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseLoadMoreAdapter;
import cn.ifhu.supplier.model.newbean.data.DeliverGoodsDataBean;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class DistributionOrderAdapter extends BaseLoadMoreAdapter<DeliverGoodsDataBean.ListBean, DistributionOrderAdapter.ViewHolder> {


    private List<DeliverGoodsDataBean.ListBean> mDatas;
    private Context mContext;
    private OnclickButton onclickButton;

    public void setData(List<DeliverGoodsDataBean.ListBean> data) {
        mDatas = data;
        resetLodingMore();
        notifyDataSetChanged();
    }

    public DistributionOrderAdapter(List<DeliverGoodsDataBean.ListBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        return new ViewHolder(View.inflate(mContext, R.layout.item_distribution, null));
    }

    @Override
    public List<DeliverGoodsDataBean.ListBean> getDataList() {
        return mDatas;
    }

    @Override
    public void bindOtherViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvOrderSn.setText(mDatas.get(position).getDeliver_id());
        holder.tvOrderTime.setText(mDatas.get(position).getAddtime());
        if (mDatas.get(position).getIs_send() == 0) {
            holder.tvOrderState.setText("待配送");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_service_color));
            holder.llBottom.setVerticalGravity(View.VISIBLE);
        } else {
            holder.tvOrderState.setText("已配送");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_comfirm_color));
            holder.llBottom.setVerticalGravity(View.GONE);
        }
        holder.tvPriceAmount.setText(mDatas.get(position).getTotal_price());
        holder.tvShippingFee.setText(mDatas.get(position).getNum());
        holder.tvGoodsAmount.setText(mDatas.get(position).getGoods_attr_count());
        holder.tvCustomerName.setText(mDatas.get(position).getName());
        holder.tvCustomerPhone.setText(mDatas.get(position).getMobile());
        holder.tvCustomerAddress.setText(mDatas.get(position).getDistrict());
        holder.ivPhoto.load(mDatas.get(position).getGoods_info().getPic());
        holder.tvProductName.setText(mDatas.get(position).getGoods_info().getName());
        holder.tvPrice.setText(mDatas.get(position).getGoods_info().getTotal_price());
        holder.tvAmount.setText(mDatas.get(position).getGoods_info().getNum());
        holder.tvSpecification.setText(mDatas.get(position).getGoods_info().getAttr().get(position).getAttr_name());

        holder.tvCallCustomer.setOnClickListener(v -> {
            if (onclickButton != null) {
                onclickButton.TvCallCustomer(position);
            }
        });
        holder.ivCallCustomer.setOnClickListener(v -> {
            if (onclickButton != null) {
                onclickButton.IvCallCustomer(position);
            }
        });
        holder.tvOk.setOnClickListener(v -> {
            if (onclickButton != null) {
                onclickButton.setDelivery(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnclickButton {
        void TvCallCustomer(int position);

        void IvCallCustomer(int position);

        void setDelivery(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_sn)
        TextView tvOrderSn;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_order_state)
        TextView tvOrderState;
        @BindView(R.id.tv_customer_name)
        TextView tvCustomerName;
        @BindView(R.id.tv_customer_phone)
        TextView tvCustomerPhone;
        @BindView(R.id.tv_customer_address)
        TextView tvCustomerAddress;
        @BindView(R.id.tv_call_customer)
        TextView tvCallCustomer;
        @BindView(R.id.iv_call_customer)
        ImageView ivCallCustomer;
        @BindView(R.id.rl_customer)
        RelativeLayout rlCustomer;
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
        @BindView(R.id.tv_goods_amount)
        TextView tvGoodsAmount;
        @BindView(R.id.tv_shipping_fee)
        TextView tvShippingFee;
        @BindView(R.id.tv_price_amount)
        TextView tvPriceAmount;
        @BindView(R.id.tv_bottom_line)
        TextView tvBottomLine;
        @BindView(R.id.tv_ok)
        TextView tvOk;
        @BindView(R.id.ll_bottom)
        RelativeLayout llBottom;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
