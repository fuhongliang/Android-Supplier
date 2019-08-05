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

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

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

public class OrdersPageAdapter extends UltimateViewAdapter<OrdersPageAdapter.ViewHolder> {

    private String loading = "正在加载中...";
    private boolean isCanLoading = true;


    public interface OnclickButton {
        void modifyPrice(int position);

        void shipping(int position);

    }

    boolean isCancelOrder = false;

    private List<OrdersDataBean.OrderBean> mDatas;
    private Context mContext;
    public OrdersAdapter.OnclickButton onclickButton;

    UltimateRecyclerView recyclerObject;//外部的列表对象

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public OrdersPageAdapter(List<OrdersDataBean.OrderBean> mDatas, Context mContext, OrdersAdapter.OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void setRecyclerObject(UltimateRecyclerView recyclerObject) {
        this.recyclerObject = recyclerObject;
    }

    public void updateData(List<OrdersDataBean.OrderBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 插入新的数据列
     *
     * @param newDatas 需要插入的数据
     */
    public void insert(List<OrdersDataBean.OrderBean> newDatas) {
        insertInternal(mDatas, newDatas);
    }

    /**
     * 设置加载状态
     *
     * @param mCancelLoading
     */
    public void setLoadingState(boolean mCancelLoading) {
        if (mCancelLoading) {
            loading = "没有更多数据啦 >_< ";
            isCanLoading = false;
        } else {
            loading = "正在加载中...";
            isCanLoading = true;
        }
        notifyItemChanged(mDatas.size());
    }

    public boolean getLoadingState() {
        return isCanLoading;
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
    public ViewHolder newFooterHolder(View view) {
        View view11 = LayoutInflater.from(mContext).inflate(R.layout.load_more_layout, recyclerObject, false);
        return new ViewHolder(view11, true);
    }

    @Override
    public ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_new_order, parent, false), false);
        return holder;
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    @Override
    public long generateHeaderId(int position) {
        //生成每个item的头部的id
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < mDatas.size()) {
            OrdersDataBean.OrderBean orderBean = mDatas.get(position);

            if (orderBean.getIs_pay() == 0) {
                holder.mTvOrderState.setText("待付款");
                holder.mTvShip.setVisibility(View.GONE);
                holder.mTvModifyPrice.setVisibility(View.VISIBLE);
                holder.mTvModifyExpressNo.setVisibility(View.GONE);
            } else if (orderBean.getIs_send() == 0) {
                holder.mTvShip.setVisibility(View.VISIBLE);
                holder.mTvModifyPrice.setVisibility(View.GONE);
                holder.mTvModifyExpressNo.setVisibility(View.GONE);
                holder.mTvOrderState.setText("待发货");
            } else if (orderBean.getIs_confirm() == 0) {
                holder.mTvShip.setVisibility(View.GONE);
                holder.mTvModifyPrice.setVisibility(View.GONE);
                holder.mTvModifyExpressNo.setVisibility(View.VISIBLE);
                holder.mTvOrderState.setText("待收货");
            } else {
                holder.mTvOrderState.setText("已完成");
                holder.mTvShip.setVisibility(View.GONE);
                holder.mTvModifyPrice.setVisibility(View.GONE);
                holder.mTvModifyExpressNo.setVisibility(View.GONE);
            }

            if (isCancelOrder) {
                holder.mTvOrderState.setText("已取消");
                holder.mTvShip.setVisibility(View.GONE);
                holder.mTvModifyPrice.setVisibility(View.GONE);
                holder.mTvModifyExpressNo.setVisibility(View.GONE);
                holder.mTvLine.setVisibility(View.GONE);
                holder.mLlBottom.setVisibility(View.GONE);
            }else {
                holder.mTvLine.setVisibility(View.VISIBLE);
                holder.mLlBottom.setVisibility(View.VISIBLE);
            }
            holder.mTvOrderSn.setText("订单编号：" + orderBean.getOrder_no() + "");
            holder.mTvCustomerName.setText(orderBean.getName());
            holder.mTvCustomerPhone.setText(settingphone(orderBean.getMobile() + ""));

            if (!orderBean.getGoodsList().isEmpty() && orderBean.getGoodsList().size()>0){
                OrdersDataBean.OrderBean.GoodsListBean goodsBean = orderBean.getGoodsList().get(0);
                holder.mTvProductName.setText(goodsBean.getName());
                holder.mTvPrice.setText(Constants.unit + goodsBean.getTotal_price());
                holder.mTvAmount.setText("x " + goodsBean.getAttr().size());
                holder.mIvPhoto.load(goodsBean.getPic());
            }

            holder.mTvGoodsAmount.setText("共"+orderBean.getGoodsList().size() +"件商品");
            holder.mTvShippingFee.setText("(含运费"+Constants.unit + orderBean.getExpress_price() + ")");
            holder.mTvPriceAmount.setText(Constants.unit + orderBean.getPay_price() + "");

            holder.mTvModifyPrice.setOnClickListener(v -> onclickButton.modifyPrice(position));
            holder.mTvModifyExpressNo.setOnClickListener(v -> onclickButton.shipping(position));
            holder.mTvShip.setOnClickListener(v -> onclickButton.shipping(position));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("OrderId", orderBean.getOrder_id());
                mContext.startActivity(intent);
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
            holder.mTvCallCustomer.setOnClickListener(v -> DialogUtils.showConfirmDialog("温馨提示", "是否拨打客户电话", "取消", "确定", ((MainActivity) mContext).getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
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

        } else {
            // TODO: 2019-07-02 底部加载item
            holder.footer.setText(loading);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_sn)
        TextView mTvOrderSn;
        @BindView(R.id.tv_order_state)
        TextView mTvOrderState;
        @BindView(R.id.tv_customer_name)
        TextView mTvCustomerName;
        @BindView(R.id.tv_customer_phone)
        TextView mTvCustomerPhone;
        @BindView(R.id.tv_call_customer)
        TextView mTvCallCustomer;
        @BindView(R.id.iv_call_customer)
        ImageView mIvCallCustomer;
        @BindView(R.id.iv_photo)
        GlideImageView mIvPhoto;
        @BindView(R.id.tv_product_name)
        TextView mTvProductName;
        @BindView(R.id.tv_specification)
        TextView mTvSpecification;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_amount)
        TextView mTvAmount;
        @BindView(R.id.tv_goods_amount)
        TextView mTvGoodsAmount;
        @BindView(R.id.tv_shipping_fee)
        TextView mTvShippingFee;
        @BindView(R.id.tv_price_amount)
        TextView mTvPriceAmount;
        @BindView(R.id.tv_modify_express_no)
        TextView mTvModifyExpressNo;
        @BindView(R.id.tv_ship)
        TextView mTvShip;
        @BindView(R.id.tv_modify_price)
        TextView mTvModifyPrice;
        @BindView(R.id.tv_bottom_line)
        TextView mTvLine;
        @BindView(R.id.ll_bottom)
        LinearLayout mLlBottom;

        TextView footer;

        public ViewHolder(View view, boolean isFooter) {
            super(view);
            if (isFooter) {
                footer = view.findViewById(R.id.text_text);
            } else {
                ButterKnife.bind(this, view);
            }
        }
    }
}
