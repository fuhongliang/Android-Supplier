package cn.ifhu.supplier.fragments.home;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.home.EvaluationActivity;
import cn.ifhu.supplier.activity.home.FinanceSettlementActivity;
import cn.ifhu.supplier.activity.join.DepositAgreementActivity;
import cn.ifhu.supplier.activity.join.MyDepositActivity;
import cn.ifhu.supplier.activity.me.StoreSetUpActivity;
import cn.ifhu.supplier.activity.notice.NoticeListActivity;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.model.newbean.data.DepostListBean;
import cn.ifhu.supplier.model.newbean.data.HomeDataBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DepostListBeanLogic;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

import static cn.ifhu.supplier.utils.Constants.UNNORMALORDER;
import static cn.ifhu.supplier.utils.Constants.UNPAYORDER;
import static cn.ifhu.supplier.utils.Constants.UNSHIPPINGORDER;

/**
 * @author tony
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.iv_logo)
    GlideImageView mIvLogo;
    @BindView(R.id.tv_store_name)
    TextView mTvStoreName;
    Unbinder unbinder;
    @BindView(R.id.ll_notice)
    LinearLayout mLlNotice;
    @BindView(R.id.ll_reviews)
    LinearLayout mLlReviews;
    @BindView(R.id.ll_store_settings)
    LinearLayout mLlStoreSettings;
    @BindView(R.id.ll_finance)
    LinearLayout mLlFinance;
    @BindView(R.id.ll_depost)
    LinearLayout mLlDepost;
    @BindView(R.id.iv_depost)
    ImageView mIvDepost;
    DepostListBean mDepostListBean;
    @BindView(R.id.tv_depost)
    TextView mTvDepost;
    @BindView(R.id.tv_sales_statistics)
    TextView mTvSalesStatistics;
    @BindView(R.id.tv_sales_today)
    TextView mTvSalesToday;
    @BindView(R.id.tv_sales_yesterday)
    TextView mTvSalesYesterday;
    @BindView(R.id.tv_sales_serven_days)
    TextView mTvSalesServenDays;
    @BindView(R.id.tv_sales_thirty_days)
    TextView mTvSalesThirtyDays;
    @BindView(R.id.tv_sales_orders)
    TextView mTvSalesOrders;
    @BindView(R.id.tv_sales_money)
    TextView mTvSalesMoney;
    @BindView(R.id.tv_unpay_orders)
    TextView mTvUnpayOrders;
    @BindView(R.id.ll_unpay_orders)
    LinearLayout mLlUnpayOrders;
    @BindView(R.id.tv_unshipping_orders)
    TextView mTvUnshippingOrders;
    @BindView(R.id.ll_unshipping_orders)
    LinearLayout mLlUnshippingOrders;
    @BindView(R.id.tv_problem_orders)
    TextView mTvProblemOrders;
    @BindView(R.id.ll_problem_orders)
    LinearLayout mLlProblemOrders;
    @BindView(R.id.line_chart)
    LineChart chart;
    HomeDataBean mHomeDataBean = new HomeDataBean();
    int payIndex = 1;
    int sellIndex = 1;
    @BindView(R.id.tv_pay_statistics_today)
    TextView mTvPayStatisticsToday;
    @BindView(R.id.tv_pay_statistics_yesterday)
    TextView mTvPayStatisticsYesterday;
    @BindView(R.id.tv_pay_statistics_sevendays)
    TextView mTvPayStatisticsSevendays;
    @BindView(R.id.tv_pay_statistics_thirtydays)
    TextView mTvPayStatisticsThirtydays;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeSellStatisticsTab(1);
        changePayStatisticsTab(1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setClickable(false);
        chart.setScaleEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    switch (payIndex) {
                        case 1:
                            return mHomeDataBean.getStore().getOrder_price_chart().getToday().getTime().get((int) value);
                        case 2:
                            return mHomeDataBean.getStore().getOrder_price_chart().getYesterday().getTime().get((int) value);
                        case 3:
                            return mHomeDataBean.getStore().getOrder_price_chart().getSevenday().getTime().get((int) value);
                        case 4:
                            return mHomeDataBean.getStore().getOrder_price_chart().getThirtyday().getTime().get((int) value);
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ((int) value) + "";
            }
        });
        xAxis.setTextColor(Color.parseColor("#999999"));
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = chart.getAxisLeft();
        //重置所有限制线,以避免重叠线
        leftAxis.removeAllLimitLines();
        leftAxis.setLabelCount(4, false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(Color.parseColor("#999999"));
        chart.getAxisRight().setEnabled(false);

        // set data
        chart.setData(generateDataLine(mHomeDataBean, payIndex));

        // holder.chart.invalidate();
        chart.animateX(750);
    }

    public void getOperationData() {

        RetrofitAPIManager.create(OperationService.class).storeOperateData(new BasePostBean())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<HomeDataBean>(false) {
            @Override
            protected void onApiComplete() {
            }

            @Override
            protected void onSuccees(BaseEntity<HomeDataBean> t) throws Exception {
                mHomeDataBean = t.getData();
                changePayStatisticsTab(payIndex);
                chart.setData(generateDataLine(mHomeDataBean, payIndex));
                chart.animateX(750);
                changeSellStatisticsTab(sellIndex);
                showOrderStatusData();
            }
        });
    }


    private LineData generateDataLine(HomeDataBean homeDataBean, int index) {
        List<String> stringList = new ArrayList<>();
        ArrayList<Entry> values1 = new ArrayList<>();
        try {
            switch (index) {
                case 1:
                    stringList = homeDataBean.getStore().getOrder_price_chart().getToday().getIncome();
                    break;
                case 2:
                    stringList = homeDataBean.getStore().getOrder_price_chart().getYesterday().getIncome();
                    break;
                case 3:
                    stringList = homeDataBean.getStore().getOrder_price_chart().getSevenday().getIncome();
                    break;
                case 4:
                    stringList = homeDataBean.getStore().getOrder_price_chart().getThirtyday().getIncome();
                    break;
                default:
                    break;
            }
            if (stringList != null) {
                for (int i = 0; i < stringList.size(); i++) {
                    values1.add(new Entry(i, Float.parseFloat(stringList.get(i).replaceAll(",",""))));
                }
            }
        } catch (Exception e) {
            for (int i = 0; i < 7; i++) {
                values1.add(new Entry(i, 0));
            }
            e.printStackTrace();
        }

        LineDataSet d1 = new LineDataSet(values1, "");
        d1.setLineWidth(1.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighlightEnabled(false);
        d1.setDrawValues(false);
        d1.setDrawCircles(false);
        d1.setDrawFilled(true);
        d1.setMode(LineDataSet.Mode.LINEAR);
        d1.setColor(Color.rgb(23, 159, 255));
        d1.setCircleColor(Color.rgb(23, 159, 255));
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
            d1.setFillDrawable(drawable);
        } else {
            d1.setFillColor(Color.BLACK);
        }
        return new LineData(sets);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTvStoreName.setText(MchInfoLogic.getMchInfoBean().getMch_info().getMch_name());
        mIvLogo.loadCircle(MchInfoLogic.getMchInfoBean().getMch_info().getLogo());
        mDepostListBean = DepostListBeanLogic.getDepostListBean();
        getDepositlist();
        if (DepostListBeanLogic.isDepostPassEmpty()) {
            mIvDepost.setSelected(false);
            if (DepostListBeanLogic.isDepostReviewsEmpty()) {
                mTvDepost.setText("保证金");
            } else {
                mTvDepost.setText("审核中");
            }
        } else {
            mIvDepost.setSelected(true);
            mTvDepost.setText("保证金");
        }
        //获取数据
        getOperationData();
    }

    public void getDepositlist() {
        RetrofitAPIManager.create(OperationService.class).getDepositList(new BasePostBean())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<DepostListBean>(false) {
            @Override
            protected void onApiComplete() {
            }

            @Override
            protected void onSuccees(BaseEntity<DepostListBean> t) throws Exception {
                DepostListBeanLogic.saveDepostListBean(t.getData());
                if (t.getData().getPass() != null && t.getData().getPass().size() > 0) {
                    mIvDepost.setSelected(true);
                } else {
                    mIvDepost.setSelected(false);
                }
            }
        });
    }

    public void showSellStatistics() {
        try{
            switch (sellIndex) {
                case 1:
                    mTvSalesOrders.setText(mHomeDataBean.getStore().getSell_statistics_today().getOrder_num()+"");
                    mTvSalesMoney.setText(mHomeDataBean.getStore().getSell_statistics_today().getOrder_price());
                    break;
                case 2:
                    mTvSalesOrders.setText(mHomeDataBean.getStore().getSell_statistics_yesterday().getOrder_num()+"");
                    mTvSalesMoney.setText(mHomeDataBean.getStore().getSell_statistics_yesterday().getOrder_price());

                    break;
                case 3:
                    mTvSalesOrders.setText(mHomeDataBean.getStore().getSell_statistics_sevendays().getOrder_num()+"");
                    mTvSalesMoney.setText(mHomeDataBean.getStore().getSell_statistics_sevendays().getOrder_price());

                    break;
                case 4:
                    mTvSalesOrders.setText(mHomeDataBean.getStore().getSell_statistics_thirtydays().getOrder_num()+"");
                    mTvSalesMoney.setText(mHomeDataBean.getStore().getSell_statistics_thirtydays().getOrder_price());
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            mTvSalesOrders.setText("0");
            mTvSalesMoney.setText("0.0");

            e.printStackTrace();
        }

    }

    public void showOrderStatusData(){
        mTvUnpayOrders.setText(mHomeDataBean.getStore().getOrder_statistics().getWait_pay_orders()+"");
        mTvUnshippingOrders.setText(mHomeDataBean.getStore().getOrder_statistics().getWait_send_orders()+"");
        mTvProblemOrders.setText(mHomeDataBean.getStore().getOrder_statistics().getRefunding_orders()+"");
    }
    @OnClick(R.id.ll_notice)
    public void onMLlNoticeClicked() {
        startActivity(new Intent(getContext(), NoticeListActivity.class));
    }

    @OnClick(R.id.ll_reviews)
    public void onMLlReviewsClicked() {

    startActivity(new Intent(getContext(), EvaluationActivity.class));
    }

    @OnClick(R.id.ll_store_settings)
    public void onMLlStoreSettingsClicked() {
        startActivity(new Intent(getContext(), StoreSetUpActivity.class));
    }

    @OnClick(R.id.ll_finance)
    public void onMLlFinanceClicked() {
        startActivity(new Intent(getContext(), FinanceSettlementActivity.class));
    }

    @OnClick(R.id.ll_depost)
    public void onMLlDepostClicked() {
        if (DepostListBeanLogic.isDepostPassEmpty() && DepostListBeanLogic.isDepostReviewsEmpty()) {
            startActivity(new Intent(getActivity(), DepositAgreementActivity.class));
        } else {
            startActivity(new Intent(getActivity(), MyDepositActivity.class));
        }
    }

    @OnClick(R.id.tv_sales_statistics)
    public void onMTvSalesStatisticsClicked() {
        changeSellStatisticsTab(1);
    }

    @OnClick(R.id.tv_sales_today)
    public void onMTvSalesTodayClicked() {
        changeSellStatisticsTab(2);
    }

    @OnClick(R.id.tv_sales_yesterday)
    public void onMTvSalesYesterdayClicked() {
        changeSellStatisticsTab(3);
    }

    @OnClick(R.id.tv_sales_serven_days)
    public void onMTvSalesServenDaysClicked() {
        changeSellStatisticsTab(4);
    }

    @OnClick(R.id.tv_sales_thirty_days)
    public void onMTvSalesThirtyDaysClicked() {
        changeSellStatisticsTab(5);
    }

    /**
     * 改变销售统计tab
     *
     * @param index
     */
    public void changeSellStatisticsTab(int index) {
        switch (index) {
            case 1:
                mTvSalesStatistics.setSelected(true);
                mTvSalesToday.setSelected(false);
                mTvSalesYesterday.setSelected(false);
                mTvSalesServenDays.setSelected(false);
                mTvSalesThirtyDays.setSelected(false);
                break;
            case 2:
                mTvSalesStatistics.setSelected(false);
                mTvSalesToday.setSelected(true);
                mTvSalesYesterday.setSelected(false);
                mTvSalesServenDays.setSelected(false);
                mTvSalesThirtyDays.setSelected(false);
                break;
            case 3:
                mTvSalesStatistics.setSelected(false);
                mTvSalesToday.setSelected(false);
                mTvSalesYesterday.setSelected(true);
                mTvSalesServenDays.setSelected(false);
                mTvSalesThirtyDays.setSelected(false);
                break;
            case 4:
                mTvSalesStatistics.setSelected(false);
                mTvSalesToday.setSelected(false);
                mTvSalesYesterday.setSelected(false);
                mTvSalesServenDays.setSelected(true);
                mTvSalesThirtyDays.setSelected(false);
                break;
            case 5:
                mTvSalesStatistics.setSelected(false);
                mTvSalesToday.setSelected(false);
                mTvSalesYesterday.setSelected(false);
                mTvSalesServenDays.setSelected(false);
                mTvSalesThirtyDays.setSelected(true);
                break;
            default:
                break;
        }
        sellIndex = index;
        showSellStatistics();
    }


    /**
     * 改变支付金额统计tab
     *
     * @param index
     */
    public void changePayStatisticsTab(int index) {
        switch (index) {
            case 1:
                mTvPayStatisticsToday.setSelected(true);
                mTvPayStatisticsYesterday.setSelected(false);
                mTvPayStatisticsSevendays.setSelected(false);
                mTvPayStatisticsThirtydays.setSelected(false);
                break;
            case 2:
                mTvPayStatisticsToday.setSelected(false);
                mTvPayStatisticsYesterday.setSelected(true);
                mTvPayStatisticsSevendays.setSelected(false);
                mTvPayStatisticsThirtydays.setSelected(false);
                break;
            case 3:
                mTvPayStatisticsToday.setSelected(false);
                mTvPayStatisticsYesterday.setSelected(false);
                mTvPayStatisticsSevendays.setSelected(true);
                mTvPayStatisticsThirtydays.setSelected(false);
                break;
            case 4:
                mTvPayStatisticsToday.setSelected(false);
                mTvPayStatisticsYesterday.setSelected(false);
                mTvPayStatisticsSevendays.setSelected(false);
                mTvPayStatisticsThirtydays.setSelected(true);
                break;
            default:
                break;
        }

        payIndex = index;
        chart.setData(generateDataLine(mHomeDataBean, payIndex));
        chart.animateX(750);
    }

    @OnClick(R.id.ll_unpay_orders)
    public void onMLlUnpayOrdersClicked() {
        EventBus.getDefault().post(new MessageEvent(UNPAYORDER));
    }

    @OnClick(R.id.ll_unshipping_orders)
    public void onMLlUnshippingOrdersClicked() {
        EventBus.getDefault().post(new MessageEvent(UNSHIPPINGORDER));
    }

    @OnClick(R.id.ll_problem_orders)
    public void onMLlProblemOrdersClicked() {
        EventBus.getDefault().post(new MessageEvent(UNNORMALORDER));
    }

    @OnClick(R.id.tv_pay_statistics_today)
    public void onMTvPayStatisticsTodayClicked() {
        changePayStatisticsTab(1);
    }

    @OnClick(R.id.tv_pay_statistics_yesterday)
    public void onMTvPayStatisticsYesterdayClicked() {
        changePayStatisticsTab(2);
    }

    @OnClick(R.id.tv_pay_statistics_sevendays)
    public void onMTvPayStatisticsSevendaysClicked() {
        changePayStatisticsTab(3);
    }

    @OnClick(R.id.tv_pay_statistics_thirtydays)
    public void onMTvPayStatisticsThirtydaysClicked() {
        changePayStatisticsTab(4);
    }
}
