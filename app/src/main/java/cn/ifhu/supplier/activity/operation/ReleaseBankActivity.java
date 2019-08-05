package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
import cn.ifhu.supplier.model.bean.ReleaseBankBean;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import io.reactivex.Observer;

/**
 * @author fuhongliang
 */
public class ReleaseBankActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_bank_information)
    RelativeLayout rlBankInformation;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_bank);
        ButterKnife.bind(this);
        tvTitle.setText("银行卡管理");
        RekeaseBankinformation();
    }

    public void RekeaseBankinformation() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).bankAccountList(MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ReleaseBankBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ReleaseBankBean> t) throws Exception {
                tvBank.setText(t.getData().getBank_type());
                tvName.setText(t.getData().getAccount_name());
                String bankNumber = "**** **** **** **** ";
                tvBankNumber.setText(bankNumber+getStringLastFourLetters(t.getData().getAccount_number()));

            }
        });
    }



    public String getStringLastFourLetters(String bankNumber){
        if (StringUtils.isEmpty(bankNumber) || bankNumber.length()<4){
            return "****";
        }else {
            return bankNumber.substring(bankNumber.length()-4,bankNumber.length());
        }
    }

    //解除绑定银行卡
    public void UntiedBank() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delBankAccount(MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);

            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                startActivity(new Intent(ReleaseBankActivity.this, FinanceActivity.class));
            }


        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_bank_information)
    public void onRlBankInformationClicked() {
        startActivity(new Intent(ReleaseBankActivity.this, AccoutInformationActivity.class));

    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        UntiedBank();
    }
}
