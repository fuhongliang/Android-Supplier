package cn.ifhu.supplier.utils.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.umeng.message.PushAgent;

public class StartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("--------------------", "开机启动楼~~~~");
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.onAppStart();
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        mPushAgent.getMessageHandler();
    }

}