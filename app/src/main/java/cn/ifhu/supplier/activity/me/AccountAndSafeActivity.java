package cn.ifhu.supplier.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.login.LoginActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

/**
 * @author fuhongliang
 */
public class AccountAndSafeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_change_password)
    RelativeLayout rlChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountandsafe);
        ButterKnife.bind(this);
        tvTitle.setText("账号与安全");
        tvPhone.setText(MchInfoLogic.getMchTel() + "");
        tvName.setText(MchInfoLogic.getMchRealname());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_change_password)
    public void onRlChangePasswordClicked() {
        startActivity(new Intent(AccountAndSafeActivity.this, ChangePassWordActivity.class));
    }

    @OnClick(R.id.rl_drop_out)
    public void onMRlDropOutClicked() {
        DialogUtils.showConfirmDialog("温馨提示", "是否确定退出登录", "取消", "确定", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {

            }

            @Override
            public void ok() {
                MchInfoLogic.loginOut();
                clearAllAndStart(LoginActivity.class);
            }
        });
    }
}
