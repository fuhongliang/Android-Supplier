package cn.ifhu.supplier.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Objects;

import cn.ifhu.supplier.MyApplication;

/**
 * author: tommy
 * date:   On 2018/8/9
 * @author fuhongliang
 */
public class DeviceUtil {
    /**
     * 获得屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    public static int getDeviceWidth(Context mCtx) {
        WindowManager wm = (WindowManager) mCtx
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }




    /**
     * 获取app版本号
     *
     * @param ctx
     * @return
     */
    public static String getAppVersion(Context ctx) {
        // 请求失败的情况下，重新请求次数
        int mRetryCount = 0;
        String versionName = "";
        PackageInfo pi = null;
        // 版本号获取失败会影响到更新，所以再失败的情况下要重试3次
        while ((null == versionName || versionName.equals(""))
                && mRetryCount < 3) {
            try {
                pi = ctx.getPackageManager().getPackageInfo(
                        ctx.getPackageName(), 0);
                versionName = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            mRetryCount++;
        }

        return versionName;
    }

    public static String getHeaderVersion(Context context) {
        return "android;" + getAppVersion(context);
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
            return -1;
        }
    }


    public static int dip2px(float dipValue) {
        final float scale = MyApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float dip2pxF(float dipValue) {
        final float scale = MyApplication.getApplication().getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public static boolean toMarket(Context context, String appPkg, String marketPkg) {
        Uri uri = Uri.parse("market://details?id=" + appPkg);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (marketPkg != null) {
            intent.setPackage(marketPkg);
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        Objects.requireNonNull(cmb).setPrimaryClip(ClipData.newPlainText(null, content.trim()));
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb.hasPrimaryClip()) {
            return cmb.getPrimaryClip().getItemAt(0).getText().toString();
        }
        return "";
    }

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

    public static boolean isPkgInstalled(String pkgName, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            installed = false;
        }
        return installed;
    }

    /**
     * 获取厂商信息
     *
     * @return 获取厂商信息
     */
    public static String getManufacturer() {
        return (Build.MANUFACTURER) == null ? "" : (Build.MANUFACTURER).trim();
    }

}
