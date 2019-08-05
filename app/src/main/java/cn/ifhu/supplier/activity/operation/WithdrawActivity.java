package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;

public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;
    @BindView(R.id.tv_y_jiesuan)
    TextView tvYJiesuan;
    double y_jiesuan = 0;//int类型数据返回(从上一个页面抛出、这个页面首先就得接收)
    String bank_type;//string类型
    String account_number;
    boolean withdraw = false;
    @BindView(R.id.rl_bank)
    RelativeLayout rlBank;
    @BindView(R.id.iv_arrow_right)
    ImageView ivArrowRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        ButterKnife.bind(this);
        initData();                     //下面的方法调用上来
        tvTitle.setText("提现");
    }


    public void initData() {
        y_jiesuan = getIntent().getDoubleExtra("jiesuan", 0);
        //接口命名 = getIngent().getingExtra(ing类型)("自定义名称",默认为0)
        bank_type = getIntent().getStringExtra("bank_type");
        //接口命名 = getIntent().getStringExtra("自定义名称")
        account_number = getIntent().getStringExtra("account_number");
        tvYJiesuan.setText(y_jiesuan + "");
        //设置它显示出来、强制转换为String类型
        tvBankType.setText(bank_type);
        tvBankNumber.setText(account_number);
        if (bank_type == null || StringUtils.isEmpty(bank_type)) {
            withdraw = false;
            tvBankType.setVisibility(View.GONE);
            tvBankNumber.setText("请添加银行卡");
            ivArrowRight.setVisibility(View.VISIBLE);
        } else {
            withdraw = true;
            ivArrowRight.setVisibility(View.INVISIBLE);
        }
    }

    public void withdrawMoney() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).pdCashAdd(MchInfoLogic.getMchId() + "", etMoney.getText().toString())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                startActivity(new Intent(WithdrawActivity.this, WithdrawSuccessActivity.class));
            }

        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (withdraw){
           checkContent();
        }else {
            ToastHelper.makeText("请添加银卡").show();
        }

    }

    public void checkContent(){
        if (StringUtils.isEmpty(etMoney.getText().toString())) {
            ToastHelper.makeText("请输入提现金额").show();
        } else if (Double.parseDouble(etMoney.getText().toString()) > y_jiesuan){
            ToastHelper.makeText("输入金额不可大于可提现金额").show();
        }else {
            withdrawMoney();
        }
    }
    @OnClick(R.id.tv_money)
    public void onTvMoneyClicked() {
        etMoney.setText(y_jiesuan + "");
    }

    @OnClick(R.id.rl_bank)
    public void onRlBankClicked() {
        if (withdraw) {

        } else {
            startActivity(new Intent(WithdrawActivity.this, ManageBankActivity.class));
        }
    }
}
