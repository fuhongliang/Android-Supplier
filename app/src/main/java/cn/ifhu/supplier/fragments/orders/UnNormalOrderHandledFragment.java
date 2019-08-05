package cn.ifhu.supplier.fragments.orders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.UnNormalOrdersPageAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.model.newbean.data.RefundOrdersDataBean;
import cn.ifhu.supplier.model.newbean.post.ModifyPricePostBean;
import cn.ifhu.supplier.model.newbean.post.RefundOrdersPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OrderService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DividerItemDecoration;

import static cn.ifhu.supplier.utils.Constants.ORDERCOMING;

/**
 * 已处理
 * @author fuhongliang
 */
public class UnNormalOrderHandledFragment extends BaseFragment {
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.rl_empty)
    FrameLayout llEmpty;
    UnNormalOrdersPageAdapter ordersPageAdapter;
    @BindView(R.id.recycler_list_test)
    UltimateRecyclerView ultimateRecyclerView;

    private int currentPages = 1;

    private List<RefundOrdersDataBean.RefundOrderBean> mDatas = new ArrayList<>();

    public static UnNormalOrderHandledFragment newInstance() {
        return new UnNormalOrderHandledFragment();
    }


    public UnNormalOrderHandledFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.load_more_layout,null,false));
        ultimateRecyclerView.reenableLoadmore();
        ultimateRecyclerView.setOnLoadMoreListener((itemsCount, maxLastVisiblePosition) -> {
            // TODO: 2019-07-02 请求处理
            if (ordersPageAdapter.getLoadingState()) {
                getNewOrders(currentPages);
            }
        });
        ordersPageAdapter = new UnNormalOrdersPageAdapter(mDatas, getActivity(), new UnNormalOrdersPageAdapter.OnclickButton() {

            @Override
            public void refuse(int position) {

            }

            @Override
            public void agree(int position) {

            }
        });
        ordersPageAdapter.setRecyclerObject(ultimateRecyclerView);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ultimateRecyclerView.setAdapter(ordersPageAdapter);
        setRefreshLayout();
        getNewOrders(1);
        EventBus.getDefault().register(this);
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        layoutSwipeRefresh.setOnRefreshListener(() -> getNewOrders(1));
    }

    public void getNewOrders(int pages) {
        layoutSwipeRefresh.setRefreshing(true);
        RefundOrdersPostBean ordersPostBean = new RefundOrdersPostBean();
        ordersPostBean.setRefund_status(2);
        ordersPostBean.setPage(pages);
        ordersPostBean.setLimit(20);
        RetrofitAPIManager.create(OrderService.class).getRefundOrders(ordersPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<RefundOrdersDataBean>(true) {

            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);

            }

            @Override
            protected void onSuccees(BaseEntity<RefundOrdersDataBean> t) throws Exception {

                if (t.getData() == null || t.getData().getRefund_order().isEmpty()) {
                    ordersPageAdapter.setLoadingState(true);
                    if (mDatas.size()!=0&&pages==1){
                        mDatas.clear();
                        ordersPageAdapter.updateData(mDatas);
                    }
                } else {
                    if (pages==1) {
                        mDatas.clear();
                        mDatas.addAll(t.getData().getRefund_order());
                        ordersPageAdapter.updateData(mDatas);
                        currentPages=1;
                    } else {
                        mDatas.addAll(t.getData().getRefund_order());
                        ordersPageAdapter.insert(t.getData().getRefund_order());
                    }
                    if (t.getData().getRefund_order().size()<20) {
                        ordersPageAdapter.setLoadingState(true);
                    } else {
                        ordersPageAdapter.setLoadingState(false);
                    }
                    currentPages = currentPages + 1 ;
                }
                updateEmptyView();
            }
        });
    }

    public void updateEmptyView() {
        if (ordersPageAdapter.getItemCount() > 0) {
            llEmpty.setVisibility(View.GONE);
        } else {
            llEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void modifyOrderPrice(int orderId, int type, String update_price, String update_express) {
        setLoadingMessageIndicator(true);
        ModifyPricePostBean modifyPricePostBean = new ModifyPricePostBean();
        modifyPricePostBean.setOrder_id(orderId);
        modifyPricePostBean.setType(type);
        modifyPricePostBean.setUpdate_price(update_price);
        modifyPricePostBean.setUpdate_express(update_express);

        RetrofitAPIManager.create(OrderService.class).modifyOrderPrice(modifyPricePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                getNewOrders(1);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ORDERCOMING:
                getNewOrders(1);
                break;
            default:
        }
    }
}
