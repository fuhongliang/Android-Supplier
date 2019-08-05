package cn.ifhu.supplier.utils.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import com.umeng.message.UmengMessageService;

import org.greenrobot.eventbus.EventBus;

import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.utils.Constants;

/**
 * @author fuhongliang
 */
public class MyPushIntentService extends UmengMessageService {

    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra("body");
            Log.d(TAG, "message="+message);
            EventBus.getDefault().post(new MessageEvent(Constants.ORDERCOMING, message));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "服务开始了！！！");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "服务创建了！！！");
    }
}