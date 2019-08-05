package cn.ifhu.supplier.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by tommy on 17/12/19.
 */

public class KeyBoardUtil {
    /**
     * Supresses instantiation
     */
    private KeyBoardUtil() {
        throw new AssertionError();
    }

    public static float convertDpToPx(Context context, float dp) {
        Resources res = context.getResources();

        return dp * (res.getDisplayMetrics().densityDpi / 160f);
    }

    /**
     * Show keyboard and focus to given EditText
     *
     * @param context Context
     * @param target  EditText to focus
     */
    public static void showKeyboard(Context context, EditText target) {
        if (context == null || target == null) {
            return;
        }

        InputMethodManager imm = getInputMethodManager(context);
        imm.showSoftInput(target, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * Show keyboard and focus to given EditText.
     * Use this method if target EditText is in Dialog.
     *
     * @param dialog Dialog
     * @param target EditText to focus
     */
    public static void showKeyboardInDialog(Dialog dialog, EditText target) {
        if (dialog == null || target == null) {
            return;
        }

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        target.requestFocus();
    }

    /**
     * hide keyboard
     *
     * @param context Context
     * @param target  View that currently has focus
     */
    public static void hideKeyboard(Context context, View target) {
        if (context == null || target == null) {
            return;
        }

        InputMethodManager imm = getInputMethodManager(context);
        imm.hideSoftInputFromWindow(target.getWindowToken(), 0);
    }

    /**
     * hide keyboard
     *
     * @param activity Activity
     */
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();

        if (view != null) {
            hideKeyboard(activity, view);
        }
    }

    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * Determine if keyboard is visible
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    public static boolean isKeyboardVisible(Activity activity, View activityRoot) {
        Rect r = new Rect();

        int visibleThreshold = Math
                .round(KeyBoardUtil.convertDpToPx(activity, 100));

        activityRoot.getWindowVisibleDisplayFrame(r);

        int heightDiff = activityRoot.getRootView().getHeight() - r.height();

        return heightDiff > visibleThreshold;
    }

}
