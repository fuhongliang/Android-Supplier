package cn.ifhu.supplier.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.ShareListAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.base.BaseLoadMoreAdapter;
import cn.ifhu.supplier.base.LoadMoreScrollListener;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.ShareListDataBean;
import cn.ifhu.supplier.model.newbean.post.ShareListPostBean;
import cn.ifhu.supplier.model.newbean.post.SharePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.HomeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.DividerItemDecoration;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

public class DeleteShareActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_right_text)
    TextView tvRightText;


    @BindView(R.id.recycler_list_test)
    RecyclerView recyclerList;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.rl_empty)
    FrameLayout rlEmpty;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;

    ShareListAdapter shareListAdapter;

    private List<ShareListDataBean.ListBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        tvTitle.setText("我的团长");
        tvRightText.setText("添加团长");
        shareListAdapter = new ShareListAdapter(mDatas, this, new ShareListAdapter.OnclickButton() {
            @Override
            public void addShare(int position) {
            }

            @Override
            public void deleteShare(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否删除团长", "取消", "确定", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        /**
                         * 删除团长接口
                         */
                        setLoadingMessageIndicator(true);
                        SharePostBean sharePostBean = new SharePostBean();
                        sharePostBean.setShare_id(mDatas.get(position).getId());
                        RetrofitAPIManager.create(HomeService.class).delShare(sharePostBean)
                                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                            @Override
                            protected void onApiComplete() {
                                setLoadingMessageIndicator(false);
                            }

                            @Override
                            protected void onSuccees(BaseEntity t) throws Exception {
                                ToastHelper.makeText(t.getMessage()).show();
                                getShareList(1);
                            }
                        });
                    }
                });

            }
        }, 1);
        shareListAdapter.setLoadMordListener(new BaseLoadMoreAdapter.LoadMoreListenter() {
            @Override
            public void onLoadMore(int loadIndex) {
                getShareList(loadIndex);
            }
        });
        recyclerList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerList.setAdapter(shareListAdapter);
        recyclerList.setOnScrollListener(new LoadMoreScrollListener(recyclerList));
        setRefreshLayout();
        getShareList(1);
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        layoutSwipeRefresh.setOnRefreshListener(() -> getShareList(1));
    }

    /**
     * 团长列表接口
     *
     * @param pages
     */
    public void getShareList(int pages) {
        layoutSwipeRefresh.setRefreshing(true);
        ShareListPostBean shareListPostBean = new ShareListPostBean();
        shareListPostBean.setType(1);
        shareListPostBean.setLimit(10);
        shareListPostBean.setPage(pages);
        RetrofitAPIManager.create(HomeService.class).listShare(shareListPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ShareListDataBean>(true) {
            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ShareListDataBean> t) throws Exception {
                if (pages == 1) {
                    mDatas.clear();
                    mDatas.addAll(t.getData().getList());
                    shareListAdapter.setData(mDatas);
                } else {
                    mDatas.addAll(t.getData().getList());
                    shareListAdapter.appendList(t.getData().getList());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getShareList(1);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_right_text)
    public void onTvRightTextClicked() {
        goToActivity(AddShareListActivity.class);
    }
}
