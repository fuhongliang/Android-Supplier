package cn.ifhu.supplier.fragments.orders;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.notice.NoticeListActivity;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.view.RVPIndicator;

/**
 * @author tony
 */
public class OrdersFragment extends BaseFragment {

    @BindView(R.id.tv_normal_order)
    TextView mTvNormalOrder;
    @BindView(R.id.tv_unnormal_order)
    TextView mTvUnnormalOrder;
    @BindView(R.id.rvp_indicator)
    RVPIndicator rvpIndicator;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    Unbinder unbinder;

    private List<String> mList = Arrays.asList("待付款", "待发货", "待收货", "已完成", "已取消", "待处理", "已处理");
    FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragmentArrayList = new ArrayList<Fragment>();


    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_managment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFragmentArrayList.add(UnPayingOrderFragment.newInstance());
        mFragmentArrayList.add(UnShippingOrderFragment.newInstance());
        mFragmentArrayList.add(OngoingOrderFragment.newInstance());
        mFragmentArrayList.add(FinishedOrderFragment.newInstance());
        mFragmentArrayList.add(CancelOrderFragment.newInstance());
        mFragmentArrayList.add(UnNormalOrderUnHandleFragment.newInstance());
        mFragmentArrayList.add(UnNormalOrderHandledFragment.newInstance());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
        handleOrderStatus(true);
    }

    public void initViewPager() {
        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragmentArrayList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentArrayList.get(position);
            }
        };
        rvpIndicator.setTitleList(mList);
        vpContent.setOffscreenPageLimit(7);
        vpContent.setAdapter(mAdapter);
        rvpIndicator.setViewPager(vpContent, 0);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                handleOrderStatus(i<5);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_notice)
    public void onIvNoticeClicked() {
        startActivity(new Intent(getContext(), NoticeListActivity.class));
    }

    @OnClick(R.id.tv_normal_order)
    public void onMTvNormalOrderClicked() {
        handleOrderStatus(true);
        rvpIndicator.setViewPosition(0);
    }

    @OnClick(R.id.tv_unnormal_order)
    public void onMTvUnnormalOrderClicked() {
        handleOrderStatus(false);
        rvpIndicator.setViewPosition(5);
    }

    public void handleOrderStatus(boolean normalStatus){
        mTvNormalOrder.setSelected(!normalStatus);
        mTvUnnormalOrder.setSelected(normalStatus);
    }
}
