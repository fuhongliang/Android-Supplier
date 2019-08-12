package cn.ifhu.supplier.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerView mRecyclerView;

    public LoadMoreScrollListener(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        BaseLoadMoreAdapter adapter = (BaseLoadMoreAdapter) mRecyclerView.getAdapter();

        if (null == manager) {
            throw new RuntimeException("you should call setLayoutManager() first!!");
        }
        if (null == adapter){
            throw new RuntimeException("you not call setAdapter()");
        }
        if (manager instanceof LinearLayoutManager) {
            int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();

            if (adapter.getItemCount() >= BaseLoadMoreAdapter.PAGESIZE && lastCompletelyVisibleItemPosition == adapter.getItemCount() - 1 && adapter.isHasMore()) {
                adapter.loadingMore();
            }
        }
    }
}
