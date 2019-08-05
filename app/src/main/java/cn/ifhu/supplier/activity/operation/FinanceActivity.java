package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.financial.BillsListActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.FinanceBean;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;

/**
 * @author fuhongliang
 */
public class FinanceActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rule)
    TextView rule;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.iv_tongzhi)
    ImageView ivTongzhi;
    @BindView(R.id.rl_add_bank)
    RelativeLayout rlAddBank;
    @BindView(R.id.tv_not_settlement)
    TextView tvNotSettlement;
    @BindView(R.id.rl_financial_bill)
    RelativeLayout rlFinancialBill;
    @BindView(R.id.lv_finance)
    ListView lvFinance;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;
    @BindView(R.id.tv_add_time)
    TextView tvAddTime;



    private double y_jiesuan = 0;//声明一个int类型(数据需要跳转到其他页面)
    private String bank_type;//声明一个string类型
    private String account_number;

    boolean hasBankCard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_settlement);
        ButterKnife.bind(this);
        tvTitle.setText("财务结算");
    }


    public void bankAccount() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).storeJiesuan(MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<FinanceBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<FinanceBean> t) throws Exception {

                tvSettlement.setText(t.getData().getY_jiesuan() + "");
                tvNotSettlement.setText(t.getData().getD_jiesuan() + "");
                String BankNumber = "";
                tvBankNumber.setText(BankNumber + getStringLastFourLetters(t.getData().getAccount().getAccount_number()));
                tvBankType.setText(t.getData().getAccount().getBank_type());
                tvAddTime.setText(t.getData().getAddtime());
                if (t.getData().getAddtime()== null || StringUtils.isEmpty(t.getData().getAddtime())) {
                    ivTongzhi.setVisibility(View.INVISIBLE);
                } else {
                    ivTongzhi.setVisibility(View.VISIBLE);
                }
                y_jiesuan = t.getData().getY_jiesuan();//上面声明类型、这里就需要去调用它的接口、获取数据赋值给ing类型数据
                bank_type = t.getData().getAccount().getBank_type();//String类型
                account_number = t.getData().getAccount().getAccount_number();
                if (t.getData().getAccount() == null || StringUtils.isEmpty(t.getData().getAccount().getAccount_number())) {
                    hasBankCard = false;
                    tvBankType.setText("请添加银行卡");
                } else {
                    hasBankCard = true;
                }
            }
        });
    }

    public String getStringLastFourLetters(String bankNumber) {
        if (StringUtils.isEmpty(bankNumber) || bankNumber.length() < 4) {
            return "";
        } else {
            return bankNumber.substring(bankNumber.length() - 4, bankNumber.length());
        }
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @OnClick(R.id.rl_add_bank)
    public void onRlAddBankClicked() {
        if (hasBankCard) {
            startActivity(new Intent(FinanceActivity.this, ReleaseBankActivity.class));
        } else {
            startActivity(new Intent(FinanceActivity.this, ManageBankActivity.class));
        }
    }

    @OnClick(R.id.rl_financial_bill)
    public void onRlFinancialBillClicked() {
        startActivity(new Intent(FinanceActivity.this, BillsListActivity.class));
    }

    @OnClick(R.id.tv_withdraw)
    public void onTvWithdrawClicked() {
        Intent intent = new Intent(FinanceActivity.this, WithdrawActivity.class);//首先要new一个intent、然后从哪个页面跳转到哪个页面
        intent.putExtra("jiesuan", y_jiesuan);//如需要携带数据跳转页面、不能直接start跳转
        intent.putExtra("bank_type", bank_type);//intent.putExtra是固定的("名字自定义",这个名称需要跟上面声明的一样)
        intent.putExtra("account_number", account_number);
        startActivity(intent);//start刚才new的intent
    }


    @Override
    protected void onResume() {
        super.onResume();
        bankAccount();

    }
}
