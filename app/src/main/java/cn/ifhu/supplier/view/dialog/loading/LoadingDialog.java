package cn.ifhu.supplier.view.dialog.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cn.ifhu.supplier.R;


/**
 * Created by tommy on 17/12/29.
 */

public class LoadingDialog extends Dialog {

    private static LoadingDialog mProgressDialog;

    private com.github.rahatarmanahmed.cpv.CircularProgressView mProgressView;
    private TextView tvMsg;
    private View contentView;

    /**
     * Loging弹框
     */
    public static void progressShow(Context ctx) {
        progressShow("", ctx, null);
    }

    /**
     * Loging弹框
     *
     * @param msg
     */
    public static void progressShow(String msg, Context ctx) {
        progressShow(msg, ctx, null);
    }

    /**
     * Loging弹框
     *
     * @param msg
     * @param dismissListener
     */
    public static void progressShow(String msg, Context ctx, OnDismissListener dismissListener) {
        if (ctx != null && !((Activity) ctx).isFinishing()) {
            //show dialog
            progressCancel();
            mProgressDialog = new LoadingDialog(ctx, msg);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnDismissListener(dismissListener);
            mProgressDialog.show();
        }
    }

    /**
     * 取消等待弹框
     */
    public static void progressCancel() {
        //进度框对话条,用try catch 来处理
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            mProgressDialog = null;
        }
    }


    public LoadingDialog(Context context, String strMessage) {
        this(context, R.style.loadDialog, strMessage);
    }

    public LoadingDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.dialog_loading_drawable);
        contentView = findViewById(R.id.dialog_view);
        mProgressView = findViewById(R.id.progress_view);
    }

    @Override
    public void show() {
        super.show();
        mProgressView.startAnimation();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
