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
import android.widget.LinearLayout;
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
import cn.ifhu.supplier.adapter.FullCutRuleAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.FullCutPostBean;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.ExpandListView;
import io.reactivex.Observer;

/**
 * @author fuhongliang
 */
public class AddFullReductionActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_input_full)
    EditText etInputFull;
    @BindView(R.id.et_input_cut)
    EditText etInputCut;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.listView)
    ExpandListView listView;
    @BindView(R.id.et_name)
    EditText etName;
    private List<FullCutPostBean.RuleBean> rule;
    FullCutRuleAdapter fullCutRuleAdapter;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_full_reduction);
        ButterKnife.bind(this);
        rule = new ArrayList<>();
        fullCutRuleAdapter = new FullCutRuleAdapter(rule, this);
        listView.setAdapter(fullCutRuleAdapter);
        fullCutRuleAdapter.setOnClickItem(new FullCutRuleAdapter.OnClickItem() {
            @Override
            public void deleteItem(int position) {
                rule.remove(position);
                fullCutRuleAdapter.setBeanList(rule);
            }
        });
        tvTitle.setText("添加活动");
        type = 0;
        initTimePicker();
        tvStartTime.setOnClickListener(v -> {
            type = 0;
            pvTime.show(v);
        });
        tvEndTime.setOnClickListener(v -> {
            type = 1;
            pvTime.show(v);
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    private TimePickerView pvTime;

    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, (date, v) -> {
            if (type == 0) {
                tvStartTime.setText(DateUtil.getDateToString(date));
            } else {
                tvEndTime.setText(DateUtil.getDateToString(date));
            }

        })
                .setTimeSelectChangeListener(date -> {
                    if (date.before(Calendar.getInstance().getTime())) {
                        pvTime.setDate(Calendar.getInstance());
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
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

    @OnClick(R.id.ll_add)
    public void onLlAddClicked() {
        String full = etInputFull.getText().toString().trim();
        String cut = etInputCut.getText().toString().trim();
        if (StringUtils.isEmpty(full)) {
            ToastHelper.makeText("请输入满多少元", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return;
        }
        if (StringUtils.isEmpty(cut)) {
            ToastHelper.makeText("请输入减多少元", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return;
        }

        if (Double.parseDouble(cut)>=Double.parseDouble(full)) {
            ToastHelper.makeText("满减金额必须小于规则金额", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return;
        }

        if (rule.size() > 3) {
            ToastHelper.makeText("最多只能添加4条规则", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
        } else {
            FullCutPostBean.RuleBean ruleBean = new FullCutPostBean.RuleBean();
            ruleBean.setPrice(Integer.parseInt(full));
            ruleBean.setDiscount(Integer.parseInt(cut));
            rule.add(ruleBean);
            fullCutRuleAdapter.setBeanList(rule);
            etInputFull.setText("");
            etInputCut.setText("");
        }

    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (checkContent()) {
            setLoadingMessageIndicator(true);
                FullCutPostBean fullCutPostBean = new FullCutPostBean();
            fullCutPostBean.setMansong_name(etName.getText().toString().trim());
            fullCutPostBean.setStart_time(tvStartTime.getText().toString());
            fullCutPostBean.setEnd_time(tvEndTime.getText().toString());
            fullCutPostBean.setRules(rule);
            fullCutPostBean.setRemark(etRemark.getText().toString().trim());
            fullCutPostBean.setStore_id(MchInfoLogic.getMchId() + "");
            RetrofitAPIManager.create(OperationService.class).mamsongEditOrAdd(fullCutPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Observer> t) throws Exception {
                    ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                    finish();
                }
            });
        }
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(etName.getText().toString().trim())) {
            ToastHelper.makeText("请输入活动名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvStartTime.getText().toString().trim())) {
            ToastHelper.makeText("请选择开始时间", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvEndTime.getText().toString().trim())) {
            ToastHelper.makeText("请选择结束时间", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }


        if (rule == null || rule.size() < 1) {
            ToastHelper.makeText("请添加满立减规则", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }
        return true;
    }
}
