package cn.ifhu.supplier.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;


public class ExpandListView extends ListView {
    private GestureDetector mGestureDetector;

    public ExpandListView(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(new YScrollDetector());
    }

    public ExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(new YScrollDetector());
    }

    public ExpandListView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetector(new YScrollDetector());
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return mGestureDetector.onTouchEvent(ev);

    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            //判断是否上下滑动
            if (Math.abs(distanceY) / Math.abs(distanceX) > 2) {
                return true;//截获事件
            }
            return false;
        }

    }
}
