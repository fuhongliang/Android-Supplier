package cn.ifhu.supplier.fragments.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import cn.ifhu.supplier.activity.orders.ShippingActivity;
import cn.ifhu.supplier.adapter.OrdersAdapter;
import cn.ifhu.supplier.adapter.OrdersPageAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.newbean.data.OrdersDataBean;
import cn.ifhu.supplier.model.newbean.post.ModifyPricePostBean;
import cn.ifhu.supplier.model.newbean.post.OrdersPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.net.OrderService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DividerItemDecoration;
import cn.ifhu.supplier.view.dialog.nicedialog.ModifyPriceDialog;

import static cn.ifhu.supplier.activity.orders.ShippingActivity.ORDERID;
import static cn.ifhu.supplier.utils.Constants.ORDERCOMING;

/**
 * 待发货
 * @author fuhongliang
 */
public class UnShippingOrderFragment extends BaseFragment {
//    @BindView(R.id.recycler_list)
//    RecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.rl_empty)
    FrameLayout llEmpty;

//  OrdersAdapter newOrdersAdapter;
    OrdersPageAdapter ordersPageAdapter;//分页列表的适配器
    @BindView(R.id.recycler_list_test)
    UltimateRecyclerView ultimateRecyclerView;

    private int currentPages = 1;

    private List<OrdersDataBean.OrderBean> mDatas = new ArrayList<>();

    public static UnShippingOrderFragment newInstance() {
        return new UnShippingOrderFragment();
    }


    public UnShippingOrderFragment() {
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
        ordersPageAdapter = new OrdersPageAdapter(mDatas, getActivity(), new OrdersAdapter.OnclickButton() {
            @Override
            public void modifyPrice(int position) {
                View view = getLayoutInflater().inflate(R.layout.modify_price_dialog, null);
                DialogUtils.showModifyPriceDialog(getActivity().getSupportFragmentManager(),new ModifyPriceDialog.ButtonOnclick() {
                    @Override
                    public void addPrice(String goodsPrice, String shippingFee) {
                        String mGoodsPrice = "0";
                        String mShippingFee = "0";

                        if (!StringUtils.isEmpty(goodsPrice)){
                            mGoodsPrice = goodsPrice;
                        }

                        if (!StringUtils.isEmpty(shippingFee)){
                            mShippingFee = shippingFee;
                        }
                        if (!"0".equals(mGoodsPrice)  || !"0".equals(mShippingFee)){
                            modifyOrderPrice(mDatas.get(position).getOrder_id(),1,mGoodsPrice,mShippingFee);
                        }
                        InputMethodManager mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }

                    @Override
                    public void discount(String goodsPrice, String shippingFee) {
                        String mGoodsPrice = "0";
                        String mShippingFee = "0";

                        if (!StringUtils.isEmpty(goodsPrice)){
                            mGoodsPrice = goodsPrice;
                        }

                        if (!StringUtils.isEmpty(shippingFee)){
                            mShippingFee = shippingFee;
                        }
                        if (!"0".equals(mGoodsPrice)  || !"0".equals(mShippingFee)){
                            modifyOrderPrice(mDatas.get(position).getOrder_id(),0,mGoodsPrice,mShippingFee);
                        }

                        InputMethodManager mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }
                });
            }

            @Override
            public void shipping(int position) {
                Intent intent = new Intent(getActivity(), ShippingActivity.class);
                intent.putExtra(ORDERID,mDatas.get(position).getOrder_id());
                startActivity(intent);
            }
        });
        ordersPageAdapter.setRecyclerObject(ultimateRecyclerView);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ultimateRecyclerView.setAdapter(ordersPageAdapter);
//        recyclerList.setAdapter(newOrdersAdapter);
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
        OrdersPostBean ordersPostBean = new OrdersPostBean();
        ordersPostBean.setStatus(1);
        ordersPostBean.setPage(pages);
        ordersPostBean.setPer_page(20);
        RetrofitAPIManager.create(OrderService.class).getOrders(ordersPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<OrdersDataBean>(true) {

            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);

            }

            @Override
            protected void onSuccees(BaseEntity<OrdersDataBean> t) throws Exception {

                if (t.getData() == null || t.getData().getOrder().isEmpty()) {
                    ordersPageAdapter.setLoadingState(true);
                    if (mDatas.size()!=0&&pages==1){
                        mDatas.clear();
                        ordersPageAdapter.updateData(mDatas);
                    }
                } else {
                    if (pages==1) {
                        mDatas.clear();
                        mDatas.addAll(t.getData().getOrder());
                        ordersPageAdapter.updateData(mDatas);
                        currentPages=1;
                    } else {
                        mDatas.addAll(t.getData().getOrder());
                        ordersPageAdapter.insert(t.getData().getOrder());
                    }
                    if (t.getData().getOrder().size()<20) {
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
