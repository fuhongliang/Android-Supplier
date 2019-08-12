package cn.ifhu.supplier.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.newbean.data.PickListDataBean;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class PickListAdapter extends UltimateViewAdapter<PickListAdapter.ViewHolder> {




    public interface OnclickButton {
        void finishPicking(int position);

        void pickingDetails(int position);
    }

    private List<PickListDataBean.PickListBean> mDatas;
    private Context mContext;
    private OnclickButton onclickButton;

    UltimateRecyclerView recyclerObject;//外部的列表对象

    public PickListAdapter(List<PickListDataBean.PickListBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void setRecyclerObject(UltimateRecyclerView recyclerObject) {
        this.recyclerObject = recyclerObject;
    }

    public void updateData(List<PickListDataBean.PickListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 插入新的数据列
     *
     * @param newDatas 需要插入的数据
     */
    public void insert(List<PickListDataBean.PickListBean> newDatas) {
        insertInternal(mDatas, newDatas);
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
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_picking_orders, recyclerObject, false), false);
        return holder;
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == mDatas.size()) return;
        holder.tvOrderSn.setText(mDatas.get(position).getHuodan_id());
        holder.tvCreateTime.setText(mDatas.get(position).getCreate_time());
        holder.tvGoodsNum.setText(mDatas.get(position).getGoods_num());
        holder.tvGoodsCount.setText(mDatas.get(position).getGoods_count());
        holder.tvTotalPayPrice.setText(mDatas.get(position).getTotal_pay_price());
        if (mDatas.get(position).getPick_status() == 0) {
            holder.tvOrderState.setText("待拣货");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_service_color));
            holder.llBottom.setVerticalGravity(View.VISIBLE);
        } else {
            holder.tvOrderState.setText("已拣货");
            holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.order_comfirm_color));
            holder.llBottom.setVerticalGravity(View.GONE);
        }
        holder.tvProductName.setText(mDatas.get(position).getGoods_info().getName());
        holder.ivPhoto.load(mDatas.get(position).getGoods_info().getPic());
        holder.tvSpecification.setText(mDatas.get(position).getGoods_info().getAttr().get(position).getAttr_name());
        holder.tvPrice.setText(mDatas.get(position).getGoods_info().getTotal_price());
        holder.tvAmount.setText(mDatas.get(position).getGoods_info().getNum());
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
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static

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
        TextView footer;

        public ViewHolder(@NonNull View itemView, boolean isFooter) {
            super(itemView);
            if (isFooter) {
                footer = itemView.findViewById(R.id.text_text);
            } else {
                ButterKnife.bind(this, itemView);
            }
//            ButterKnife.bind(itemView);
        }

    }

}
