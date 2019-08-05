package cn.ifhu.supplier.activity.me;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.post.GetModifyPwdSmsBean;
import cn.ifhu.supplier.model.newbean.post.ModifyPwdBean;
import cn.ifhu.supplier.net.MeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class ChangePassWordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private Timer mTimer;
    private int mOriSecond = 5;
    private int mCurSecond;
    private int mCodeLength = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        tvTitle.setText("修改密码");
        tvPhone.setText(MchInfoLogic.getMchTel());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        setLoadingMessageIndicator(true);
        GetModifyPwdSmsBean getModifyPwdSmsBean = new GetModifyPwdSmsBean();
        getModifyPwdSmsBean.setUsername(MchInfoLogic.getMchTel());
        RetrofitAPIManager.create(MeService.class).getCaptchaModifyPwd(getModifyPwdSmsBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                showCountDown();
            }
        });
    }

    private void showCountDown() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(
                () -> {
                    mCurSecond--;
                    if (mCurSecond < 1) {
                        runOnUiThread(() -> {
                            tvGetCode.setText("获取验证码");
                            tvGetCode.setClickable(true);
                            executor.shutdown();
                        });
                        mCurSecond = 60;
                    } else {
                        String sencond = getString(R.string.get_code_again, mCurSecond);
                        runOnUiThread(() -> {
                            tvGetCode.setText(sencond);
                            tvGetCode.setClickable(false);
                        });
                    }
                },
                0,
                1000,
                TimeUnit.MILLISECONDS);
    }

    @OnClick(R.id.btn_ok)
    public void onBtnOkClicked() {
        if (checkPassWord()) {
            setLoadingMessageIndicator(true);
            ModifyPwdBean modifyPwdBean = new ModifyPwdBean();
            modifyPwdBean.setCode(code);
            modifyPwdBean.setPassword(newPassWord);
            modifyPwdBean.setPhone(MchInfoLogic.getMchTel());
            RetrofitAPIManager.create(MeService.class).modifyPwd(modifyPwdBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity t) throws Exception {
                    ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                    finish();
                }
            });
        }
    }

    String newPassWord = "";
    String newPassWordAgain = "";
    String code = "";

    public boolean checkPassWord() {
        newPassWord = etNewPassword.getText().toString().trim();
        newPassWordAgain = etPasswordAgain.getText().toString().trim();
        code = etCode.getText().toString().trim();

        if (StringUtils.isEmpty(code)) {
            return false;
        }

        if (StringUtils.isEmpty(newPassWord) || newPassWord.length() < mCodeLength) {
            return false;
        }

        if (!newPassWord.equals(newPassWordAgain)) {
            return false;
        }
        return true;
    }

}
