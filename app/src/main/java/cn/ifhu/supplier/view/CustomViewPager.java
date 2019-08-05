package cn.ifhu.supplier.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * @author tommy
 * @date 17/12/4
 */

public class CustomViewPager extends ViewPager {
    /**
     * 禁止滑动标识
     */
    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            try {
                return super.onTouchEvent(event);
            }catch (Exception e){

            }
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            try {
                return super.onInterceptTouchEvent(event);
            }catch (Exception e){

            }
        }

        return false;
    }

    public void setScrollEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
