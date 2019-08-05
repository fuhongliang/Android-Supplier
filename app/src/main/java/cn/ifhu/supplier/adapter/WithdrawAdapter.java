package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.bean.WithDrawBean;
import cn.ifhu.supplier.utils.DateUtil;

/**
 * @author fuhongliang
 */
public class WithdrawAdapter extends BaseAdapter {
    public List<WithDrawBean.ListBean> mDataList;
    public Context mContext;
    public int mCurPosition = 0;

    public WithdrawAdapter(List<WithDrawBean.ListBean> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    public void setmDataList(List<WithDrawBean.ListBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_withdraw, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDate.setText(DateUtil.getLongToString(mDataList.get(position).getAdd_time()));
        viewHolder.tvAccountNumbe.setText("转至"+mDataList.get(position).getBank_no()+"账户");
        viewHolder.tvMoney.setText("￥"+mDataList.get(position).getAmount());
        switch (mDataList.get(position).getPayment_state()) {
            case "1":
                viewHolder.tvState.setText("已出账");
                viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.peisongzhong_text_color));
                break;
            case "2":
                viewHolder.tvState.setText("已确认");
                viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.yiwangcheng_text_color));
                break;
            case "3":
                viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.yiwangcheng_text_color));
                viewHolder.tvState.setText("已审核");
                break;
            case "4":
                viewHolder.tvState.setText("已完成");
                viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.yiquxiao_text_color));
                break;
            default:
                viewHolder.tvState.setText("已出账");
                viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.peisongzhong_text_color));
                break;
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_account_numbe)
        TextView tvAccountNumbe;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_state)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
