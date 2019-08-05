package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.bean.FullCutPostBean;

/**
 * @author fuhongliang
 */
public class FullCutRuleAdapter extends BaseAdapter {

    List<FullCutPostBean.RuleBean> ruleBeans;
    Context mContext;
    OnClickItem onClickItem;

    public FullCutRuleAdapter(List<FullCutPostBean.RuleBean> ruleBeans, Context mContext) {
        this.ruleBeans = ruleBeans;
        this.mContext = mContext;
    }

    public void setBeanList(List<FullCutPostBean.RuleBean> ruleBeans) {
        this.ruleBeans = ruleBeans;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return ruleBeans == null ? 0 : ruleBeans.size();
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
            convertView = layoutInflater.inflate(R.layout.item_full_cut_rule, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvPrice.setText(ruleBeans.get(position).getPrice()+"");
        viewHolder.tvDiscount.setText(ruleBeans.get(position).getDiscount()+"");

        if (onClickItem != null) {
            viewHolder.rlDelete.setOnClickListener(v -> onClickItem.deleteItem(position));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_discount)
        TextView tvDiscount;
        @BindView(R.id.rl_delete)
        RelativeLayout rlDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface OnClickItem {
        void deleteItem(int position);
    }

}
