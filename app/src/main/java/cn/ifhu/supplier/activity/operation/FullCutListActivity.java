package cn.ifhu.supplier.activity.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.FullCutAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.FullCutBean;
import cn.ifhu.supplier.view.dialog.nicedialog.BuyDiscountDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class FullCutListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_discount)
    ListView lvDiscount;
    List<FullCutBean> fullCutBeanList = new ArrayList<>();
    FullCutAdapter fullCutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_cut_list);
        ButterKnife.bind(this);
        tvTitle.setText("满立减");
        fullCutAdapter = new FullCutAdapter(fullCutBeanList, this);
        fullCutAdapter.setOnClickItem(position -> DialogUtils.showConfirmDialog("温馨提示","是否确认删除此促销活动？", getSupportFragmentManager(),new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                delFullCutData(position);
            }
        }));
        lvDiscount.setAdapter(fullCutAdapter);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_add_full_cut)
    public void onRlAddFullCutClicked() {
        startActivity(new Intent(FullCutListActivity.this,AddFullReductionActivity.class));
    }


    public void getFullCutListData() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getFullCutList(MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<FullCutBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<FullCutBean>> t) throws Exception {
                fullCutBeanList = t.getData();
                fullCutAdapter.setBeanList(fullCutBeanList);
                if (fullCutBeanList != null && fullCutBeanList.size() > 0) {
                    llEmpty.setVisibility(View.GONE);
                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onCodeError(BaseEntity<List<FullCutBean>> t) throws Exception {
                super.onCodeError(t);
                if (t.getCode() == 2000) {
                    showBuyFullCutQuanxianDialog();
                }
            }
        });
    }

    public void showBuyFullCutQuanxianDialog() {
        View view = getLayoutInflater().inflate(R.layout.buy_discount_dialog, null);
        DialogUtils.showBuyDiscountDialog(getSupportFragmentManager(), new BuyDiscountDialog.ButtonOnclick() {
            @Override
            public void ok(String amount) {
                if (!StringUtils.isEmpty(amount)) {
                    buyFullCutQuanxian(amount);
                }
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }

            @Override
            public void cancel() {
                finish();
            }
        });
    }

    public void buyFullCutQuanxian(String month) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).buy_mansong_quota(Integer.parseInt(month), MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        getFullCutListData();
    }

    public void delFullCutData(int position) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delFullCut(fullCutBeanList.get(position).getMansong_id()+"",MchInfoLogic.getMchId()+"")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                fullCutBeanList.remove(position);
                fullCutAdapter.setBeanList(fullCutBeanList);
            }
        });
    }

}
