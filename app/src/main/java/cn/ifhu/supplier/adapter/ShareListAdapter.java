package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseLoadMoreAdapter;
import cn.ifhu.supplier.model.newbean.data.ShareListDataBean;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class ShareListAdapter extends BaseLoadMoreAdapter<ShareListDataBean.ListBean, ShareListAdapter.ViewHolder> {


    private List<ShareListDataBean.ListBean> mDatas;
    private Context mContext;
    private OnclickButton onclickButton;
    private int index;


    @Override
    public void setData(List<ShareListDataBean.ListBean> data) {
        mDatas = data;
        resetLodingMore();
        notifyDataSetChanged();
    }

    public ShareListAdapter(List<ShareListDataBean.ListBean> mDatas, Context mContext, OnclickButton onclickButton,int index) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
        this.index = index;

    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        return new ViewHolder(View.inflate(mContext, R.layout.item_share, null));
    }

    @Override
    public List<ShareListDataBean.ListBean> getDataList() {
        return mDatas;
    }

    @Override
    public void bindOtherViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivAvatar.load(mDatas.get(position).getAvatar_url());
        holder.tvName.setText(mDatas.get(position).getName());
        holder.tvAddress.setText(mDatas.get(position).getAddress());
        if (index == 0){
            holder.ok.setText("添加");
            holder.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclickButton != null){
                        onclickButton.addShare(position);
                    }
                }
            });
        }else {
            holder.ok.setText("删除");
            holder.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclickButton != null){
                        onclickButton.deleteShare(position);
                    }
                }
            });
        }
//        holder.ok.setOnClickListener(v -> {
//            if (onclickButton != null) {
//                if (index == 0){
//                    holder.ok.setText("添加");
//                    onclickButton.addShare(position);
//                }else {
//                    holder.ok.setText("删除");
//                    onclickButton.deleteShare(position);
//                }
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size() < PAGESIZE ? mDatas.size() : mDatas.size() + 1;
    }

    public interface OnclickButton {
        void addShare(int position);

        void deleteShare(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        GlideImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.ok)
        TextView ok;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
