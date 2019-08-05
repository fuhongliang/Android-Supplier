package cn.ifhu.supplier.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;


@Keep
public class ViewManager {

    private static Stack<Activity> activityStack;
    private static BaseFragment[] baseFragments = new BaseFragment[4];

    public static ViewManager getInstance() {
        return ViewManagerHolder.sInstance;
    }

    private static class ViewManagerHolder {
        private static final ViewManager sInstance = new ViewManager();
    }

    private ViewManager() {
    }

    public void addFragment(int index, BaseFragment fragment) {
        baseFragments[index] = fragment;
    }


    public BaseFragment getFragment(int index) {
        if (baseFragments != null) {
            return baseFragments[index];
        }
        return null;
    }


    public List<BaseFragment> getAllFragment() {
        if (baseFragments != null) {
            return  Arrays.asList(baseFragments);
        }
        return null;
    }


    /**
     * 添加指定Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }


    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }


    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 结束指定Class的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }


    /**
     * 结束全部的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            //杀死后台进程需要在AndroidManifest中声明android.permission.KILL_BACKGROUND_PROCESSES；
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            //System.exit(0);
        } catch (Exception e) {
            Log.e("ActivityManager", "app exit" + e.getMessage());
        }
    }
}
