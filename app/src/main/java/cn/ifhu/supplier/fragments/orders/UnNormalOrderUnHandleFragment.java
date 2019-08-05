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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.UnNormalOrdersPageAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.RefundOrdersDataBean;
import cn.ifhu.supplier.model.newbean.post.RefundHandlePostBean;
import cn.ifhu.supplier.model.newbean.post.RefundOrdersPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OrderService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.DividerItemDecoration;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

/**
 * 待处理
 * @author fuhongliang
 */
public class UnNormalOrderUnHandleFragment extends BaseFragment {
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

    public static UnNormalOrderUnHandleFragment newInstance() {
        return new UnNormalOrderUnHandleFragment();
    }


    public UnNormalOrderUnHandleFragment() {
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
                getOrders(currentPages);
            }
        });
        ordersPageAdapter = new UnNormalOrdersPageAdapter(mDatas, getActivity(), new UnNormalOrdersPageAdapter.OnclickButton() {

            @Override
            public void refuse(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "确认拒绝该退货退款申请？",
                        getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok() {
                                handleRefundOrder(0,mDatas.get(position).getOrder_refund_id(),"商家拒绝退货退款");
                            }
                        });
            }

            @Override
            public void agree(int position) {
                DialogUtils.showConfirmDialog("确认同意退货退款？", "确认通过后退款金额将直接返还给用户！",
                        getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok() {
                                handleRefundOrder(1,mDatas.get(position).getOrder_refund_id(),"商家同意退货退款");
                            }
                        });
            }
        });
        ordersPageAdapter.setRecyclerObject(ultimateRecyclerView);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ultimateRecyclerView.setAdapter(ordersPageAdapter);
        setRefreshLayout();
        getOrders(1);
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        layoutSwipeRefresh.setOnRefreshListener(() -> getOrders(1));
    }

    public void getOrders(int pages) {
        layoutSwipeRefresh.setRefreshing(true);
        RefundOrdersPostBean ordersPostBean = new RefundOrdersPostBean();
        ordersPostBean.setRefund_status(1);
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


    public void handleRefundOrder(int mOption,int order_refund_id,String refuse_desc) {
        setLoadingMessageIndicator(true);
        RefundHandlePostBean refundHandlePostBean = new RefundHandlePostBean();
        refundHandlePostBean.setOption(mOption);
        refundHandlePostBean.setOrder_refund_id(order_refund_id);
        refundHandlePostBean.setRefuse_desc(refuse_desc);
        RetrofitAPIManager.create(OrderService.class).refundHandle(refundHandlePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                getOrders(1);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
