package cn.ifhu.supplier.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import cn.ifhu.supplier.MyApplication;

import static cn.ifhu.supplier.utils.Constants.APP_PREFERENCE;

/**
 * @author fuhongliang
 */
public class IrReference {

    private static SharedPreferences settings;
    public static SharedPreferences.Editor editor;
    private static IrReference self = null;
    private static boolean sIsAtLeastGB;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }

    public IrReference(Context context) {
        settings = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    /**
     * 可以直接通过类获取到IrReference对象，然后保存或者读取数据
     *
     * @return IrReference
     */
    public static IrReference getInstance() {
        if (self == null) {
            self = new IrReference(MyApplication.getApplication());
        }
        return self;
    }

    /**
     * 针对不同系统版本调用不同方法去保存SharedPreferences文件
     * @param editor SharedPreferences的编辑器
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    /**
     *
     * @param paramString  配置名称
     * @param paramBoolean 配置值
     */
    public void setBoolean(String paramString, boolean paramBoolean) {
        editor.putBoolean(paramString, paramBoolean);
        apply(editor);
    }

    /**
     *
     * @param paramString  配置名称
     * @param defaultValue 返回的默认值
     * @return
     */
    public boolean getBoolean(String paramString, boolean defaultValue) {
        return settings.getBoolean(paramString, defaultValue);
    }

    /**
     *
     * @param paramString 配置名称
     * @param paramValue  配置值
     */
    public void saveString(String paramString, String paramValue) {
        editor.putString(paramString, paramValue);
        apply(editor);
    }

    /**
     *
     * @param paramString   配置名称
     * @param defaultValue  返回的默认值
     * @return
     */
    public String getString(String paramString, String defaultValue) {
        return settings.getString(paramString, defaultValue);
    }

    /**
     *
     * @param paramString 配置名称
     * @param paramValue  配置值
     */
    public void setInt(String paramString, int paramValue) {
        editor.putInt(paramString, paramValue);
        apply(editor);
    }

    /**
     *
     * @param paramString   配置名称
     * @param defaultValue  返回的默认值
     * @return
     */
    public int getInt(String paramString, int defaultValue) {
        return settings.getInt(paramString, defaultValue);
    }

    /**
     *
     * @param paramString 配置名称
     * @param paramValue  配置值
     */
    public void setLong(String paramString, long paramValue) {
        editor.putLong(paramString, paramValue);
        apply(editor);
    }

    /**
     *
     * @param paramString   配置名称
     * @param defaultValue  返回的默认值
     * @return
     */
    public long getLong(String paramString, long defaultValue) {
        return settings.getLong(paramString, defaultValue);
    }

    public void setFloat(String paramString, float paramValue) {
        editor.putFloat(paramString, paramValue);
        apply(editor);
    }

    public float getFloat(String paramString, float defaultValue) {
        return settings.getFloat(paramString, defaultValue);
    }

    public void clear() {
        settings.edit().clear().commit();
    }

    public void clearSingle(String key) {
        editor.remove(key).commit();
    }

}
