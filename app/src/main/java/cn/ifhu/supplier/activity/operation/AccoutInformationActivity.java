package cn.ifhu.supplier.activity.operation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.AccoutInformationBean;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class AccoutInformationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;

    @BindView(R.id.tv_branch)
    TextView tvBranch;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        ButterKnife.bind(this);
        tvTitle.setText("账户信息");
        bankInInformation();
    }

    public void bankInInformation() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).bankAccountInfo(MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<AccoutInformationBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<AccoutInformationBean> t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                tvAccountName.setText(t.getData().getAccount_name());
                tvBankNumber.setText(t.getData().getAccount_number());
                tvBankType.setText(t.getData().getBank_type());
                tvBranch.setText(t.getData().getBank_name());
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBankClicked() {
        finish();
    }
}
