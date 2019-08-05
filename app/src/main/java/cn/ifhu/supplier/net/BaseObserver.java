package cn.ifhu.supplier.net;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.utils.ToastHelper;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static cn.ifhu.supplier.utils.Constants.LOGOUT;

/**
 * @author fuhongliang
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private static final String TAG = "BaseObserver";
    /**
     * 用于标识服务端返回异常时是否弹出错误toast
     */
    private boolean needShowErroToast = false;

    public BaseObserver(boolean showErrorToast) {
        this.needShowErroToast = showErrorToast;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }


    @Override
    public void onNext(BaseEntity<T> tLinkBaseEntity) {
        Logger.d(tLinkBaseEntity.toString());
        if (tLinkBaseEntity.isSuccess()) {
            try {
                onSuccees(tLinkBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onAPIError();//异常错误回调
                onCodeError(tLinkBaseEntity);
                if (tLinkBaseEntity.isTokenTimeOut()) {
                    try {
                        EventBus.getDefault().post(new MessageEvent(LOGOUT));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    //异常code回调
                    if (needShowErroToast) {
                        if (tLinkBaseEntity != null && !TextUtils.isEmpty(tLinkBaseEntity.getMessage())) {
                            Logger.d(tLinkBaseEntity.getMessage());
                            ToastHelper.makeText(tLinkBaseEntity.getMessage(), Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onAPIError();//http异常
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException
                    || e instanceof SocketTimeoutException) {

                onFailure(e, true);
            } else {
                if (e instanceof HttpException) {
                    HttpException error = (HttpException) e;
                    if (400 <= error.code() && error.code() < 500) {
                        if (needShowErroToast) {
                            if (!TextUtils.isEmpty(error.getMessage())) {
                                ToastHelper.makeText(error.getMessage(), Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                            }
                        }
                    } else if (error.code() >= 500) {
                        onFailure(e, false);
                    }
                } else {
                    ToastHelper.makeText(e.getMessage(), Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        onApiComplete();
    }

    @Override
    public void onComplete() {
        onApiComplete();
    }

    /**
     * 接口处理无论成功与否页面对用的操作，需复写此方法
     *
     * @param
     */
    protected abstract void onApiComplete();

    /**
     * 返回成功
     *
     * @param t
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     */
    protected void onCodeError(BaseEntity<T> t) throws Exception {
        ToastHelper.makeText(t.getMessage()).show();
    }


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     */
    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
    }

    /**
     * 任何类型的错误返回:网络连接异常，后端返回异常，后端业务返回非成功，主要用于重置提交按钮状态
     */
    protected void onAPIError() {
    }
}
