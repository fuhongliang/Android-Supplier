package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.MainActivity;
import cn.ifhu.supplier.activity.orders.OrderDetailActivity;
import cn.ifhu.supplier.model.newbean.data.OrdersDataBean;
import cn.ifhu.supplier.utils.Constants;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

/**
 * @author fuhongliang
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    public interface OnclickButton {
        void modifyPrice(int position);

        void shipping(int position);

    }

    boolean isCancelOrder = false;

    private List<OrdersDataBean.OrderBean> mDatas;
    private Context mContext;
    public OnclickButton onclickButton;

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public OrdersAdapter(List<OrdersDataBean.OrderBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void updateData(List<OrdersDataBean.OrderBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_order, viewGroup,
                false));
        return holder;
    }

    //电话中间4位数为星星
    public String settingphone(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber) || phoneNumber.length() < 4) {
            return "";
        } else {
            return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrdersDataBean.OrderBean orderBean = mDatas.get(position);
        if (orderBean.getIs_pay() == 0){
            holder.mTvOrderState.setText("待付款");
            holder.mTvShip.setVisibility(View.GONE);
            holder.mTvModifyPrice.setVisibility(View.VISIBLE);
            holder.mTvModifyExpressNo.setVisibility(View.GONE);
            holder.mTvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_not_pay_color));

        }else if (orderBean.getIs_send() == 0){
            holder.mTvShip.setVisibility(View.VISIBLE);
            holder.mTvModifyPrice.setVisibility(View.GONE);
            holder.mTvModifyExpressNo.setVisibility(View.GONE);
            holder.mTvOrderState.setText("待发货");
            holder.mTvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_before_comfirm_color));
        }else if (orderBean.getIs_confirm() == 0){
            holder.mTvShip.setVisibility(View.GONE);
            holder.mTvModifyPrice.setVisibility(View.GONE);
            holder.mTvModifyExpressNo.setVisibility(View.VISIBLE);
            holder.mTvOrderState.setText("待收货");
            holder.mTvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_before_comfirm_color));
        }else {
            holder.mTvOrderState.setText("已完成");
            holder.mTvShip.setVisibility(View.GONE);
            holder.mTvModifyPrice.setVisibility(View.GONE);
            holder.mTvModifyExpressNo.setVisibility(View.GONE);
            holder.mTvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_comfirm_color));
            holder.mLlBottomBtn.setVisibility(View.GONE);
        }

        if (isCancelOrder){
            holder.mTvOrderState.setText("已取消");
            holder.mTvShip.setVisibility(View.GONE);
            holder.mTvModifyPrice.setVisibility(View.GONE);
            holder.mTvModifyExpressNo.setVisibility(View.GONE);
            holder.mTvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_cancel_color));
            holder.mLlBottomBtn.setVisibility(View.GONE);
        }


        holder.mTvCustomerName.setText(orderBean.getName());
        holder.mTvCustomerPhone.setText(settingphone(orderBean.getMobile() + ""));
        holder.mTvCustomerAdd.setText(orderBean.getAddress());
        holder.mLlContent.removeAllViews();
        if (orderBean.isExpendOrder()) {
            for (OrdersDataBean.OrderBean.GoodsListBean extendOrderGoodsBean : orderBean.getGoodsList()) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_product, null);
                TextView mProductName = view.findViewById(R.id.tv_product_name);
                TextView mPrice = view.findViewById(R.id.tv_price);
                TextView mAmount = view.findViewById(R.id.tv_amount);
                GlideImageView mPic = view.findViewById(R.id.iv_photo);
                mProductName.setText(extendOrderGoodsBean.getName());
                mPrice.setText(Constants.unit + extendOrderGoodsBean.getTotal_price());
                mAmount.setText("x " + 1);
                mPic.load(extendOrderGoodsBean.getPic());
                holder.mLlContent.addView(view);
            }
            holder.mTvExpend.setText("收起");
            holder.mIvIcZhankai.setBackgroundResource(R.drawable.icon_shouqi);
        } else {
            if (orderBean.getGoodsList() != null && orderBean.getGoodsList().size() > 0) {
                OrdersDataBean.OrderBean.GoodsListBean extendOrderGoodsBean = orderBean.getGoodsList().get(0);
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_product, null);
                TextView mProductName = view.findViewById(R.id.tv_product_name);
                TextView mPrice = view.findViewById(R.id.tv_price);
                TextView mAmount = view.findViewById(R.id.tv_amount);
                GlideImageView mPic = view.findViewById(R.id.iv_photo);
                mProductName.setText(extendOrderGoodsBean.getName());
                mPrice.setText(Constants.unit + extendOrderGoodsBean.getTotal_price());
                mAmount.setText("x " + 1);
                mPic.load(extendOrderGoodsBean.getPic());
                holder.mLlContent.addView(view);
            }
            holder.mTvExpend.setText("展开");
            holder.mIvIcZhankai.setBackgroundResource(R.drawable.icon_zhankai);
        }
        holder.mTvOrderSn.setText("订单编号：" + orderBean.getOrder_no() + "");
        holder.mTvOrderTime.setText("下单时间：" + orderBean.getAddtime() + "");
        holder.mTvRemarksInformation.setText(orderBean.getRemark());
        holder.mTvShippingFee.setText(Constants.unit + orderBean.getExpress_price() + "");
        holder.mTvAmount.setText(Constants.unit + orderBean.getPay_price() + "");
        holder.mTvModifyPrice.setOnClickListener(v -> onclickButton.modifyPrice(position));
        holder.mTvModifyExpressNo.setOnClickListener(v -> onclickButton.shipping(position));
        holder.mTvShip.setOnClickListener(v -> onclickButton.shipping(position));
        if (orderBean.getGoodsList() != null && orderBean.getGoodsList().size() > 1) {
            holder.mTvExpend.setVisibility(View.VISIBLE);
            holder.mIvIcZhankai.setVisibility(View.VISIBLE);
        } else {
            holder.mTvExpend.setVisibility(View.INVISIBLE);
            holder.mIvIcZhankai.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, OrderDetailActivity.class);
            intent.putExtra("OrderId",orderBean.getOrder_id());
            mContext.startActivity(intent);
        });

        holder.mIvIcZhankai.setOnClickListener(v -> {
            mDatas.get(position).setExpendOrder(!mDatas.get(position).isExpendOrder());
            notifyDataSetChanged();
        });

        holder.mTvExpend.setOnClickListener(v -> {
            mDatas.get(position).setExpendOrder(!mDatas.get(position).isExpendOrder());
            notifyDataSetChanged();
        });

        holder.mIvCallCustomer.setOnClickListener(v -> DialogUtils.showConfirmDialog("温馨提示", "是否拨打客户电话", "取消", "确定", ((MainActivity) mContext).getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + orderBean.getMobile());
                intent.setData(data);
                mContext.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_state)
        TextView mTvOrderState;
        @BindView(R.id.icon_shou)
        ImageView mIconShou;
        @BindView(R.id.tv_customer_name)
        TextView mTvCustomerName;
        @BindView(R.id.tv_customer_phone)
        TextView mTvCustomerPhone;
        @BindView(R.id.iv_call_customer)
        ImageView mIvCallCustomer;
        @BindView(R.id.icon_weizhi)
        ImageView mIconWeizhi;
        @BindView(R.id.tv_customer_add)
        TextView mTvCustomerAdd;
        @BindView(R.id.ll_content)
        LinearLayout mLlContent;
        @BindView(R.id.ll_order_detail)
        LinearLayout mLlOrderDetail;
        @BindView(R.id.ll_bottom_btn)
        LinearLayout mLlBottomBtn;

        @BindView(R.id.tv_order_sn)
        TextView mTvOrderSn;
        @BindView(R.id.tv_order_time)
        TextView mTvOrderTime;
        @BindView(R.id.tv_remarks)
        TextView mTvRemarks;
        @BindView(R.id.tv_remarks_information)
        TextView mTvRemarksInformation;
        @BindView(R.id.tv_shipping_fee)
        TextView mTvShippingFee;
        @BindView(R.id.tv_amount)
        TextView mTvAmount;
        @BindView(R.id.tv_modify_price)
        TextView mTvModifyPrice;
        @BindView(R.id.tv_ship)
        TextView mTvShip;
        @BindView(R.id.tv_modify_express_no)
        TextView mTvModifyExpressNo;

        @BindView(R.id.tv_expend)
        TextView mTvExpend;
        @BindView(R.id.iv_ic_zhankai)
        ImageView mIvIcZhankai;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}