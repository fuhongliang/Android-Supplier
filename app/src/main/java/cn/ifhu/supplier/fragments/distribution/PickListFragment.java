package cn.ifhu.supplier.fragments.distribution;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.distribution.PickingDetailsActivity;
import cn.ifhu.supplier.adapter.PickListAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.base.BaseLoadMoreAdapter;
import cn.ifhu.supplier.base.LoadMoreScrollListener;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.PickListDataBean;
import cn.ifhu.supplier.model.newbean.post.PaginationPostBean;
import cn.ifhu.supplier.model.newbean.post.SetPickingPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.DistributionService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DividerItemDecoration;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * 拣货单列表
 */
public class PickListFragment extends BaseFragment {

    Unbinder unbinder;
    PickListAdapter newPickListAdapter;

    @BindView(R.id.recycler_list_test)
    RecyclerView recyclerList;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.rl_empty)
    FrameLayout rlEmpty;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;

    public static Fragment newInstance() {
        return new PickListFragment();
    }


    private List<PickListDataBean.PickListBean> mDatas = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        getPickGoods(1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newPickListAdapter = new PickListAdapter(mDatas, getActivity(), new PickListAdapter.OnclickButton() {
            @Override
            public void finishPicking(int position) {
                /**
                 * 设置拣货接口
                 */
                setLoadingMessageIndicator(true);
                SetPickingPostBean setPickingPostBean = new SetPickingPostBean();
                setPickingPostBean.setHuodan_id(mDatas.get(position).getHuodan_id());
                RetrofitAPIManager.create(DistributionService.class).setPick(setPickingPostBean)
                        .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                    @Override
                    protected void onApiComplete() {
                        setLoadingMessageIndicator(false);
                    }

                    @Override
                    protected void onSuccees(BaseEntity t) throws Exception {
                        ToastHelper.makeText("拣货成功").show();
                        getPickGoods(1);
                    }
                });
            }

            @Override
            public void pickingDetails(int position) {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), PickingDetailsActivity.class);
                    intent.putExtra("Huodan_id",mDatas.get(position).getHuodan_id());
                    intent.putExtra("Create_time",mDatas.get(position).getCreate_time());
                    intent.putExtra("Goods_num",mDatas.get(position).getGoods_num());
                    intent.putExtra("Goods_count",mDatas.get(position).getGoods_count());
                    intent.putExtra("Total_pay_price",mDatas.get(position).getTotal_pay_price());
                    intent.putExtra("pick_status",mDatas.get(position).getPick_status());
                    intent.putExtra("pick_no",mDatas.get(position).getPick_no());
                    startActivity(intent);
                }

            }
        });
        newPickListAdapter.setLoadMordListener(new BaseLoadMoreAdapter.LoadMoreListenter() {
            @Override
            public void onLoadMore(int loadIndex) {
                getPickGoods(loadIndex);
            }
        });
        recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerList.setAdapter(newPickListAdapter);
        recyclerList.setOnScrollListener(new LoadMoreScrollListener(recyclerList));
        setRefreshLayout();
        getPickGoods(1);
    }

    /**
     * 刷新监听
     */
    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        layoutSwipeRefresh.setOnRefreshListener(() -> getPickGoods(1));
    }

    /**
     * 商品拣货单列表接口
     */
    public void getPickGoods(int pages) {
        layoutSwipeRefresh.setRefreshing(true);
        PaginationPostBean paginationPostBean = new PaginationPostBean();
        paginationPostBean.setLimit(10);
        paginationPostBean.setPage(pages);
        RetrofitAPIManager.create(DistributionService.class).pickGoods(paginationPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<PickListDataBean>(false) {
            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<PickListDataBean> t) throws Exception {
                if (pages == 1) {
                    mDatas.clear();
                    mDatas.addAll(t.getData().getPick_list());
                    newPickListAdapter.setData(mDatas);
                } else {
                    mDatas.addAll(t.getData().getPick_list());
                    newPickListAdapter.appendList(t.getData().getPick_list());
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
