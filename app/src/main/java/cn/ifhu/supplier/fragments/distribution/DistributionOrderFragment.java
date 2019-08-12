package cn.ifhu.supplier.fragments.distribution;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//import cn.ifhu.supplier.R;
//import cn.ifhu.supplier.adapter.DistributionOrderAdapter;
//import cn.ifhu.supplier.base.BaseFragment;
//import cn.ifhu.supplier.model.newbean.data.PickListDataBean;

//public class DistributionOrderFragment extends BaseFragment {
//    Unbinder unbinder;
//    @BindView(R.id.recycler_list)
//    UltimateRecyclerView recyclerList;
//    @BindView(R.id.order)
//    TextView order;
//    @BindView(R.id.rl_empty)
//    FrameLayout rlEmpty;
//    @BindView(R.id.layout_swipe_refresh)
//    SwipeRefreshLayout layoutSwipeRefresh;
//
//    DistributionOrderAdapter newDistrbutionOrderAdapter;
//
//    public static Fragment newInstance() {
//        return new DistributionOrderFragment();
//    }
//
//    private List<PickListDataBean.PickListBean> mDatas = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerList.setLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.load_more_layout, null, false));
//        recyclerList.reenableLoadmore();
//        newDistrbutionOrderAdapter.setRecyclerObject(recyclerList);
//        recyclerList.setAdapter(newDistrbutionOrderAdapter);
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//}
