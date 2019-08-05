package cn.ifhu.supplier.fragments.operation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.SettledAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.JSBean;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;

/**
 * 已结算
 * @author fuhongliang
 */
public class SettledFragment extends BaseFragment {

    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_settled)
    TextView tvSettled;
    @BindView(R.id.tv_unsettled)
    TextView tvUnsettled;
    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    SettledAdapter settledAdapter;
    List<JSBean.ListBean> listBeans = new ArrayList<>();
    private TimePickerView pvTime;

    public static SettledFragment newInstance() {
        return new SettledFragment();
    }


    public SettledFragment() {
    }


    public void getAllSettledData(String date) {
        if (!StringUtils.isEmpty(date)) {
            setLoadingMessageIndicator(true);
            RetrofitAPIManager.create(OperationService.class).allStoreJiesuan(date, MchInfoLogic.getMchId())
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<JSBean>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<JSBean> t) throws Exception {
                    if (t.getData().getList() !=null && t.getData().getList().size()>0){
                        settledAdapter.setmDataList(t.getData().getList());
                    }
                    initData(t.getData());
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settled, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settledAdapter = new SettledAdapter(listBeans, getContext());
        listView.setAdapter(settledAdapter);
        getAllSettledData(DateUtil.getCurDateYear());
        initTimePicker();
    }
    private void initTimePicker() {
        pvTime = new TimePickerBuilder(getContext(), (date, v) -> {
            tvYear.setText(DateUtil.getCurDateYear(date)+"年");
            getAllSettledData(DateUtil.getCurDateYear(date));
        })
                .setTimeSelectChangeListener(date -> {

                })
                .setType(new boolean[]{true, false, false, false, false, false})
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


    public void initData(JSBean withDrawBean) {
        tvSettled.setText(withDrawBean.getY_jiesuan()+"");
        tvUnsettled.setText(withDrawBean.getD_jiesuan()+"");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_year)
    public void onViewClicked() {
        pvTime.show();
    }
}
