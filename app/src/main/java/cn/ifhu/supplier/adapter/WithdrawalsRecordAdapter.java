package cn.ifhu.supplier.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.newbean.data.WithdrawalsRecordDataBean;
import cn.ifhu.supplier.utils.DateUtil;

public class WithdrawalsRecordAdapter extends UltimateViewAdapter<WithdrawalsRecordAdapter.ViewHolder> {


    private String loading = "正在加载中...";
    private boolean isCanLoading = true;
    boolean isCancelOrder = false;

    private List<WithdrawalsRecordDataBean.CashBean> mDatas;
    private Context mContext;

    UltimateRecyclerView recyclerObject;//外部的列表对象

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public WithdrawalsRecordAdapter(List<WithdrawalsRecordDataBean.CashBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;

    }


    public void setRecyclerObject(UltimateRecyclerView recyclerObject) {
        this.recyclerObject = recyclerObject;
    }

    public void updateData(List<WithdrawalsRecordDataBean.CashBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 插入新的数据列
     *
     * @param newDatas 需要插入的数据
     */
    public void insert(List<WithdrawalsRecordDataBean.CashBean> newDatas) {
        insertInternal(mDatas, newDatas);
    }

    /**
     * 设置加载状态
     *
     * @param mCancelLoading
     */
    public void setLoadingState(boolean mCancelLoading) {
        if (mCancelLoading) {
            loading = "";
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
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_withdrawals_record, recyclerObject, false), false);
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
        if (mDatas.size() == position) return;
        holder.tvAddTime.setText(DateUtil.stampToDate(mDatas.get(position).getAddtime()," "));
        holder.tvMoney.setText(mDatas.get(position).getMoney());
        if (mDatas.get(position).getStatus() == 0) {
            holder.tvStatus.setText("待审核");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (mDatas.get(position).getStatus() == 1) {
            holder.tvStatus.setText("已通过");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.hint_text_color));
        } else {
            holder.tvStatus.setText("未通过");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.order_not_pay_color));
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView footer;

        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_add_time)
        TextView tvAddTime;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        public ViewHolder(@NonNull View itemView, boolean isFooter) {
            super(itemView);
            if (isFooter) {
                footer = itemView.findViewById(R.id.text_text);
                footer.setText(loading);
            } else {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
