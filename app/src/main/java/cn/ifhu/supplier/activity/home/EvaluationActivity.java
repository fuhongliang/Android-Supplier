package cn.ifhu.supplier.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
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
import cn.ifhu.supplier.view.RVPIndicator;

public class EvaluationActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.rvp_indicator)
    RVPIndicator rvpIndicator;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.vp_content)
    ViewPager vpContent;


    private List<String> mList = Arrays.asList("全部评价", "未回复");
    FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragmentArrayList = new ArrayList<Fragment>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_evaluation_management);
        ButterKnife.bind(this);
        tvTitle.setText("评价管理");
        mFragmentArrayList.add(AllEvaluationFragment.newInstance());
        mFragmentArrayList.add(UnEvaluationFragment.newInstance());
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
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

}
