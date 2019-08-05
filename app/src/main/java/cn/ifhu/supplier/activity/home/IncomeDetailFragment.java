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
import android.widget.FrameLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.IncomeDetailAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.IncomeDetailDataBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.HomeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;


public class IncomeDetailFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recycler_list)
    UltimateRecyclerView recyclerList;
    @BindView(R.id.rl_empty)
    FrameLayout rlEmpty;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    private int currentPages = 1;
    private List<IncomeDetailDataBean.AccountsBean> mDatas = new ArrayList<>();

    IncomeDetailAdapter incomeDetailAdapter;

    public static Fragment newInstance() {
        return new IncomeDetailFragment();
    }

    public IncomeDetailFragment() {
        // Required empty public constructor
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
        incomeDetailAdapter = new IncomeDetailAdapter(mDatas,getActivity());
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerList.setLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.load_more_layout, null, false));
        recyclerList.reenableLoadmore();
        recyclerList.setOnLoadMoreListener((itemsCount, maxLastVisiblePosition) -> {
            // TODO: 2019-07-02 请求处理
            if (incomeDetailAdapter.getLoadingState()) {
                getIncomeDetail(++currentPages);
                incomeDetailAdapter.setLoadingState(true);
            }
        });
        incomeDetailAdapter.setRecyclerObject(recyclerList);
        recyclerList.setAdapter(incomeDetailAdapter);
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
        RetrofitAPIManager.create(HomeService.class).incomeDetail(allEvaluationPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<IncomeDetailDataBean>(true) {

            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
                incomeDetailAdapter.setLoadingState(true);
            }

            @Override
            protected void onSuccees(BaseEntity<IncomeDetailDataBean> t) throws Exception {

                if (t.getData() == null || t.getData().getAccounts().isEmpty()) {

                    if (mDatas.size() != 0 && pages == 1) {
                        mDatas.clear();
                        incomeDetailAdapter.updateData(mDatas);
                    }
                } else {

                    if (getActivity() != null) {
                        ((FinanceSettlementActivity) getActivity()).setBalanceMoney(t.getData().getBalance());
                    }

                    if (pages == 1) {
                        mDatas.clear();
                        mDatas.addAll(t.getData().getAccounts());
                        incomeDetailAdapter.updateData(mDatas);
                        currentPages = 1;
                    } else {
                        mDatas.addAll(t.getData().getAccounts());
                        incomeDetailAdapter.insert(t.getData().getAccounts());
                    }
//                    if (t.getData().getAccounts().size() < 20) {
//                        incomeDetailAdapter.setLoadingState(true);
//                    } else {
//                        incomeDetailAdapter.setLoadingState(false);
//                    }
                    currentPages = currentPages + 1;
                }
                updateEmptyView();

            }

            public void updateEmptyView() {
                if (incomeDetailAdapter.getItemCount() > 0) {
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
            protected void onCodeError(BaseEntity<IncomeDetailDataBean> t) throws Exception {
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
