package cn.ifhu.supplier.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
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

public class BankWithdrawActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.et_nick_name)
    EditText etNickName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bank_withdraw);
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
        if (StringUtils.isEmpty(etMoney.getText().toString())) {
            ToastHelper.makeText("请输入提现金额").show();
            return false;
        }
        if (StringUtils.isEmpty(etAccount.getText().toString())) {
            ToastHelper.makeText("请输入银行卡号").show();
            return false;
        }
        if (StringUtils.isEmpty(etBankName.getText().toString())) {
            ToastHelper.makeText("请输入开户行名称").show();
            return false;
        }
        if (StringUtils.isEmpty(etNickName.getText().toString())) {
            ToastHelper.makeText("请输入开户人姓名").show();
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
            withdrawPostBean.setMoney(Double.valueOf(etMoney.getText().toString()));
            withdrawPostBean.setType(3);
            WithdrawPostBean.TypeData tapeData = new WithdrawPostBean.TypeData();
            tapeData.setAccount(etAccount.getText().toString());
            tapeData.setBank_name(etBankName.getText().toString());
            tapeData.setNickname(etNickName.getText().toString());
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
