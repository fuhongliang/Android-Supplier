package cn.ifhu.supplier.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomDatePicker  extends TimePicker
{
    private List<NumberPicker> mPickers;

    public CustomDatePicker(Context context)
    {
        super(context);
        findNumberPicker();
    }

    public CustomDatePicker(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        findNumberPicker();
    }

    public CustomDatePicker(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        findNumberPicker();
    }

    /**
     * 得到控件里面的numberpicker组件
     */
    private void findNumberPicker()
    {
        mPickers = new ArrayList<NumberPicker>();
        LinearLayout llFirst = (LinearLayout) getChildAt(0);
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);

        for (int i = 0; i < mSpinners.getChildCount(); i++)
        {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);
            mPickers.add(i, picker);
        }
    }




    private String format2Digits(int value)
    {
        return String.format("%02d",value);
    }


    /**
     * 设置picker间隔
     *
     * @param margin
     */
    public void setPickerMargin(int margin)
    {
        for (NumberPicker picker : mPickers)
        {
            LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) picker.getLayoutParams();
            lps.setMargins(margin, 0, margin, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                lps.setMarginStart(margin);
                lps.setMarginEnd(margin);
            }
            picker.setLayoutParams(lps);
        }
    }

    /**
     * 设置时间选择器的分割线颜色
     */
    public void setDividerColor(int color)
    {
        for (int i = 0; i < mPickers.size(); i++)
        {
            NumberPicker picker = mPickers.get(i);

            try
            {
                Field pf = NumberPicker.class.getDeclaredField("mSelectionDivider");
                pf.setAccessible(true);
                pf.set(picker, new ColorDrawable(color));
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }
    }
}
