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
import cn.ifhu.supplier.model.bean.DiscountBean;
import cn.ifhu.supplier.utils.DateUtil;

/**
 * @author fuhongliang
 */
public class DiscountAdapter extends BaseAdapter {

    List<DiscountBean> discountBeanList;
    Context mContext;
    OnClickItem onClickItem;

    public DiscountAdapter(List<DiscountBean> discountBeanList, Context mContext) {
        this.discountBeanList = discountBeanList;
        this.mContext = mContext;
    }

    public void setDiscountBeanList(List<DiscountBean> discountBeanList) {
        this.discountBeanList = discountBeanList;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return discountBeanList == null ? 0 : discountBeanList.size();
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
            convertView = layoutInflater.inflate(R.layout.item_limit_discounts, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDiscountName.setText(discountBeanList.get(position).getXianshi_name());
        viewHolder.tvAtLess.setText("购买下限：" + discountBeanList.get(position).getLower_limit());
        viewHolder.tvTime.setText(DateUtil.getLongToString(discountBeanList.get(position).getStart_time()) + "-" + DateUtil.getLongToString(discountBeanList.get(position).getEnd_time()));
        if (discountBeanList.get(position).getState() == 1) {
            viewHolder.ivDiscount.setBackgroundResource(R.drawable.yhq_bnt_xszk);
            viewHolder.ivState.setVisibility(View.INVISIBLE);
            viewHolder.llEdit.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivState.setVisibility(View.VISIBLE);
            viewHolder.ivDiscount.setBackgroundResource(R.drawable.yhq_bnt_xszk01);
            viewHolder.llEdit.setVisibility(View.INVISIBLE);
        }
        if (onClickItem != null){
            viewHolder.llEdit.setOnClickListener(v -> onClickItem.editDiscount(position));
            viewHolder.llDelete.setOnClickListener(v -> onClickItem.deleteDiscount(position));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_discount)
        ImageView ivDiscount;
        @BindView(R.id.tv_discount_name)
        TextView tvDiscountName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_at_less)
        TextView tvAtLess;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.ll_edit)
        LinearLayout llEdit;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface OnClickItem {
        void editDiscount(int position);

        void deleteDiscount(int position);
    }

}
