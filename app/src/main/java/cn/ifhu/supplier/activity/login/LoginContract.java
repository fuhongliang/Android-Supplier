package cn.ifhu.supplier.activity.login;


import cn.ifhu.supplier.base.BasePresenter;
import cn.ifhu.supplier.base.BaseView;

/**
 * @author fuhongliang
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        /**
         * 页面显示后初始化操作入口
         */
        void initData();

        /**
         * 显示最后一次登录的手机号码
         */
        void showLastTimePhoneNumber();

        /**
         * 提示信息
         * @param msg 内容
         */
        void showToast(String msg);

        void setLoadingMessageIndicator(boolean indicator);

        void loginSuccess();

        /**
         * 开始验证码计时
         */
        void startTimer();

        void showGetMessageBtn(boolean show);
    }

    interface Presenter extends BasePresenter {
        boolean checkPhoneNumber(String phoneNo,String passWord);
        void loginWithCode(String phone, String code,boolean isPassWordLogin);
        void getCaptchaLogin(String phone);
        void showMessageBtn(boolean show);
    }
}
