package cn.ifhu.supplier.activity.login;


import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.UserServiceBean;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UserService;
import cn.ifhu.supplier.model.newbean.data.MchBean;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * 登录逻辑
 * @author fuhongliang
 */
public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View loginView;

    @SuppressLint("RestrictedApi")
    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = checkNotNull(loginView, "loginView cannot be null!");
        this.loginView.setPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void start() {
        loginView.initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean checkPhoneNumber(String phoneNo, String passWord) {
        if (StringUtils.isEmpty(phoneNo)) {
            loginView.showToast("请输入手机号码！");
            return false;
        }
        if (StringUtils.isEmpty(passWord)) {
            loginView.showToast("请输入密码！");
            return false;
        }
        return true;
    }

    /**
     * 登录请求
     * @param phone 手机号
     * @param code 密码或验证码
     */
    @Override
    public void loginWithCode(String phone, String code,boolean isPassWordLogin) {
        loginView.setLoadingMessageIndicator(true);
        UserServiceBean.LoginForm loginForm = new UserServiceBean.LoginForm();
        loginForm.setUsername(phone);
        if (isPassWordLogin){
            loginForm.setPassword(code);
        } else {
            loginForm.setCaptcha(code);
        }

        RetrofitAPIManager.create(UserService.class).login(loginForm)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<MchBean>(true) {

            @Override
            protected void onApiComplete() {
                loginView.setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<MchBean> t) throws Exception {
                loginView.showToast("登录成功！");
                MchInfoLogic.saveMchInfoBean(t.getData());
                loginView.loginSuccess();
            }

            @Override
            protected void onCodeError(BaseEntity<MchBean> t) throws Exception {
                super.onCodeError(t);
                loginView.setLoadingMessageIndicator(false);
            }
        });
    }

    @Override
    public void getCaptchaLogin(String phone) {
        loginView.setLoadingMessageIndicator(true);
        UserServiceBean.LoginForm loginForm = new UserServiceBean.LoginForm();
        loginForm.setUsername(phone);
        RetrofitAPIManager.create(UserService.class).getCaptchaLogin(loginForm)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {
                loginView.setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                loginView.showToast("获取验证码成功");
                loginView.startTimer();
            }

            @Override
            protected void onCodeError(BaseEntity<Object> t) throws Exception {
                super.onCodeError(t);
            }
        });
    }

    @Override
    public void showMessageBtn(boolean show) {
        loginView.showGetMessageBtn(show);
    }
}
