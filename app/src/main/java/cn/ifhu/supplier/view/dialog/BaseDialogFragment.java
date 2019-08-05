package cn.ifhu.supplier.view.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author tommy
 * @date 17/12/12
 */

public class BaseDialogFragment extends DialogFragment {

    private boolean canCancelOutside = true;
    private int mBackStackId;
    private boolean isCanCancel = true;

    @Override
    public void show(FragmentManager manager, String tag) {
        show(manager, tag, true);
    }

    public int show(FragmentManager fragmentManager, String tag, boolean allowStateLoss) {
        return show(fragmentManager.beginTransaction(), tag, allowStateLoss);
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return show(transaction, tag, true);
    }

    protected int show(FragmentTransaction transaction, String tag, boolean allowStateLoss) {
        transaction.add(this, tag);
        this.mBackStackId = allowStateLoss ? transaction.commitAllowingStateLoss() : transaction.commit();
        return this.mBackStackId;
    }

    public void setCanceledOnTouchOutside(boolean isCancel) {

        canCancelOutside = isCancel;
    }

    public void setCanCancel(boolean isCanCancel) {
        this.isCanCancel = isCanCancel;
    }


    @Override
    public void onActivityCreated(Bundle arg0) {
        if (getDialog() == null) {
            setShowsDialog(false);
        }
        super.onActivityCreated(arg0);
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(canCancelOutside);
            getDialog().setCancelable(this.isCanCancel);
        }
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

}
