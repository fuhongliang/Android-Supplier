package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.bean.VouCherBean;

/**
 * @author fuhongliang
 */
public class VouCherAdapter extends BaseAdapter {

    List<VouCherBean> vouCherBeanList;
    Context mContext;
    OnClickItem onClickItem;

    public VouCherAdapter(List<VouCherBean> vouCherBeanList, Context mContext) {
        this.vouCherBeanList = vouCherBeanList;
        this.mContext = mContext;
    }

    public void setvouCherBeanList(List<VouCherBean> vouCherBeanList) {
        this.vouCherBeanList = vouCherBeanList;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return vouCherBeanList == null ? 0 : vouCherBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_voucher, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvVoucherName.setText(vouCherBeanList.get(position).getVoucher_title());
        viewHolder.tvLimit.setText("每人限领"+vouCherBeanList.get(position).getVoucher_eachlimit()+"张");
        viewHolder.tvTime.setText(vouCherBeanList.get(position).getVoucher_end_date());

        viewHolder.tvLessNumber.setText(vouCherBeanList.get(position).getVoucher_surplus()+"");
        viewHolder.tvReceived.setText(vouCherBeanList.get(position).getVoucher_giveout()+"");
        viewHolder.tvUseed.setText(vouCherBeanList.get(position).getVoucher_used()+"");
        viewHolder.tvTotalPrice.setText(vouCherBeanList.get(position).getVoucher_limit()+"");

        if (vouCherBeanList.get(position).getVoucher_state() == 1) {
            viewHolder.ivState.setBackgroundResource(R.drawable.yhq_bnt_xszk_djq);
            viewHolder.ivStateRight.setVisibility(View.INVISIBLE);
            viewHolder.llEdit.setVisibility(View.VISIBLE);

            viewHolder.tvLessNumber.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            viewHolder.tvReceived.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            viewHolder.tvUseed.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            viewHolder.tvTotalPrice.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

        } else {
            viewHolder.ivStateRight.setVisibility(View.VISIBLE);
            viewHolder.ivState.setBackgroundResource(R.drawable.yhq_bnt_xszk_djq01);
            viewHolder.llEdit.setVisibility(View.INVISIBLE);

            viewHolder.tvLessNumber.setTextColor(mContext.getResources().getColor(R.color.voucher_normal_color));
            viewHolder.tvReceived.setTextColor(mContext.getResources().getColor(R.color.voucher_normal_color));
            viewHolder.tvUseed.setTextColor(mContext.getResources().getColor(R.color.voucher_normal_color));
            viewHolder.tvTotalPrice.setTextColor(mContext.getResources().getColor(R.color.voucher_normal_color));

        }

        if (onClickItem != null){
            viewHolder.llEdit.setOnClickListener(v -> onClickItem.editDiscount(position));
            viewHolder.llDelete.setOnClickListener(v -> onClickItem.deleteDiscount(position));
        }
        return convertView;
    }


    public interface OnClickItem {
        void editDiscount(int position);

        void deleteDiscount(int position);
    }

    static class ViewHolder {

        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_voucher_name)
        TextView tvVoucherName;
        @BindView(R.id.tv_limit)
        TextView tvLimit;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_state_right)
        ImageView ivStateRight;
        @BindView(R.id.tv_less_number)
        TextView tvLessNumber;
        @BindView(R.id.tv_received)
        TextView tvReceived;
        @BindView(R.id.tv_useed)
        TextView tvUseed;
        @BindView(R.id.tv_total_price)
        TextView tvTotalPrice;
        @BindView(R.id.ll_edit)
        LinearLayout llEdit;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
