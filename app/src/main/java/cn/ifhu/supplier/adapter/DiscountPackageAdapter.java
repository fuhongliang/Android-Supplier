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
import cn.ifhu.supplier.model.bean.DiscountPackageBean;

/**
 * @author fuhongliang
 */
public class DiscountPackageAdapter extends BaseAdapter {

    List<DiscountPackageBean> discountPackageBeans;
    Context mContext;
    OnClickItem onClickItem;

    public DiscountPackageAdapter(List<DiscountPackageBean> discountPackageBeans, Context mContext) {
        this.discountPackageBeans = discountPackageBeans;
        this.mContext = mContext;
    }

    public void setBeanList(List<DiscountPackageBean> discountPackageBeans) {
        this.discountPackageBeans = discountPackageBeans;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return discountPackageBeans == null ? 0 : discountPackageBeans.size();
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
            convertView = layoutInflater.inflate(R.layout.item_package, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(discountPackageBeans.get(position).getBl_name());
        viewHolder.tvPrice.setText("总优惠价格：￥" + discountPackageBeans.get(position).getPrice());

        if (discountPackageBeans.get(position).getBl_state() == 1) {
            viewHolder.ivState.setBackgroundResource(R.drawable.yhq_bnt_xszk_tc);
            viewHolder.ivStateRight.setVisibility(View.GONE);
            viewHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            viewHolder.ivState.setBackgroundResource(R.drawable.yhq_bnt_xszk_tc01);
            viewHolder.ivStateRight.setVisibility(View.VISIBLE);
            viewHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.second_grey));
        }

        if (onClickItem != null) {
            viewHolder.llDelete.setOnClickListener(v -> onClickItem.deleteDiscountPackage(position));
            viewHolder.llEdit.setOnClickListener(v -> onClickItem.editDiscountPackage(position));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ll_edit)
        LinearLayout llEdit;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.iv_state_right)
        ImageView ivStateRight;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface OnClickItem {
        void editDiscountPackage(int position);

        void deleteDiscountPackage(int position);
    }
}
