package cn.ifhu.supplier.adapter;

import android.content.Context;
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
import cn.ifhu.supplier.model.newbean.data.AllEvaluationDataBean;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.DeviceUtil;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * 评价适配器
 */
public class AllEvaluationAdapter extends UltimateViewAdapter<AllEvaluationAdapter.ViewHolder> {



    private String loading = "正在加载中...";
    private boolean isCanLoading = true;


    public interface OnclickButton {
        void delete(int position);

        void hide(int position);

        void reply(int position);

    }

    boolean isCancelOrder = false;

    private List<AllEvaluationDataBean.CommentBean> mDatas;
    private Context mContext;
    public OnclickButton onclickButton;

    UltimateRecyclerView recyclerObject;//外部的列表对象

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public AllEvaluationAdapter(List<AllEvaluationDataBean.CommentBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void setRecyclerObject(UltimateRecyclerView recyclerObject) {
        this.recyclerObject = recyclerObject;
    }

    public void updateData(List<AllEvaluationDataBean.CommentBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 插入新的数据列
     *
     * @param newDatas 需要插入的数据
     */
    public void insert(List<AllEvaluationDataBean.CommentBean> newDatas) {
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
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_evaluation, recyclerObject, false), false);
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

        if (position == mDatas.size()) return;
        if (position < mDatas.size()) {
            holder.ivAvatar.loadCircle(mDatas.get(position).getAvatar());
            holder.tvName.setText(mDatas.get(position).getName());
            holder.addtime.setText(DateUtil.stampToDate(mDatas.get(position).getAddtime()," "));
            if (StringUtils.isEmpty(mDatas.get(position).getContent())) {
                holder.tvContent.setVisibility(View.GONE);
            } else {
                holder.tvContent.setVisibility(View.VISIBLE);
            }
            if (mDatas.get(position).getScore().equals("1.0")) {
                holder.tvGoodReview.setText("差评");
                holder.ivScore.setBackgroundResource(R.drawable.pj_ic_cp);
            } else if (mDatas.get(position).getScore().equals("2.0")) {
                holder.tvGoodReview.setText("中评");
                holder.ivScore.setBackgroundResource(R.drawable.pj_ic_zp);
            } else {
                holder.tvGoodReview.setText("好评");
                holder.ivScore.setBackgroundResource(R.drawable.pj_ic_hp);
            }

            if (StringUtils.isEmpty(mDatas.get(position).getReply_content())) {
                holder.tvReplyContent.setVisibility(View.GONE);
                holder.tvLine.setVisibility(View.GONE);
            } else {
                holder.tvReplyContent.setVisibility(View.VISIBLE);
                holder.tvReplyContent.setText("商家回复：\n" + mDatas.get(position).getReply_content());
                holder.tvLine.setVisibility(View.VISIBLE);
            }

            if (mDatas.get(position).getPic_list().size() <= 0) {
                holder.llPicList.setVerticalGravity(View.GONE);
            } else {
                holder.llPicList.setVerticalGravity(View.VISIBLE);
                holder.llPicList.removeAllViews();
                for (int i = 0; i < mDatas.get(position).getPic_list().size(); i++) {
                    View categoryView = LayoutInflater.from(mContext).inflate(R.layout.review_image_item, recyclerObject, false);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceUtil.dip2px(105), DeviceUtil.dip2px(90));
                    params.leftMargin = DeviceUtil.dip2px(15);
                    GlideImageView imageView = categoryView.findViewById(R.id.iv_image);
                    categoryView.setLayoutParams(params);
                    imageView.load(mDatas.get(position).getPic_list().get(i));
                    holder.llPicList.addView(categoryView);
                }
            }


            holder.tvBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclickButton != null) {
                        onclickButton.delete(position);
                    }
                }
            });
            holder.tvBtnHide.setText(mDatas.get(position).getIs_hide()==1 ? "显示":"隐藏");
            holder.tvBtnHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclickButton != null) {
                        onclickButton.hide(position);
                    }
                }
            });
            holder.tvBtnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclickButton != null) {
                        onclickButton.reply(position);
                    }
                }
            });
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        GlideImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.addtime)
        TextView addtime;
        @BindView(R.id.tv_good_review)
        TextView tvGoodReview;
        @BindView(R.id.tv_reply_content)
        TextView tvReplyContent;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.ll_pic_list)
        LinearLayout llPicList;
        @BindView(R.id.tv_btn_delete)
        TextView tvBtnDelete;
        @BindView(R.id.tv_btn_hide)
        TextView tvBtnHide;
        @BindView(R.id.tv_btn_reply)
        TextView tvBtnReply;
        @BindView(R.id.iv_score)
        ImageView ivScore;
        @BindView(R.id.tv_line)
        TextView tvLine;
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
