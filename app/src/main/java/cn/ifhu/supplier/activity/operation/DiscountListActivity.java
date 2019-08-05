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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.DiscountAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.DiscountBean;
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
public class DiscountListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_discount)
    ListView lvDiscount;
    @BindView(R.id.rl_add_discount)
    RelativeLayout rlAddDiscount;
    DiscountAdapter discountAdapter;
    List<DiscountBean> discountBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_list);
        ButterKnife.bind(this);
        discountAdapter = new DiscountAdapter(discountBeanList, this);
        discountAdapter.setOnClickItem(new DiscountAdapter.OnClickItem() {
            @Override
            public void editDiscount(int position) {
                Intent intent = new Intent(DiscountListActivity.this, AddLimitDiscountsActivity.class);
                intent.putExtra("xianshi_id", discountBeanList.get(position).getXianshi_id() + "");
                startActivity(intent);
            }

            @Override
            public void deleteDiscount(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否删除该限时折扣", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
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
        lvDiscount.setAdapter(discountAdapter);
        tvTitle.setText("限时折扣");
    }

    public void deleteDiscountItem(int position) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delDiscount(discountBeanList.get(position).getXianshi_id() + "", MchInfoLogic.getMchId() + "")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                discountBeanList.remove(position);
                discountAdapter.setDiscountBeanList(discountBeanList);
            }
        });
    }

    public void getDiscountListData() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getDiscountList(MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<DiscountBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<DiscountBean>> t) throws Exception {
                discountBeanList = t.getData();
                discountAdapter.setDiscountBeanList(discountBeanList);
                if (discountAdapter.getCount() > 0) {
                    llEmpty.setVisibility(View.GONE);
                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onCodeError(BaseEntity<List<DiscountBean>> t) throws Exception {
                super.onCodeError(t);
                if (t.getCode() == 2000) {
                    showBuyDiscountDialog();
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_add_discount)
    public void onRlAddDiscountClicked() {
        startActivity(new Intent(DiscountListActivity.this,AddLimitDiscountsActivity.class));
    }

    public void showBuyDiscountDialog() {
        View view = getLayoutInflater().inflate(R.layout.buy_discount_dialog, null);
        DialogUtils.showBuyDiscountDialog(getSupportFragmentManager(), new BuyDiscountDialog.ButtonOnclick() {
            @Override
            public void ok(String amount) {
                if (!StringUtils.isEmpty(amount)) {
                    buyDiscountQuanxian(amount);
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

    public void buyDiscountQuanxian(String month) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).buyDiscount_quota(Integer.parseInt(month), MchInfoLogic.getMchId())
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
        getDiscountListData();
    }
}
