package cn.ifhu.supplier.activity.orders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.model.newbean.post.ShippingPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OrderService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.Constants;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class ShippingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.swh_express)
    Switch mSwhExpress;
    @BindView(R.id.et_express_name)
    EditText mEtExpressName;
    @BindView(R.id.ll_express)
    LinearLayout mLlExpress;
    @BindView(R.id.et_express_no)
    EditText mEtExpressNo;
    @BindView(R.id.ll_express_no)
    LinearLayout mLlExpressNo;
    @BindView(R.id.et_express_remark)
    EditText mEtExpressRemark;
    @BindView(R.id.ll_express_remark)
    LinearLayout mLlExpressRemark;
    @BindView(R.id.ll_express_info)
    LinearLayout mLlExpressInfo;
    @BindView(R.id.swh_no_express)
    Switch mSwhNoExpress;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.ll_remark)
    LinearLayout mLlRemark;
    @BindView(R.id.tv_shipping)
    TextView mTvShipping;
    /**
     * 是否需要物流 1为需要，0为不需要
     */
    int is_express = 1;
    public static final String ORDERID = "ORDERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        ButterKnife.bind(this);
        mTvTitle.setText("设置发货");


        // 添加监听
        mSwhExpress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mSwhNoExpress.setChecked(!isChecked);
            if (isChecked) {
                is_express = 1;
                mLlRemark.setVisibility(View.GONE);
                mLlExpressInfo.setVisibility(View.VISIBLE);
            }
        });

        mSwhNoExpress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mSwhExpress.setChecked(!isChecked);
            if (isChecked) {
                is_express = 0;
                mLlRemark.setVisibility(View.VISIBLE);
                mLlExpressInfo.setVisibility(View.GONE);
            }
        });

    }

    public void shipping() {
        setLoadingMessageIndicator(true);
        ShippingPostBean shippingPostBean = new ShippingPostBean();
        shippingPostBean.setIs_express(is_express);
        shippingPostBean.setOrder_id(getIntent().getIntExtra(ORDERID,0));
        if (is_express == 1){
            shippingPostBean.setExpress(mEtExpressName.getText().toString());
            shippingPostBean.setExpress_no(mEtExpressNo.getText().toString());
            shippingPostBean.setWords(mEtExpressRemark.getText().toString());
        }else {
            shippingPostBean.setWords(mEtRemark.getText().toString());
        }

        RetrofitAPIManager.create(OrderService.class).deliveryGoods(shippingPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                EventBus.getDefault().post(new MessageEvent(Constants.ORDERCOMING));
                ToastHelper.makeText(t.getMessage()).show();
                finish();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onMIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_shipping)
    public void onMTvShippingClicked() {
        shipping();
    }
}
