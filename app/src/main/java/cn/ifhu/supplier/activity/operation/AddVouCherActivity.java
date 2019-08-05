package cn.ifhu.supplier.activity.operation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.CategoryWheelItem;
import cn.ifhu.supplier.model.bean.ValueBean;
import cn.ifhu.supplier.model.bean.ValuePostBean;
import cn.ifhu.supplier.model.bean.VouCherInfoBean;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.DeviceUtil;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import jsc.kit.wheel.dialog.ColumnWheelDialog;

/**
 * @author fuhongliang
 */
public class AddVouCherActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_voucher_name)
    EditText etVoucherName;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.iv_less)
    ImageView ivLess;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.et_description)
    EditText etDescription;

    List<ValueBean> valueBeanList = new ArrayList<>();
    ColumnWheelDialog dialog = null;

    int limit = 1;
    String voucher_id;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_voucher);
        ButterKnife.bind(this);
        tvTitle.setText("限时折扣");
        getValueListData();

        voucher_id = getIntent().getStringExtra("voucher_id");
        if (StringUtils.isEmpty(voucher_id)) {
            tvTitle.setText("添加活动");
        } else {
            tvTitle.setText("编辑活动");
            getVouCherInfo();
        }
        initTimePicker();
        tvDate.setOnClickListener(v -> {
            pvTime.show(v);
        });
    }

    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, (date, v) -> {
            tvDate.setText(DateUtil.getDateToString(date));

        })
                .setTimeSelectChangeListener(date -> {
                    if (date.before(Calendar.getInstance().getTime())) {
                        pvTime.setDate(Calendar.getInstance());
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true)
                .addOnCancelClickListener(view -> {
                })
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }

    public void getVouCherInfo() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getVoucherInfo(voucher_id, MchInfoLogic.getMchId() + "")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<VouCherInfoBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<VouCherInfoBean> t) throws Exception {
                initData(t.getData());
            }
        });
    }

    public void initData(VouCherInfoBean vouCherInfoBean) {
        etVoucherName.setText(vouCherInfoBean.getVoucher_title());
        tvValue.setText(vouCherInfoBean.getVoucher_price() + "");
        etPrice.setText(vouCherInfoBean.getVoucher_limit() + "");
        tvDate.setText(vouCherInfoBean.getVoucher_end_date() + "");
        etNumber.setText(vouCherInfoBean.getVoucher_total() + "");
        tvLimit.setText(vouCherInfoBean.getVoucher_eachlimit() + "");
        limit = vouCherInfoBean.getVoucher_eachlimit();
        etDescription.setText(vouCherInfoBean.getVoucher_desc());
    }

    public void getValueListData() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getMianzhiList()
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<ValueBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<ValueBean>> t) throws Exception {
                valueBeanList.addAll(t.getData());
                initItems();
            }
        });

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        postValueData();
    }


    public void postValueData() {
        if (!checkContentEmpty()) {
            ValuePostBean valuePostBean = new ValuePostBean();
            valuePostBean.setTitle(etVoucherName.getText().toString());
            valuePostBean.setMianzhi(tvValue.getText().toString());
            valuePostBean.setLimit_price(etPrice.getText().toString());
            valuePostBean.setEnd_time(tvDate.getText().toString());
            valuePostBean.setTotal_nums(Integer.parseInt(etNumber.getText().toString()));
            valuePostBean.setEach_limit(Integer.parseInt(tvLimit.getText().toString()));
            valuePostBean.setDescribe(etDescription.getText().toString());
            valuePostBean.setStore_id(MchInfoLogic.getMchId());
            if (!StringUtils.isEmpty(voucher_id)) {
                valuePostBean.setVoucher_id(voucher_id);
            }
            setLoadingMessageIndicator(true);
            RetrofitAPIManager.create(OperationService.class).voucherEdit(valuePostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Object> t) throws Exception {
                    ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                    finish();
                }
            });

        }

    }

    public boolean checkContentEmpty() {

        if (StringUtils.isEmpty(etVoucherName.getText().toString())) {
            ToastHelper.makeText("请输入代金券名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }

        if (StringUtils.isEmpty(tvValue.getText().toString().trim())) {
            ToastHelper.makeText("请选择代金券面值", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入使用条件", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(tvDate.getText().toString().trim())) {
            ToastHelper.makeText("请选择有效期", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }


        if (StringUtils.isEmpty(etNumber.getText().toString().trim()) || Integer.parseInt(etNumber.getText().toString().trim()) <= 0) {
            ToastHelper.makeText("请输入大于0的发放数量", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }

        if (StringUtils.isEmpty(etDescription.getText().toString().trim())) {
            ToastHelper.makeText("请输入描述", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }

        return false;
    }

    @OnClick(R.id.tv_value)
    public void onTvValueClicked() {
        if (dialog == null) {
            dialog = createDialog();
        } else {
            if (valueBeanList != null && valueBeanList.size() > 0) {
                dialog.show();
            }
        }
    }

    private ColumnWheelDialog createDialog() {
        ColumnWheelDialog<CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem> dialog = new ColumnWheelDialog<>(this);
        dialog.show();
        dialog.setTextSize(DeviceUtil.dip2px(18));
        dialog.setTitle("");
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", (v, item0, item1, item2, item3, item4) -> {
            String result = "";
            if (item0 != null) {
                result += item0.getShowText();

            }
            tvValue.setText(result);
            return false;
        });
        dialog.setItems(initItems(), null, null, null, null);
        dialog.setSelected(0, 0, 0, 0, 0);
        return dialog;
    }

    private CategoryWheelItem[] initItems() {
        final CategoryWheelItem[] items;
        try {
            items = new CategoryWheelItem[valueBeanList.size()];
            for (int i = 0; i < valueBeanList.size(); i++) {
                items[i] = new CategoryWheelItem(valueBeanList.get(i).getVoucher_price() + "", valueBeanList.get(i).getVoucher_price_id());
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CategoryWheelItem[1];
    }


    @OnClick(R.id.iv_less)
    public void onIvLessClicked() {
        if (limit != 1) {
            limit = limit - 1;
            tvLimit.setText(limit + "");
        }

    }

    @OnClick(R.id.iv_more)
    public void onIvMoreClicked() {
        limit = limit + 1;
        tvLimit.setText(limit + "");

    }
}
