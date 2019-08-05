package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idlestar.ratingstar.RatingStarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.bean.ReviewBean;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * @author fuhongliang
 */
public class AllReviewsAdapter extends RecyclerView.Adapter<AllReviewsAdapter.MyViewHolder> {


    private ReviewBean reviewBean;
    private Context mContext;
    public OnclickButton onclickButton;

    public AllReviewsAdapter(ReviewBean reviewBean, Context mContext, OnclickButton onclickButton) {
        this.reviewBean = reviewBean;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void updateData(ReviewBean reviewBean) {
        this.reviewBean = reviewBean;
        notifyDataSetChanged();
    }

    public ReviewBean getReviewBean() {
        return reviewBean;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_review, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReviewBean.ComListBean comListBean = reviewBean.getCom_list().get(position);

        holder.ivUserHeader.load(comListBean.getMember_avatar(),R.drawable.moren_yonghu);

        holder.tvUserName.setText(StringUtils.isEmpty(comListBean.getMember_name())?"匿名":comListBean.getMember_name());
        holder.tvDate.setText(comListBean.getAdd_time());
        holder.rsv_rating.setRating(comListBean.getHaoping());
        holder.tvKouwei.setText("口味"+comListBean.getKouwei() +"星");
        holder.tvBaozhuan.setText("包装"+comListBean.getBaozhuang() +"星");
        holder.tvPeisong.setText("配送"+comListBean.getPeisong() +"星");
        holder.tvReviewContent.setText(comListBean.getContent());
        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickButton.reply(position);
            }
        });
        if (StringUtils.isEmpty(comListBean.getReplay())){
            holder.rlReply.setVisibility(View.GONE);
            holder.tvReply.setVisibility(View.VISIBLE);
        }else {
            holder.tvReply.setVisibility(View.GONE);
            holder.rlReply.setVisibility(View.VISIBLE);
            holder.tvReplyContent.setText(Html.fromHtml(String.format(mContext.getString(R.string.shop_reply), comListBean.getReplay())));
        }

    }

    @Override
    public int getItemCount() {
        return reviewBean.getCom_list() == null?0:reviewBean.getCom_list().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_header)
        GlideImageView ivUserHeader;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_kouwei)
        TextView tvKouwei;
        @BindView(R.id.tv_baozhuan)
        TextView tvBaozhuan;
        @BindView(R.id.tv_peisong)
        TextView tvPeisong;
        @BindView(R.id.tv_review_content)
        TextView tvReviewContent;
        @BindView(R.id.tv_reply_content)
        TextView tvReplyContent;
        @BindView(R.id.rl_reply)
        RelativeLayout rlReply;
        @BindView(R.id.rsv_rating)
        RatingStarView rsv_rating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnclickButton {
        void reply(int position);
    }
}