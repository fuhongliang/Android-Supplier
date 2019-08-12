package cn.ifhu.supplier.fragments.distribution;

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
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.PickListAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.PickListDataBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.SetPickingPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.DistributionService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * 拣货单列表
 */
public class PickListFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recycler_list)
    UltimateRecyclerView recyclerList;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.rl_empty)
    FrameLayout rlEmpty;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;

    PickListAdapter newPickListAdapter;

    public static Fragment newInstance() {
        return new PickListFragment();
    }

    private List<PickListDataBean.PickListBean> mDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        setPickGoods();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerList.setLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.load_more_layout, null, false));
        recyclerList.reenableLoadmore();
        newPickListAdapter = new PickListAdapter(mDatas, getActivity(), new PickListAdapter.OnclickButton() {
            @Override
            public void finishPicking(int position) {
                /**
                 * 设置拣货接口
                 */
                setLoadingMessageIndicator(true);
                SetPickingPostBean setPickingPostBean = new SetPickingPostBean();
                setPickingPostBean.getHuodan_id();
                RetrofitAPIManager.create(DistributionService.class).setPick(setPickingPostBean)
                        .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                    @Override
                    protected void onApiComplete() {
                        setLoadingMessageIndicator(false);
                    }

                    @Override
                    protected void onSuccees(BaseEntity t) throws Exception {
                        ToastHelper.makeText(t.getMessage()).show();
                    }
                });
            }

            @Override
            public void pickingDetails(int position) {
//                if (getActivity() != null) {
//                    Intent intent = new Intent(getActivity(),PickingDetailsActivity.class);
//                    intent.putExtra("Huodan_id",mDatas.get(position).getHuodan_id());
//                    intent.putExtra("Create_time",mDatas.get(position).getCreate_time());
//                    intent.putExtra("Goods_num",mDatas.get(position).getGoods_num());
//                    intent.putExtra("Goods_count",mDatas.get(position).getGoods_count());
//                    intent.putExtra("Total_pay_price",mDatas.get(position).getTotal_pay_price());
//                    intent.putExtra("pick_status",mDatas.get(position).getPick_status());
//                    startActivity(intent);
//                }

            }
        });
        newPickListAdapter.setRecyclerObject(recyclerList);
        recyclerList.setAdapter(newPickListAdapter);
    }


    /**
     * 商品拣货单接口
     */
    public void setPickGoods() {
        setLoadingMessageIndicator(true);
        BasePostBean basePostBean = new BasePostBean();
        RetrofitAPIManager.create(DistributionService.class).pickGoods(basePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<PickListDataBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<PickListDataBean> t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
