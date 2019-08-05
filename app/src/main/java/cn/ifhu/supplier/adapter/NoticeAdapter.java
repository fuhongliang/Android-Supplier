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
import cn.ifhu.supplier.model.bean.NoticeBean;
import cn.ifhu.supplier.utils.DateUtil;

/**
 * @author fuhongliang
 */
public class NoticeAdapter extends BaseAdapter {

    List<NoticeBean.MsgBean> noticeBeanList;
    Context mContext;

    public NoticeAdapter(List<NoticeBean.MsgBean> noticeBeanList, Context mContext) {
        this.noticeBeanList = noticeBeanList;
        this.mContext = mContext;
    }

    public void setvouCherBeanList(List<NoticeBean.MsgBean> noticeBeanList) {
        this.noticeBeanList = noticeBeanList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return noticeBeanList == null ? 0 : noticeBeanList.size();
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
            convertView = layoutInflater.inflate(R.layout.item_system_notification, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNoticeContent.setText(noticeBeanList.get(position).getMsg_content());
        viewHolder.tvNoticeStatus.setText(noticeBeanList.get(position).getMsg_type());
        viewHolder.tvNoticeTime.setText(DateUtil.getLongToStringMinute(noticeBeanList.get(position).getAddtime()));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_notice_status)
        TextView tvNoticeStatus;
        @BindView(R.id.tv_notice_time)
        TextView tvNoticeTime;
        @BindView(R.id.tv_notice_content)
        TextView tvNoticeContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
