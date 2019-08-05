package cn.ifhu.supplier.activity.financial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.fragments.operation.SettledFragment;
import cn.ifhu.supplier.fragments.operation.WithdrawFragment;
import cn.ifhu.supplier.view.RVPIndicator;

/**
 * 全部账单
 * @author fuhongliang
 */
public class BillsListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rvp_indicator)
    RVPIndicator rvpIndicator;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private List<String> mList = Arrays.asList("已结算", "已提现");
    FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragmentArrayList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_list);
        ButterKnife.bind(this);
        tvTitle.setText("全部账单");
        mFragmentArrayList.add(SettledFragment.newInstance());
        mFragmentArrayList.add(WithdrawFragment.newInstance());
        initViewPager();
    }

    public void initViewPager() {
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        vpContent.setOffscreenPageLimit(3);
        vpContent.setAdapter(mAdapter);
        rvpIndicator.setViewPager(vpContent, 0);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
