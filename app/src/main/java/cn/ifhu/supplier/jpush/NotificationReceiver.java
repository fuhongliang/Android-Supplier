package cn.ifhu.supplier.jpush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.orhanobut.logger.Logger;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.HomeActivity;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationReceiver extends JPushMessageReceiver {
    private static final String TAG = "JIGUANG-JPush";

    /**
     * TODO 连接极光服务器
     */
    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        Logger.e(TAG, "onConnected");
        Log.e(TAG, "onConnected");
    }

    /**
     * TODO 注册极光时的回调
     */
    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        Logger.e(TAG, "onRegister" + s);
        Log.e(TAG,  "onRegister" + s);
    }

    /**
     * TODO 注册以及解除注册别名时回调
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        Logger.e(TAG, jPushMessage.toString());
        Log.e(TAG,  jPushMessage.toString());
    }

    /**
     * TODO 接收到推送下来的通知
     *      可以利用附加字段（notificationMessage.notificationExtras）来区别Notication,指定不同的动作,附加字段是个json字符串
     *      通知（Notification），指在手机的通知栏（状态栏）上会显示的一条通知信息
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        Logger.e(TAG, notificationMessage.toString());
        Log.e(TAG,  notificationMessage.toString());
    }

    /**
     * TODO 打开了通知
     *      notificationMessage.notificationExtras(附加字段)的内容处理代码
     *      比如打开新的Activity， 打开一个网页等..
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        Logger.e(TAG, notificationMessage.notificationExtras);
        Log.e(TAG, notificationMessage.notificationExtras);
    }


    /**
     * TODO 接收到推送下来的自定义消息
     *      自定义消息不是通知，默认不会被SDK展示到通知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
     *      自定义消息主要用于应用的内部业务逻辑和特殊展示需求
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        Logger.e(TAG, "onMessage");
        Log.e(TAG, "onMessage");
        processCustomMessage(context,customMessage.extra);
    }

    private void processCustomMessage(Context context, String message) {

        String channelID = "1";
        String channelName = "channel_name";

        // 跳转的Activity
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, channelID);

        notification.setAutoCancel(true)
                .setContentText(message)
                .setContentTitle("我是Title")
                .setSmallIcon(R.drawable.icon)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        notificationManager.notify((int)(System.currentTimeMillis()/1000), notification.build());
    }

}
