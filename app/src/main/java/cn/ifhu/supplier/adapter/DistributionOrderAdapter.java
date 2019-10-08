package cn.ifhu.supplier.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseLoadMoreAdapter;
import cn.ifhu.supplier.model.newbean.data.DeliverGoodsDataBean;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class DistributionOrderAdapter extends BaseLoadMoreAdapter<DeliverGoodsDataBean.ListBean, DistributionOrderAdapter.ViewHolder> {


    private List<DeliverGoodsDataBean.ListBean> mDatas;
    private Context mContext;
    private OnclickButton onclickButton;

    public DistributionOrderAdapter(List<DeliverGoodsDataBean.ListBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void setData(List<DeliverGoodsDataBean.ListBean> data) {
        mDatas = data;
        resetLodingMore();
        notifyDataSetChanged();
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
        holder.tvOrderSn.setText("配货单号: "+mDatas.get(position).getSend_no());
        holder.tvOrderTime.setText("生成时间: " + DateUtil.stampToDate(mDatas.get(position).getAddtime(), " ") + "");
        if (mDatas.get(position).getIs_send() == 0) {
            holder.tvOrderState.setText("待配送");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_service_color));
            holder.llBottom.setVisibility(View.VISIBLE);
        } else {
            holder.tvOrderState.setText("已配送");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_comfirm_color));
            holder.llBottom.setVisibility(View.GONE);
        }
        holder.tvPriceAmount.setText("(合计￥" + mDatas.get(position).getTotal_price() + ")");
        holder.tvShippingFee.setText("总共" + mDatas.get(position).getNum() + "" + "件");
        holder.tvGoodsAmount.setText("共" + mDatas.get(position).getGoods_attr_count() + "" + "类商品");
        holder.tvCustomerName.setText(mDatas.get(position).getName());
        holder.tvCustomerPhone.setText(mDatas.get(position).getMobile());
        holder.tvCustomerAddress.setText("详细地址: " + mDatas.get(position).getDistrict());
        holder.ivPhoto.load(mDatas.get(position).getGoods_info().getPic());
        holder.tvProductName.setText(mDatas.get(position).getGoods_info().getName());
        holder.tvPrice.setText("￥" + mDatas.get(position).getGoods_info().getTotal_price() + "");
        if (mDatas.get(position).getGoods_info().getAttr() != null && mDatas.get(position).getGoods_info().getAttr().size() > 0) {
            holder.tvAmount.setText("x" + mDatas.get(position).getGoods_info().getNum() + "");
            holder.tvSpecification.setText(mDatas.get(position).getGoods_info().getAttr().get(0).getAttr_group_name() + ": ");
            holder.tvDefault.setText(mDatas.get(position).getGoods_info().getAttr().get(0).getAttr_name());
        }



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
        holder.llDistribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickButton != null) {
                    onclickButton.deleteShare(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDatas.size() < PAGESIZE ? mDatas.size() : mDatas.size() + 1;
    }

    public interface OnclickButton {
        void TvCallCustomer(int position);

        void IvCallCustomer(int position);

        void setDelivery(int position);

        void deleteShare(int position);

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
        @BindView(R.id.tv_default)
        TextView tvDefault;
        @BindView(R.id.ll_bottom)
        RelativeLayout llBottom;
        @BindView(R.id.ll_distribution)
        LinearLayout llDistribution;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
