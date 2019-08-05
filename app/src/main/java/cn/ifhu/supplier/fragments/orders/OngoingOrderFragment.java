package cn.ifhu.supplier.fragments.orders;


import android.annotation.SuppressLint;
import android.app.NotificationManager;
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
import android.widget.FrameLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

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
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.model.newbean.data.OrdersDataBean;
import cn.ifhu.supplier.model.newbean.post.OrdersPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OrderService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DividerItemDecoration;
import cn.ifhu.supplier.utils.notificaitons.Notificaitons;

import static cn.ifhu.supplier.activity.orders.ShippingActivity.ORDERID;
import static cn.ifhu.supplier.utils.Constants.ORDERCOMING;

/**
 * 待收货
 * @author fuhongliang
 */
public class OngoingOrderFragment extends BaseFragment {

    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.rl_empty)
    FrameLayout llEmpty;

//    OrdersAdapter newOrdersAdapter;
//    @BindView(R.id.recycler_list)
//    RecyclerView recyclerList;

    OrdersPageAdapter ordersPageAdapter;//分页列表的适配器
    @BindView(R.id.recycler_list)
    UltimateRecyclerView ultimateRecyclerView;

    private int currentPages = 1;

    private List<OrdersDataBean.OrderBean> mDatas = new ArrayList<>();

    public static OngoingOrderFragment newInstance() {
        return new OngoingOrderFragment();
    }

    public OngoingOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
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
                getNewOrders(++currentPages);
            }
        });
        ordersPageAdapter = new OrdersPageAdapter(mDatas, getActivity(), new OrdersAdapter.OnclickButton() {
            @Override
            public void modifyPrice(int position) {

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
        setRefreshLayout();
        getNewOrders(1);
        EventBus.getDefault().register(this);
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            getNewOrders(1);
        });
    }

    public void getNewOrders(int pages) {
        layoutSwipeRefresh.setRefreshing(true);
        OrdersPostBean ordersPostBean = new OrdersPostBean();
        ordersPostBean.setStatus(2);
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
                    ordersPageAdapter.setLoadingState(false);

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
                try {
                    JSONObject jsonObject = new JSONObject(messageEvent.getData());
                    UMessage msg = new UMessage(jsonObject);
                    sendNotification(getActivity(), msg.title, msg.text, msg.title);
                    getNewOrders(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    public void sendNotification(Context context, String title, String text, String contentTitle) {
        NotificationManager mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notificaitons.getInstance().sendOrderComingNotification(context, mNM, title, text, contentTitle);
    }
}
