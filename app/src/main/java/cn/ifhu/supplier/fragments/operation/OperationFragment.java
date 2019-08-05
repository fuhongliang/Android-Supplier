package cn.ifhu.supplier.fragments.operation;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.WebViewActivity;
import cn.ifhu.supplier.activity.join.DepositAgreementActivity;
import cn.ifhu.supplier.activity.join.MyDepositActivity;
import cn.ifhu.supplier.activity.me.AboutUsActivity;
import cn.ifhu.supplier.activity.me.AccountAndSafeActivity;
import cn.ifhu.supplier.activity.me.StoreSetUpActivity;
import cn.ifhu.supplier.activity.notice.NoticeListActivity;
import cn.ifhu.supplier.activity.operation.ProductManageActivity;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.DepostListBean;
import cn.ifhu.supplier.model.newbean.data.NewOperationBean;
import cn.ifhu.supplier.model.newbean.data.StoreDataBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DepostListBeanLogic;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

/**
 * @author tony
 */
public class OperationFragment extends BaseFragment {

    @BindView(R.id.ll_OperationProduct)
    LinearLayout llOperationProduct;
    @BindView(R.id.ll_operation_data)
    LinearLayout llOperationData;
    Unbinder unbinder;
    @BindView(R.id.tv_earn_today)
    TextView tvEarnToday;
    @BindView(R.id.tv_orders_today)
    TextView tvOrdersToday;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_30_ordernum)
    TextView tv30Ordernum;
    @BindView(R.id.tv_30_orderamount)
    TextView tv30Orderamount;
    StoreDataBean mStoreDataBean;
    @BindView(R.id.ll_finance)
    LinearLayout llFinance;
    @BindView(R.id.tv_order_count)
    TextView tvOrderCount;

    NewOperationBean operationBean;
    @BindView(R.id.iv_depost)
    ImageView mIvDepost;
    DepostListBean mDepostListBean;
    @BindView(R.id.tv_depost)
    TextView mTvDepost;

    public static OperationFragment newInstance() {
        return new OperationFragment();
    }


    public OperationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_OperationProduct)
    public void onLlOperationProductClicked() {
        startActivity(new Intent(getActivity(), ProductManageActivity.class));
    }


    public void getOperationData() {

        RetrofitAPIManager.create(OperationService.class).storeOperateData(new BasePostBean())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<StoreDataBean>(false) {
            @Override
            protected void onApiComplete() {
            }

            @Override
            protected void onSuccees(BaseEntity<StoreDataBean> t) throws Exception {
                initData(t.getData());
            }
        });
    }

    public void initData(StoreDataBean storeDataBean) {
        mStoreDataBean = storeDataBean;

        tvEarnToday.setText(mStoreDataBean.getStore().getTotal_income_day());
        tvOrdersToday.setText(mStoreDataBean.getStore().getOrder_num_day() + "");

        tvOrderCount.setText(mStoreDataBean.getStore().getTotal_order_count() + "");
        tvGoodsNum.setText(mStoreDataBean.getStore().getTotal_goods_count() + "");

        tv30Ordernum.setText(mStoreDataBean.getStore().getOrder_num_thirty() + "");
        tv30Orderamount.setText(mStoreDataBean.getStore().getTotal_income_thirty());

    }

    @OnClick(R.id.ll_operation_data)
    public void onViewClicked() {
        WebViewActivity.start(getContext(), mStoreDataBean.getStore().getOperate_data_url(), "经营数据");
    }

    @Override
    public void onResume() {
        super.onResume();
        mDepostListBean = DepostListBeanLogic.getDepostListBean();
        getOperationData();
        getDepositlist();
        if (DepostListBeanLogic.isDepostPassEmpty()) {
            mIvDepost.setSelected(false);
            if (DepostListBeanLogic.isDepostReviewsEmpty()) {
                mTvDepost.setText("保证金");
            }else{
                mTvDepost.setText("审核中");
            }
        } else {
            mIvDepost.setSelected(true);
            mTvDepost.setText("保证金");
        }
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

    @OnClick(R.id.ll_store_settings)
    public void onLlStoreSettingsClicked() {
        startActivity(new Intent(getContext(), StoreSetUpActivity.class));
    }

    @OnClick(R.id.rl_account_security)
    public void onRlAccountSecurityClicked() {
        startActivity(new Intent(getContext(), AccountAndSafeActivity.class));
    }

    @OnClick(R.id.rl_notices)
    public void onRlMeAddressClicked() {
        startActivity(new Intent(getContext(), NoticeListActivity.class));
    }

    @OnClick(R.id.rl_feedback)
    public void onRlFeedbackClicked() {
//        startActivity(new Intent(getContext(), FeedBackActivity.class));
        DialogUtils.showConfirmDialog("温馨提示", "是否拨打平台客服电话", "取消", "确定", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mStoreDataBean.getStore().getContact_platform_tel());
                intent.setData(data);
                getActivity().startActivity(intent);
            }
        });
    }

    @OnClick(R.id.rl_about_us)
    public void onRlAboutUsClicked() {
        startActivity(new Intent(getContext(), AboutUsActivity.class));
    }

    @OnClick(R.id.ll_depost)
    public void onMLlDepostClicked() {
        if (DepostListBeanLogic.isDepostPassEmpty() && DepostListBeanLogic.isDepostReviewsEmpty()) {
            startActivity(new Intent(getActivity(), DepositAgreementActivity.class));
        } else {
            startActivity(new Intent(getActivity(), MyDepositActivity.class));
        }
    }
}
