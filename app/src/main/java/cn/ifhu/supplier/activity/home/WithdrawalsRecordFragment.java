package cn.ifhu.supplier.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.WithdrawalsRecordAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.WithdrawalsRecordDataBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.HomeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;

public class WithdrawalsRecordFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recycler_list)
    UltimateRecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;

    private int currentPages = 1;
    private List<WithdrawalsRecordDataBean.CashBean> mDatas = new ArrayList<>();

    WithdrawalsRecordAdapter withdrawalsRecordAdapter;
    public static Fragment newInstance() {
        return new WithdrawalsRecordFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        withdrawalsRecordAdapter = new WithdrawalsRecordAdapter(mDatas,getActivity());
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerList.setLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.load_more_layout, null, false));
        recyclerList.reenableLoadmore();
        recyclerList.setOnLoadMoreListener((itemsCount, maxLastVisiblePosition) -> {
            // TODO: 2019-07-02 请求处理
            if (withdrawalsRecordAdapter.getLoadingState()) {
                getIncomeDetail(++currentPages);
            }
        });
        withdrawalsRecordAdapter.setRecyclerObject(recyclerList);
        recyclerList.setAdapter(withdrawalsRecordAdapter);
        setRefreshLayout();
        getIncomeDetail(1);
    }


    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        layoutSwipeRefresh.setOnRefreshListener(() -> getIncomeDetail(1));

    }

    /**
     * 财务结算接口、分页
     *
     * @param pages
     */
    public void getIncomeDetail(int pages) {
        layoutSwipeRefresh.setRefreshing(true);
        AllEvaluationPostBean allEvaluationPostBean = new AllEvaluationPostBean();
        allEvaluationPostBean.setLimit(20);
        allEvaluationPostBean.setPage(pages + "");
        RetrofitAPIManager.create(HomeService.class).cashOutRecord(allEvaluationPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<WithdrawalsRecordDataBean>(true) {

            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
                withdrawalsRecordAdapter.setLoadingState(true);
            }

            @Override
            protected void onSuccees(BaseEntity<WithdrawalsRecordDataBean> t) throws Exception {

                if (t.getData() == null || t.getData().getCash().isEmpty()) {
                    if (mDatas.size() != 0 && pages == 1) {
                        mDatas.clear();
                        withdrawalsRecordAdapter.updateData(mDatas);
                    }
                } else {

                    if (pages == 1) {
                        mDatas.clear();
                        mDatas.addAll(t.getData().getCash());
                        withdrawalsRecordAdapter.updateData(mDatas);
                        currentPages = 1;
                    } else {
                        mDatas.addAll(t.getData().getCash());
                        withdrawalsRecordAdapter.insert(t.getData().getCash());
                    }
//                    if (t.getData().getCash().size() < 20) {
//                        withdrawalsRecordAdapter.setLoadingState(true);
//                    } else {
//                        withdrawalsRecordAdapter.setLoadingState(false);
//                    }
                    currentPages = currentPages + 1;
                }
                updateEmptyView();

            }

            public void updateEmptyView() {
                if (withdrawalsRecordAdapter.getItemCount() > 0) {
//            llEmpty.setVisibility(View.GONE);
                } else {
//            llEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onAPIError() {
                super.onAPIError();
                Logger.d("onAPIError: ");
            }

            @Override
            protected void onCodeError(BaseEntity<WithdrawalsRecordDataBean> t) throws Exception {
                super.onCodeError(t);
                Logger.d("t.code" + t.code);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onResume() {
        super.onResume();
        getIncomeDetail(1);
    }
}
