package cn.ifhu.supplier.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseLoadMoreAdapter;
import cn.ifhu.supplier.model.newbean.data.PickListDataBean;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class PickListAdapter extends BaseLoadMoreAdapter<PickListDataBean.PickListBean, PickListAdapter.ViewHolder> {


    private List<PickListDataBean.PickListBean> mDatas;
    private Context mContext;
    private OnclickButton onclickButton;

    public PickListAdapter(List<PickListDataBean.PickListBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    @Override
    public void setData(List<PickListDataBean.PickListBean> data) {
        mDatas = data;
        resetLodingMore();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        return new ViewHolder(View.inflate(mContext, R.layout.item_picking_orders, null));
    }

    @Override
    public List<PickListDataBean.PickListBean> getDataList() {
        return mDatas;
    }

    @Override
    public void bindOtherViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvOrderSn.setText("拣货单号:" + mDatas.get(position).getPick_no());
        holder.tvCreateTime.setText("生成时间:" + DateUtil.stampToDate(mDatas.get(position).getCreate_time(), " ") + "");
        holder.tvGoodsNum.setText("总共:" + mDatas.get(position).getGoods_num() + "" + "件");
        holder.tvGoodsCount.setText("共" + mDatas.get(position).getGoods_count() + "" + "类商品");
        holder.tvTotalPayPrice.setText("(合计￥" + mDatas.get(position).getTotal_pay_price() + ")");
        if (mDatas.get(position).getPick_status() == 0) {
            holder.tvOrderState.setText("待拣货");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_service_color));
            holder.llBottom.setVisibility(View.VISIBLE);
        } else {
            holder.tvOrderState.setText("已拣货");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_comfirm_color));
            holder.llBottom.setVisibility(View.GONE);
        }
        holder.tvProductName.setText(mDatas.get(position).getGoods_info().getName());
        holder.ivPhoto.load(mDatas.get(position).getGoods_info().getPic());
        if (mDatas.get(position).getGoods_info().getAttr() != null && mDatas.get(position).getGoods_info().getAttr().size() > 0) {
            holder.tvSpecification.setText(mDatas.get(position).getGoods_info().getAttr().get(0).getAttr_group_name() + ":");
            holder.tvDefault.setText(mDatas.get(position).getGoods_info().getAttr().get(0).getAttr_name());
            holder.tvPrice.setText("￥" + mDatas.get(position).getGoods_info().getTotal_price());
            holder.tvAmount.setText("x" + mDatas.get(position).getGoods_info().getNum() + "");
        }
        holder.ok.setOnClickListener(v -> {
            if (onclickButton != null) {
                onclickButton.finishPicking(position);
            }
        });
        holder.llPicking.setOnClickListener(v -> {
            if (onclickButton != null) {
                onclickButton.pickingDetails(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDatas.size() < PAGESIZE ? mDatas.size() : mDatas.size() + 1;
    }


    public interface OnclickButton {
        void finishPicking(int position);

        void pickingDetails(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_sn)
        TextView tvOrderSn;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_order_state)
        TextView tvOrderState;
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
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.tv_goods_num)
        TextView tvGoodsNum;
        @BindView(R.id.tv_total_pay_price)
        TextView tvTotalPayPrice;
        @BindView(R.id.tv_bottom_line)
        TextView tvBottomLine;
        @BindView(R.id.ll_bottom)
        RelativeLayout llBottom;
        @BindView(R.id.ok)
        TextView ok;
        @BindView(R.id.ll_picking)
        LinearLayout llPicking;
        @BindView(R.id.tv_default)
        TextView tvDefault;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
