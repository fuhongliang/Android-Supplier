package cn.ifhu.supplier.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.MainActivity;
import cn.ifhu.supplier.activity.WebViewActivity;
import cn.ifhu.supplier.activity.join.JoinInActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.jpush.android.api.JPushInterface;

import static cn.ifhu.supplier.jpush.JpushUtil.SEQUENCE;

/**
 * @author fuhongliang
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_get_captcha_login)
    TextView tvGetCaptchaLogin;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.btn_login)
    TextView mBtnLogin;
    @BindView(R.id.tv_password)
    TextView mTvPassword;

    boolean isPassWordLogin = true;
    LoginContract.Presenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new LoginPresenter(this);
        mPresenter.start();
        mBtnLogin.setEnabled(checkPhoneNumber());
        if (MchInfoLogic.isLogin()) {
            loginSuccess();
        }
        setTextChangedListener();
    }

    public void setTextChangedListener() {
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnLogin.setEnabled(checkPhoneNumber());
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnLogin.setEnabled(checkPhoneNumber());
            }
        });
    }

    public boolean checkPhoneNumber() {
        if (StringUtils.isEmpty(etPhone.getText().toString())) {
            return false;
        }
        return !StringUtils.isEmpty(etPassword.getText().toString());
    }

    @Override
    public void initData() {
        showLastTimePhoneNumber();
    }

    @Override
    public void showLastTimePhoneNumber() {

    }

    @Override
    public void showToast(String msg) {
        ToastHelper.makeText(msg).show();
    }

    @Override
    public void loginSuccess() {
        //极光 JPush 设置别名

        JPushInterface.setAlias(getApplicationContext(), SEQUENCE, MchInfoLogic.getMchTel());
        Logger.d("JIGUANG-JPush","账号登录成功 设置别名");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void startTimer() {
        tvGetCaptchaLogin.setEnabled(false);
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCaptchaLogin.setText((millisUntilFinished / 1000) + "秒后重试");
            }

            @Override
            public void onFinish() {
                tvGetCaptchaLogin.setText("获取验证码");
                tvGetCaptchaLogin.setEnabled(true);
            }
        }.start();
    }

    @Override
    public void showGetMessageBtn(boolean show) {
        if (show) {
            mTvRightText.setText("密码登录");
            mTvTitle.setText("验证码登录");
            mTvPassword.setText("验证码");
            etPassword.setHint("请输入验证码");
            tvGetCaptchaLogin.setVisibility(View.VISIBLE);
        } else {
            mTvTitle.setText("密码登录");
            mTvRightText.setText("验证码登录");
            mTvPassword.setText(getResources().getString(R.string.password));
            etPassword.setHint("请输入密码");
            tvGetCaptchaLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @OnClick(R.id.ll_agreement)
    public void onLlAgreementClicked() {
        WebViewActivity.startLoadAssetsHtml(LoginActivity.this, "protocol.html", "服务协议");
    }

    @OnClick(R.id.tv_get_captcha_login)
    public void onGetCaptchaLoginClick() {
        mPresenter.getCaptchaLogin(etPhone.getText().toString());
    }

    @OnClick(R.id.tv_right_text)
    public void onMTvRightTextClicked() {
        if (isPassWordLogin) {
            mPresenter.showMessageBtn(true);
        } else {
            mPresenter.showMessageBtn(false);
        }
        isPassWordLogin = !isPassWordLogin;
    }

    @OnClick(R.id.btn_login)
    public void onMBtnLoginClicked() {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (mPresenter.checkPhoneNumber(phone, password)) {
            mPresenter.loginWithCode(phone, password, isPassWordLogin);
        }
    }

    @OnClick(R.id.tv_join)
    public void onMTvJoinClicked() {
        goToActivity(JoinInActivity.class);
    }
}
