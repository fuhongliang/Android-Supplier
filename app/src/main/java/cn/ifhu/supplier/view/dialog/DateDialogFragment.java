package cn.ifhu.supplier.view.dialog;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Field;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.utils.DeviceUtil;

/**
 * @author tony
 * @date 2018/8/13
 */
public class DateDialogFragment extends BaseDialogFragment {

    private OperateDialogConfirmListner confirmListner;
    private TextView tvCancel;
    private TextView tvOk;
    TimePicker timePickerBegin;
    String beginTime;

    public static void showOperateDialog(FragmentManager fragmentManager, Bundle bundle, OperateDialogConfirmListner confirmListner) {
        DateDialogFragment dialogFragment = new DateDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setDialogConfirmListner(confirmListner);
        dialogFragment.show(fragmentManager, "AlertDialogFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Bottom_top_dialog_style);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - DeviceUtil.dip2px(0), ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_wheel_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOk = view.findViewById(R.id.tv_ok);
        timePickerBegin = view.findViewById(R.id.time_picker);
        tvCancel.setOnClickListener(v -> {
            dismiss();
        });

        tvOk.setOnClickListener(v -> {
            dismiss();
            confirmListner.onClickTextView(beginTime);
        });

        addOptionalView(bundle.getString("startTime"));
//      changeTimePickerColor(view);
    }

    public void addOptionalView(String startTime) {
        beginTime = startTime;

        String[] start = startTime.split(":");
        int startHour = Integer.parseInt(start[0]);
        int startMins = Integer.parseInt(start[1]);


        timePickerBegin.setIs24HourView(true);
        //设置为24小时显示格式
        timePickerBegin.setCurrentHour(startHour);
        //当前小时
        timePickerBegin.setCurrentMinute(startMins);


        timePickerBegin.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        timePickerBegin.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            String hour;
            String min;
            if (hourOfDay > 9) {
                hour = hourOfDay + "";
            } else {
                hour = "0" + hourOfDay;
            }

            if (minute > 9) {
                min = minute + "";
            } else {
                min = "0" + minute;
            }
            beginTime = hour + ":" + min;
        });

    }

    public void changeTimePickerColor(View view) {
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");

        NumberPicker hourNumberPicker = view.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = view.findViewById(minuteNumberPickerId);

        setNumberPickerDivider(hourNumberPicker, Color.BLUE);
        setNumberPickerDivider(minuteNumberPicker, Color.BLUE);

//        setNumberpickerTextColour(hourNumberPicker, R.color.colorPrimary);
//        setNumberpickerTextColour(minuteNumberPicker, R.color.colorPrimary);

//      setPickerSize(hourNumberPicker, 30, this);

    }

    //指定分割线颜色
    private void setNumberPickerDivider(NumberPicker numberPicker, int color) {

        try {
            Field dividerFields = NumberPicker.class.getDeclaredField("mSelectionDivider");

            dividerFields.setAccessible(true);

            dividerFields.set(numberPicker, new ColorDrawable(color));

        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            Log.w("setNumberPickerTxtClr", e);
        }
    }


    //另一种指定文字颜色的方法
    private void setNumberpickerTextColour(NumberPicker number_picker, int color) {
        final int count = number_picker.getChildCount();

        for (int i = 0; i < count; i++) {
            View child = number_picker.getChildAt(i);

            try {
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);
                ((Paint) wheelpaint_field.get(number_picker)).setColor(color);
                ((EditText) child).setTextColor(color);
                number_picker.invalidate();
            } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                Log.i("setNumberPickerTxtClr", "set_numberpicker_text_colour: " + e);
            }
        }
    }


    public void setDialogConfirmListner(OperateDialogConfirmListner alertDialogConfirmListner) {
        this.confirmListner = alertDialogConfirmListner;
    }

    public interface OperateDialogConfirmListner {
        void onClickTextView(String beginTime);
    }
}