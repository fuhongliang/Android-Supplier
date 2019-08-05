package cn.ifhu.supplier.utils.notificaitons;

/**
 * Created by peter on 2018/6/27.
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;

import java.util.Arrays;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.utils.Constants;
import cn.ifhu.supplier.utils.IrReference;


public class NotificationChannels {
    public final static String CRITICAL = "critical";
    public final static String IMPORTANCE = "importance";
    public final static String DEFAULT = "default";
    public final static String LOW = "low";
    public final static String MEDIA = "media";

    public static void createAllNotificationChannels(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(nm == null) {
            return;
        }

        NotificationChannel mediaChannel = new NotificationChannel(MEDIA, context.getString(R.string.channel_media), NotificationManager.IMPORTANCE_HIGH);
        AudioAttributes.Builder audioAttributesBuilder = new AudioAttributes.Builder();
        audioAttributesBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
        AudioAttributes aa = audioAttributesBuilder.build();
        Uri mUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +context.getPackageName()+R.raw.ring);
        mediaChannel.setSound(mUri,aa);

        mediaChannel.enableLights(true);
        mediaChannel.setLightColor(Color.RED);
        boolean isShake = IrReference.getInstance().getBoolean(Constants.Shake, false);
        mediaChannel.enableVibration(isShake);
        mediaChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});


        nm.createNotificationChannels(Arrays.asList(
                new NotificationChannel(
                        CRITICAL,
                        context.getString(R.string.channel_critical),
                        NotificationManager.IMPORTANCE_HIGH),
                new NotificationChannel(
                        IMPORTANCE,
                        context.getString(R.string.channel_importance),
                        NotificationManager.IMPORTANCE_DEFAULT),
                new NotificationChannel(
                        DEFAULT,
                        context.getString(R.string.channel_default),
                        NotificationManager.IMPORTANCE_LOW),
                new NotificationChannel(
                        LOW,
                        context.getString(R.string.channel_low),
                        NotificationManager.IMPORTANCE_MIN),
                mediaChannel
        ));
    }
}
