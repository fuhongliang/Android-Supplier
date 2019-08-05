package cn.ifhu.supplier.activity.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.DiscountPackageAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.DiscountPackageBean;
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
public class DiscountPackageListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    List<DiscountPackageBean> discountPackageBeans;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_discount_package)
    ListView lvDiscountPackage;
    @BindView(R.id.rl_add_discount_package)
    RelativeLayout rlAddDiscountPackage;
    DiscountPackageAdapter discountPackageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_package_list);
        ButterKnife.bind(this);
        tvTitle.setText("优惠套餐");
        discountPackageAdapter = new DiscountPackageAdapter(discountPackageBeans, this);
        discountPackageAdapter.setOnClickItem(new DiscountPackageAdapter.OnClickItem() {
            @Override
            public void editDiscountPackage(int position) {
                Intent intent = new Intent(DiscountPackageListActivity.this,AddDiscountPackageActivity.class);
                intent.putExtra("bundling_id",discountPackageBeans.get(position).getBl_id()+"");
                startActivity(intent);
            }

            @Override
            public void deleteDiscountPackage(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否删除该优惠套餐", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        deleteDiscountItem(position);
                    }
                });
            }
        });
        lvDiscountPackage.setAdapter(discountPackageAdapter);
    }

    public void deleteDiscountItem(int position){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delDiscountPackage(discountPackageBeans.get(position).getBl_id()+"",MchInfoLogic.getMchId()+"")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                discountPackageBeans.remove(position);
                discountPackageAdapter.setBeanList(discountPackageBeans);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getDiscountListData();
    }

    public void getDiscountListData() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getDiscountPackageList(MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<DiscountPackageBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<DiscountPackageBean>> t) throws Exception {
                discountPackageBeans = t.getData();
                discountPackageAdapter.setBeanList(discountPackageBeans);
                if (discountPackageAdapter.getCount() > 0) {
                    llEmpty.setVisibility(View.GONE);
                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onCodeError(BaseEntity<List<DiscountPackageBean>> t) throws Exception {
                super.onCodeError(t);
                if (t.getCode() == 2000) {
                    showBuyDiscountPackageQuanxianDialog();
                }
            }
        });
    }

    public void showBuyDiscountPackageQuanxianDialog() {
        View view = getLayoutInflater().inflate(R.layout.buy_discount_dialog, null);
        DialogUtils.showBuyDiscountDialog(getSupportFragmentManager(), new BuyDiscountDialog.ButtonOnclick() {
            @Override
            public void ok(String amount) {
                if (!StringUtils.isEmpty(amount)) {
                    buyDiscountPackageQuanxian(amount);
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

    public void buyDiscountPackageQuanxian(String month) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).buy_bundling_quota(Integer.parseInt(month), MchInfoLogic.getMchId())
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

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_add_discount_package)
    public void onRlAddDiscountPackageClicked() {
        startActivity(new Intent(DiscountPackageListActivity.this, AddDiscountPackageActivity.class));
    }
}
