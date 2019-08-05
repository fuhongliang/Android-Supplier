package cn.ifhu.supplier.activity.operation;

import android.os.Bundle;
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
import cn.ifhu.supplier.model.bean.ReviewBean;
import cn.ifhu.supplier.fragments.reviews.AllReviewFragment;
import cn.ifhu.supplier.fragments.reviews.NeedReplyReviewFragment;
import cn.ifhu.supplier.view.RVPIndicator;

/**
 * @author fuhongliang
 */
public class ReviewListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rvp_indicator)
    RVPIndicator rvpIndicator;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_hao_ping)
    TextView tvHaoPing;
    @BindView(R.id.tv_zhong_ping)
    TextView tvZhongPing;
    @BindView(R.id.tv_cha_ping)
    TextView tvChaPing;
    private List<String> mList = Arrays.asList("全部评价", "未回复");
    FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragmentArrayList = new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        ButterKnife.bind(this);
        mFragmentArrayList.add(AllReviewFragment.newInstance());
        mFragmentArrayList.add(NeedReplyReviewFragment.newInstance());
        initViewPager();
        tvTitle.setText("用户评价");
    }

    public void setHeaderData(ReviewBean.HaopingBean headerData){
        tvRate.setText("好评度："+headerData.getRate()*100+"%");
        tvHaoPing.setText("好评（"+headerData.getHaoping()+"）");
        tvZhongPing.setText("中评（"+headerData.getZhongping()+"）");
        tvChaPing.setText("差评（"+headerData.getChaping()+"）");
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
