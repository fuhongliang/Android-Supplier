package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.bean.FullCutBean;
import cn.ifhu.supplier.utils.DateUtil;

/**
 * @author fuhongliang
 */
public class FullCutAdapter extends BaseAdapter {

    List<FullCutBean> fullCutBeans;
    Context mContext;
    OnClickItem onClickItem;

    public FullCutAdapter(List<FullCutBean> discountBeanList, Context mContext) {
        this.fullCutBeans = discountBeanList;
        this.mContext = mContext;
    }

    public void setBeanList(List<FullCutBean> discountBeanList) {
        this.fullCutBeans = discountBeanList;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return fullCutBeans == null ? 0 : fullCutBeans.size();
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
            convertView = layoutInflater.inflate(R.layout.item_full_reduction, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(fullCutBeans.get(position).getMansong_name());
        viewHolder.tvTime.setText(DateUtil.getLongToString(fullCutBeans.get(position).getStart_time())+"至"+DateUtil.getLongToString(fullCutBeans.get(position).getEnd_time()));
        if (fullCutBeans.get(position).getState() == 2) {
            viewHolder.ivState.setBackgroundResource(R.drawable.yhq_bnt_xszk_mlj);
            viewHolder.ivStateRight.setVisibility(View.INVISIBLE);
            viewHolder.tvFullCut1.setSelected(true);
            viewHolder.tvFullCut2.setSelected(true);
            viewHolder.tvFullCut3.setSelected(true);
            viewHolder.tvFullCut4.setSelected(true);
        } else {
            viewHolder.ivStateRight.setVisibility(View.VISIBLE);
            viewHolder.ivState.setBackgroundResource(R.drawable.yhq_bnt_xszk_mlj1);
            viewHolder.tvFullCut1.setSelected(false);
            viewHolder.tvFullCut2.setSelected(false);
            viewHolder.tvFullCut3.setSelected(false);
            viewHolder.tvFullCut4.setSelected(false);
        }

        if (onClickItem != null) {
            viewHolder.rlDelete.setOnClickListener(v -> onClickItem.deleteDiscount(position));
        }

        switch (fullCutBeans.get(position).getRule().size()){
            case 1:
                viewHolder.tvFullCut1.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut2.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut3.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut4.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut1.setText("满"+fullCutBeans.get(position).getRule().get(0).getPrice()+"减"+fullCutBeans.get(position).getRule().get(0).getDiscount());
                break;
            case 2:
                viewHolder.tvFullCut1.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut2.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut3.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut4.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut1.setText("满"+fullCutBeans.get(position).getRule().get(0).getPrice()+"减"+fullCutBeans.get(position).getRule().get(0).getDiscount());
                viewHolder.tvFullCut2.setText("满"+fullCutBeans.get(position).getRule().get(1).getPrice()+"减"+fullCutBeans.get(position).getRule().get(1).getDiscount());
                break;
            case 3:
                viewHolder.tvFullCut1.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut2.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut3.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut4.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut1.setText("满"+fullCutBeans.get(position).getRule().get(0).getPrice()+"减"+fullCutBeans.get(position).getRule().get(0).getDiscount());
                viewHolder.tvFullCut2.setText("满"+fullCutBeans.get(position).getRule().get(1).getPrice()+"减"+fullCutBeans.get(position).getRule().get(1).getDiscount());
                viewHolder.tvFullCut3.setText("满"+fullCutBeans.get(position).getRule().get(2).getPrice()+"减"+fullCutBeans.get(position).getRule().get(2).getDiscount());
                break;
            case 4:
                viewHolder.tvFullCut1.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut2.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut3.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut4.setVisibility(View.VISIBLE);
                viewHolder.tvFullCut1.setText("满"+fullCutBeans.get(position).getRule().get(0).getPrice()+"减"+fullCutBeans.get(position).getRule().get(0).getDiscount());
                viewHolder.tvFullCut2.setText("满"+fullCutBeans.get(position).getRule().get(1).getPrice()+"减"+fullCutBeans.get(position).getRule().get(1).getDiscount());
                viewHolder.tvFullCut3.setText("满"+fullCutBeans.get(position).getRule().get(2).getPrice()+"减"+fullCutBeans.get(position).getRule().get(2).getDiscount());
                viewHolder.tvFullCut4.setText("满"+fullCutBeans.get(position).getRule().get(3).getPrice()+"减"+fullCutBeans.get(position).getRule().get(3).getDiscount());
                break;
            default:
                viewHolder.tvFullCut1.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut2.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut3.setVisibility(View.INVISIBLE);
                viewHolder.tvFullCut4.setVisibility(View.INVISIBLE);
                break;
        }
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_full_cut1)
        TextView tvFullCut1;
        @BindView(R.id.tv_full_cut2)
        TextView tvFullCut2;
        @BindView(R.id.tv_full_cut3)
        TextView tvFullCut3;
        @BindView(R.id.tv_full_cut4)
        TextView tvFullCut4;
        @BindView(R.id.iv_state_right)
        ImageView ivStateRight;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_delete)
        RelativeLayout rlDelete;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface OnClickItem {
        void deleteDiscount(int position);
    }
}
