package cn.ifhu.supplier.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bugtags.library.Bugtags;
import com.umeng.message.PushAgent;

import cn.ifhu.supplier.view.dialog.loading.LoadingDialog;
import cn.ifhu.supplier.utils.KeyBoardUtil;
import cn.ifhu.supplier.utils.Utils;

import static cn.ifhu.supplier.utils.Constants.DATA;

/**
 * @author fuhongliang
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {     //创建时的回调函数onCreate
        super.onCreate(savedInstanceState);

        ActivityCollector.addActivity(this);
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 所有activity出栈 同时启动一个activity
     *
     * @param cls 目标页面
     * */
    public void clearAllAndStart(Class<?> cls){
        ActivityCollector.finishAll();
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    /**
     * 跳转到页面 cls
     *
     * @param cls 目标页面
     */
    public void goToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    /**
     * 携带数据跳转
     *
     * @param cls  目标页面
     * @param data 携带的数据，DATA
     */
    public void goToActivity(Class<?> cls, String data) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(DATA, data);
        startActivity(intent);
    }

    /**
     * 携带数据跳转
     *
     * @param cls  目标页面
     * @param data 携带的数据，DATA
     */
    public void goToActivity(Class<?> cls, int data) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(DATA, data);
        startActivity(intent);
    }

    /**
     * 获取页面携带的字符串
     *
     * @return 如果字符串为空则返回空
     */
    public int getDataInt() {
        return getIntent().getIntExtra(DATA, 0);
    }

    /**
     * 获取页面携带的字符串
     *
     * @return 如果字符串为空则返回空
     */
    public String getDATA() {
        if (getIntent().getStringExtra(DATA) == null) {
            return "";
        } else {
            return getIntent().getStringExtra(DATA);

        }
    }

    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .replace(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 移除fragment
     *
     * @param fragment
     */
    protected void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 是否显示加载提示
     *
     * @param active 是否激活
     */
    public void setLoadingMessageIndicator(boolean active) {
        if (active) {
            LoadingDialog.progressShow(BaseActivity.this);
        } else {
            LoadingDialog.progressCancel();
        }
    }

    public void goBack() {
        KeyBoardUtil.hideKeyboard(this);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 判断是否短时间内重复点击
     */

    private long lastClick = 0;
    public int twiceClickTime = 1500;

    public boolean isNotDuplication() {
        if (System.currentTimeMillis() - lastClick <= twiceClickTime) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
}
