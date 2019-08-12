package cn.ifhu.supplier.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.ifhu.supplier.view.dialog.loading.LoadingDialog;
import cn.ifhu.supplier.utils.Utils;

///**
// * @author tony
// */
//@Keep
//public abstract class BaseFragment extends Fragment {
//
//    protected BaseActivity mActivity;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.mActivity = (BaseActivity) context;
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    /**
//     * 获取宿主Activity
//     *
//     * @return BaseActivity
//     */
//    protected BaseActivity getHoldingActivity() {
//        return mActivity;
//    }
//
//
//    /**
//     * 添加fragment
//     *
//     * @param fragment
//     * @param frameId
//     */
//    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
//        Utils.checkNotNull(fragment);
//        getHoldingActivity().addFragment(fragment, frameId);
//
//    }
//
//
//    /**
//     * 替换fragment
//     *
//     * @param fragment
//     * @param frameId
//     */
//    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
//        Utils.checkNotNull(fragment);
//        getHoldingActivity().replaceFragment(fragment, frameId);
//    }
//
//
//    /**
//     * 隐藏fragment
//     *
//     * @param fragment
//     */
//    protected void hideFragment(BaseFragment fragment) {
//        Utils.checkNotNull(fragment);
//        getHoldingActivity().hideFragment(fragment);
//    }
//
//
//    /**
//     * 显示fragment
//     *
//     * @param fragment
//     */
//    protected void showFragment(BaseFragment fragment) {
//        Utils.checkNotNull(fragment);
//        getHoldingActivity().showFragment(fragment);
//    }
//
//
//    /**
//     * 移除Fragment
//     *
//     * @param fragment
//     */
//    protected void removeFragment(BaseFragment fragment) {
//        Utils.checkNotNull(fragment);
//        getHoldingActivity().removeFragment(fragment);
//
//    }
//
//
//    /**
//     * 弹出栈顶部的Fragment
//     */
//    protected void popFragment() {
//        getHoldingActivity().popFragment();
//    }
//
//    /**
//     * 是否显示加载提示
//     * @param active 是否激活
//     */
//    public void setLoadingMessageIndicator(boolean active) {
//        if (active) {
//            LoadingDialog.progressShow(getHoldingActivity());
//        } else {
//            LoadingDialog.progressCancel();
//        }
//    }
//
//    public abstract static class BaseLoadMoreAdapter<T,VH> extends RecyclerView.Adapter {
//
//        private static final int TYPE_OTHER = 0;
//        private static final int TYPE_FOOTER = 1;
//
//        private int loadState;
//        private final int STATE_LOADING = 1;
//        private final int STATE_LASTED = 2;
//        private final int STATE_ERROR = 3;
//
//        private LoadMoreListenter loadMoreListenter;
//
//        private boolean hasMore = true;
//
//        private int loadIndex = 2;
//        public static final int PAGESIZE = 10;
//
//        /**
//         * @return 获取正常的item的ViewHolder
//         */
//        public abstract RecyclerView.ViewHolder getViewHolder();
//
//        /**
//         * @return 获取当前的列表数据
//         */
//        public abstract List<T> getDataList();
//
//        /**
//         * 绑定普通的ViewHolder
//         * @param holder ViewHolder
//         * @param position 索引
//         */
//        public abstract void bindOtherViewHolder(@NonNull VH holder, int position);
//
//        public abstract void setData(List<T> data);
//
//        public boolean isHasMore() {
//            return hasMore;
//        }
//
//        public void setHasMore(boolean hasMore) {
//            this.hasMore = hasMore;
//        }
//
//        public void setLoadMordListener(LoadMoreListenter loadMordListener) {
//            this.loadMoreListenter = loadMordListener;
//        }
//
//
//        /**
//         * 在后面加添加数据
//         * @param data 新加进来的数据
//         */
//        public final void appendList(List<T> data){
//            int positionStart = getDataList().size();
//            getDataList().addAll(data);
//            int itemCount = getDataList().size() - positionStart;
//
//            if (positionStart == 0) {
//                notifyDataSetChanged();
//            } else {
//                notifyItemRangeInserted(positionStart + 1, itemCount);
//                loadIndex++;
//                if (itemCount < PAGESIZE) {
//                    loadState = STATE_LASTED;
//                }
//            }
//        }
//
//        public final void setLoadEorro(){
//            loadState = STATE_ERROR;
//
//            notifyItemRangeChanged(getDataList().size(), 1);
//        }
//
//        /**
//         * 设置当前的加载状态 并刷新最后一个item
//         */
//        public final void loadingMore() {
//            if (loadState == STATE_LOADING) {
//                return;
//            }
//            loadState = STATE_LOADING;
//            notifyItemRangeChanged(getDataList().size(), 1);
//
//        }
//
//        public final void resetLodingMore(){
//            loadState = 0;
//            loadIndex = 2;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            if (getDataList().size() != 0 && getDataList().size() <= position) {
//                return TYPE_FOOTER;
//            } else {
//                return TYPE_OTHER;
//            }
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            if (TYPE_FOOTER == viewType) {
//                return new FooterViewHolder(View.inflate(parent.getContext(), R.layout.foot_item, null));
//            } else {
//                return getViewHolder();
//            }
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            if (getItemViewType(position) == TYPE_FOOTER) {
//                final TextView footerText = ((FooterViewHolder) holder).footerText;
//                switch (loadState) {
//                    case STATE_LOADING:
//                        footerText.setText("正在加载中...");
//                        if (loadMoreListenter != null) {
//                            loadMoreListenter.onLoadMore(loadIndex);
//                        }
//                        break;
//                    case STATE_LASTED:
//                        footerText.setText("没有更多数据了");
//                        setHasMore(false);
//                        break;
//                    case STATE_ERROR:
//                        footerText.setText("加载错误 (点击重试)");
//
//                        ((FooterViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (loadMoreListenter != null) {
//
//                                    loadMoreListenter.onLoadMore(loadIndex);
//                                }
//                                footerText.setText("正在加载中...");
//                            }
//                        });
//                        break;
//                    default:
//                        setHasMore(true);
//                        break;
//                }
//            } else {
//                bindOtherViewHolder((VH) holder,position);
//            }
//        }
//
//        class FooterViewHolder extends RecyclerView.ViewHolder {
//
//            TextView footerText;
//
//            FooterViewHolder(@NonNull View itemView) {
//                super(itemView);
//                footerText = itemView.findViewById(R.id.footer);
//            }
//        }
//
//        public interface LoadMoreListenter {
//            void onLoadMore(int loadIndex);
//        }
//
//    }
//
//    public static class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
//
//        private RecyclerView mRecyclerView;
//
//        public LoadMoreScrollListener(RecyclerView recyclerView) {
//            this.mRecyclerView = recyclerView;
//        }
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//
//            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
//            BaseLoadMoreAdapter adapter = (BaseLoadMoreAdapter) mRecyclerView.getAdapter();
//
//            if (null == manager) {
//                throw new RuntimeException("you should call setLayoutManager() first!!");
//            }
//            if (null == adapter){
//                throw new RuntimeException("you not call setAdapter()");
//            }
//            if (manager instanceof LinearLayoutManager) {
//                int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
//
//                if (adapter.getItemCount() >= BaseLoadMoreAdapter.PAGESIZE && lastCompletelyVisibleItemPosition == adapter.getItemCount() - 1 && adapter.isHasMore()) {
//                    adapter.loadingMore();
//                }
//            }
//        }
//    }
//}

/**
 * @author tony
 */
@Keep
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 获取宿主Activity
     *
     * @return BaseActivity
     */
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().addFragment(fragment, frameId);

    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().replaceFragment(fragment, frameId);
    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().hideFragment(fragment);
    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().showFragment(fragment);
    }


    /**
     * 移除Fragment
     *
     * @param fragment
     */
    protected void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().removeFragment(fragment);

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        getHoldingActivity().popFragment();
    }

    /**
     * 是否显示加载提示
     * @param active 是否激活
     */
    public void setLoadingMessageIndicator(boolean active) {
        if (active) {
            LoadingDialog.progressShow(getHoldingActivity());
        } else {
            LoadingDialog.progressCancel();
        }
    }

}

