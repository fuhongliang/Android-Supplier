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
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.view.RVPIndicator;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

public class FinanceSettlementActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.rvp_indicator)
    RVPIndicator rvpIndicator;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private List<String> mList = Arrays.asList("收支明细", "提现记录");
    FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragmentArrayList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_finance_settlement);
        ButterKnife.bind(this);
        tvTitle.setText("财务结算");
        mFragmentArrayList.add(IncomeDetailFragment.newInstance());
        mFragmentArrayList.add(WithdrawalsRecordFragment.newInstance());
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

    public void setBalanceMoney(String num) {
        tvSettlement.setText(num);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_withdraw)
    public void onTvWithdrawClicked() {
        if (Double.valueOf(tvSettlement.getText().toString().trim()) > 0) {
            DialogUtils.showConfirmDialog("温馨提示", "请选择提现方式", "银行卡转账", "微信转账", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                @Override
                public void cancel() {
                    goToActivity(BankWithdrawActivity.class, tvSettlement.getText().toString().trim());
                }

                @Override
                public void ok() {
                    goToActivity(WeChatWithdrawActivity.class, tvSettlement.getText().toString().trim());
                }
            });
        }else {
            DialogUtils.showConfirmDialog("温馨提示", "您的余额已不足", "取消", "确定", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                @Override
                public void cancel() {

                }

                @Override
                public void ok() {

                }
            });
        }

    }
}
