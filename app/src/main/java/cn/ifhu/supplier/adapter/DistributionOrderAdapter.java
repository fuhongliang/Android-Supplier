package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import cn.ifhu.supplier.model.newbean.data.PickListDataBean;

//public class DistributionOrderAdapter extends UltimateViewAdapter<DistributionOrderAdapter.ViewHolder> {
//    public interface OnclickButton {
//        void finishPicking(int position);
//    }
//
//    private List<PickListDataBean.PickListBean> mDatas;
//    private Context mContext;
//    private DistributionOrderAdapter.OnclickButton onclickButton;
//
//    UltimateRecyclerView recyclerObject;//外部的列表对象
//
//    public DistributionOrderAdapter(List<PickListDataBean.PickListBean> mDatas, Context mContext, DistributionOrderAdapter.OnclickButton onclickButton) {
//        this.mDatas = mDatas;
//        this.mContext = mContext;
//        this.onclickButton = onclickButton;
//    }
//
//    public void setRecyclerObject(UltimateRecyclerView recyclerObject) {
//        this.recyclerObject = recyclerObject;
//    }
//
//    public void updateData(List<PickListDataBean.PickListBean> mDatas) {
//        this.mDatas = mDatas;
//        notifyDataSetChanged();
//    }
//
//    /**
//     * 插入新的数据列
//     *
//     * @param newDatas 需要插入的数据
//     */
//    public void insert(List<PickListDataBean.PickListBean> newDatas) {
//        insertInternal(mDatas, newDatas);
//    }
//
//    @Override
//    public ViewHolder newFooterHolder(View view) {
//        return null;
//    }
//
//    @Override
//    public ViewHolder newHeaderHolder(View view) {
//        return null;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    public int getAdapterItemCount() {
//        return 0;
//    }
//
//    @Override
//    public long generateHeaderId(int position) {
//        return 0;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}
