package cn.ifhu.supplier.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ifhu.supplier.R;

public abstract class BaseLoadMoreAdapter<T,VH> extends RecyclerView.Adapter {

    private static final int TYPE_OTHER = 0;
    private static final int TYPE_FOOTER = 1;

    private int loadState;
    private final int STATE_LOADING = 1;
    private final int STATE_LASTED = 2;
    private final int STATE_ERROR = 3;

    private LoadMoreListenter loadMoreListenter;

    private boolean hasMore = true;

    private int loadIndex = 2;
    public static final int PAGESIZE = 10;

    /**
     * @return 获取正常的item的ViewHolder
     */
    public abstract RecyclerView.ViewHolder getViewHolder();

    /**
     * @return 获取当前的列表数据
     */
    public abstract List<T> getDataList();

    /**
     * 绑定普通的ViewHolder
     *
     * @param holder   ViewHolder
     * @param position 索引
     */
    public abstract void bindOtherViewHolder(@NonNull VH holder, int position);

    public abstract void setData(List<T> data);

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setLoadMordListener(LoadMoreListenter loadMordListener) {
        this.loadMoreListenter = loadMordListener;
    }


    /**
     * 在后面加添加数据
     *
     * @param data 新加进来的数据
     */
    public final void appendList(List<T> data) {
        int positionStart = getDataList().size();
        getDataList().addAll(data);
        int itemCount = getDataList().size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(positionStart + 1, itemCount);
            loadIndex++;
            if (itemCount < PAGESIZE) {
                loadState = STATE_LASTED;
            }
        }
    }
    /**
     * 在后面加添加数据
     *
     * @param data 新加进来的数据
     */
    public final void appendData(T data) {
        int positionStart = getDataList().size();
        getDataList().add(data);
        int itemCount = getDataList().size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(positionStart + 1, itemCount);
            loadIndex++;
            if (itemCount < PAGESIZE) {
                loadState = STATE_LASTED;
            }
        }
    }

    public final void setLoadEorro() {
        loadState = STATE_ERROR;

        notifyItemRangeChanged(getDataList().size(), 1);
    }

    /**
     * 设置当前的加载状态 并刷新最后一个item
     */
    public final void loadingMore() {
        if (loadState == STATE_LOADING) {
            return;
        }
        loadState = STATE_LOADING;
        notifyItemRangeChanged(getDataList().size(), 1);

    }

    public final void resetLodingMore() {
        loadState = 0;
        loadIndex = 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getDataList().size() != 0 && getDataList().size() <= position) {
            return TYPE_FOOTER;
        } else {
            return TYPE_OTHER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_FOOTER == viewType) {
            return new FooterViewHolder(View.inflate(parent.getContext(), R.layout.foot_item, null));
        } else {
            return getViewHolder();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            final TextView footerText = ((FooterViewHolder) holder).footerText;
            switch (loadState) {
                case STATE_LOADING:
                    footerText.setText("正在加载中...");
                    if (loadMoreListenter != null) {
                        loadMoreListenter.onLoadMore(loadIndex);
                    }
                    break;
                case STATE_LASTED:
                    footerText.setText("没有更多数据了");
                    setHasMore(false);
                    break;
                case STATE_ERROR:
                    footerText.setText("加载错误 (点击重试)");

                    ((FooterViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (loadMoreListenter != null) {

                                loadMoreListenter.onLoadMore(loadIndex);
                            }
                            footerText.setText("正在加载中...");
                        }
                    });
                    break;
                default:
                    setHasMore(true);
                    break;
            }
        } else {
            bindOtherViewHolder((VH) holder, position);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView footerText;

        FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            footerText = itemView.findViewById(R.id.footer);
        }
    }

    public interface LoadMoreListenter {
        void onLoadMore(int loadIndex);
    }

}