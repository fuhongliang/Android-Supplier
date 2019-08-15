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
import cn.ifhu.supplier.base.BaseLoadMoreAdapter;
import cn.ifhu.supplier.model.newbean.data.AllEvaluationDataBean;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.DeviceUtil;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * 评价适配器
 */
public class AllEvaluationAdapter extends BaseLoadMoreAdapter<AllEvaluationDataBean.CommentBean, AllEvaluationAdapter.ViewHolder> {


    private List<AllEvaluationDataBean.CommentBean> mDatas;
    private Context mContext;
    public OnclickButton onclickButton;

    public AllEvaluationAdapter(List<AllEvaluationDataBean.CommentBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        return new ViewHolder(View.inflate(mContext, R.layout.item_evaluation, null));
    }

    @Override
    public List<AllEvaluationDataBean.CommentBean> getDataList() {
        return mDatas;
    }

    @Override
    public void bindOtherViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == mDatas.size()) return;
        if (position < mDatas.size()) {
            holder.ivAvatar.loadCircle(mDatas.get(position).getAvatar());
            holder.tvName.setText(mDatas.get(position).getName());
            holder.addtime.setText(DateUtil.stampToDate(mDatas.get(position).getAddtime(), " "));
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
                    View categoryView = LayoutInflater.from(mContext).inflate(R.layout.review_image_item, null, false);
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
            holder.tvBtnHide.setText(mDatas.get(position).getIs_hide() == 1 ? "显示" : "隐藏");
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
        }
    }

    @Override
    public void setData(List<AllEvaluationDataBean.CommentBean> data) {
        mDatas = data;
        resetLodingMore();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnclickButton {
        void delete(int position);

        void hide(int position);

        void reply(int position);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
