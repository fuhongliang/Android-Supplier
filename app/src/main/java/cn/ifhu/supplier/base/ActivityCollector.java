package cn.ifhu.supplier.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuhongliang
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        List delList = new ArrayList();
        for(Activity activity:activities) {
            if(!activity.isFinishing()){
                delList.add(activity);
                activity.finish();
            }
        }
        activities.removeAll(delList);
    }
}
