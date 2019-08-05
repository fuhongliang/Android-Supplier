package cn.ifhu.supplier.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.post.WithdrawPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.HomeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import io.reactivex.Observer;

public class WeChatWithdrawActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_withdraw)
    LinearLayout llWithdraw;
    @BindView(R.id.transfer_information)
    TextView transferInformation;
    @BindView(R.id.ll_we_chat)
    LinearLayout llWeChat;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.et_withdrawal_amount)
    EditText etWithdrawalAmount;
    @BindView(R.id.et_we_chat_number)
    EditText etWeChatNumber;
    @BindView(R.id.et_we_chat_name)
    EditText etWeChatName;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_we_chat_withdraw);
        ButterKnife.bind(this);
        tvTitle.setText("提现");
        tvBalance.setText("￥" + getDATA());
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public boolean checkEmpty() {
        if (StringUtils.isEmpty(etWithdrawalAmount.getText().toString())) {
            ToastHelper.makeText("请输入提现金额").show();
            return false;
        }
        if (StringUtils.isEmpty(etWeChatName.getText().toString())) {
            ToastHelper.makeText("请输入微信号").show();
            return false;
        }
        if (StringUtils.isEmpty(etWeChatNumber.getText().toString())) {
            ToastHelper.makeText("请输入微信昵称").show();
            return false;
        }
        return true;
    }

    /**
     * 提现申请接口联调
     */
    public void getApplyCashOut() {
        if (checkEmpty()) {
            setLoadingMessageIndicator(true);
            WithdrawPostBean withdrawPostBean = new WithdrawPostBean();
            withdrawPostBean.setMoney(Double.valueOf(etWithdrawalAmount.getText().toString()));
            withdrawPostBean.setType(1);
            WithdrawPostBean.TypeData tapeData = new WithdrawPostBean.TypeData();
            tapeData.setAccount(etWeChatName.getText().toString());
            tapeData.setNickname(etWeChatNumber.getText().toString());
            withdrawPostBean.setType_data(tapeData);
            RetrofitAPIManager.create(HomeService.class).applyCashOut(withdrawPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity t) throws Exception {
                    ToastHelper.makeText(t.getMessage()).show();
                    finish();
                }
            });
        }
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
            getApplyCashOut();
    }
}
