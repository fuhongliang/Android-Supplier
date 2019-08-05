package cn.ifhu.supplier.view.dialog.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

/**
 * Created by tommy on 17/12/29.
 */

public class SafeAnimatorListenerAdapter extends AnimatorListenerAdapter {
    public static final String TAG = "SafeAnimatorListenerAdapter";
    @Override
    public void onAnimationCancel(Animator animation) {
        super.onAnimationCancel(animation);
        //Log.d(TAG,"onAnimationCancel,删除各种监听器，animation@"+animation.hashCode());
        animation.removeAllListeners();
        if(animation instanceof ValueAnimator){
            ValueAnimator valueAnimator = (ValueAnimator) animation;
            valueAnimator.removeAllUpdateListeners();
        }
    }
}
