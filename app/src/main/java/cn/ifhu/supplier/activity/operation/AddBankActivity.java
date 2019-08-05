package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.WebViewActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.CategoryWheelItem;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DeviceUtil;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import jsc.kit.wheel.dialog.ColumnWheelDialog;

/**
 * @author fuhongliang
 */
public class AddBankActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_bank_number)
    EditText etBankNumber;
    @BindView(R.id.et_bank_address)
    EditText etBankAddress;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_save)
    TextView tvSave;
    ColumnWheelDialog dialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
        tvTitle.setText("添加银行卡");
    }


    public boolean checkEmpty() {                                       //public为公开的 void是没有返回数据 方法名称(){}
        if (StringUtils.isEmpty(etName.getText().toString())) {         //判断输入框是否为空、StingUtils是封装好了的.isEmpty(控件的命名.get类型().toString装换为字符串)
            ToastHelper.makeText("请输入姓名").show();                    //为空的话提示用户ToastHelper.makeText("提示").显示
            return false;                                                //返回值
        }

        if (StringUtils.isEmpty(etBankNumber.getText().toString())) {
            ToastHelper.makeText("请输入卡号").show();
            return false;
        }
        if (StringUtils.isEmpty(etBankAddress.getText().toString())) {
            ToastHelper.makeText("请输入开户银行").show();
            return false;
        }

        return true;
    }

    public void addBankAccount() {
        if (checkEmpty()) {
            setLoadingMessageIndicator(true);
            RetrofitAPIManager.create(OperationService.class).addBankAccount(MchInfoLogic.getMchId() + "",
                    etName.getText().toString(),
                    etBankNumber.getText().toString(),
                    etBankAddress.getText().toString(),
                    tvBankName.getText().toString())
                    .compose(SchedulerUtils.ioMainScheduler())
                    .subscribe(new BaseObserver<Object>(true) {
                        @Override
                        protected void onApiComplete() {
                            setLoadingMessageIndicator(false);
                        }

                        @Override
                        protected void onSuccees(BaseEntity t) throws Exception {
                            ToastHelper.makeText(t.getMessage()).show();
                            startActivity(new Intent(AddBankActivity.this, BindingSuccessActivity.class));
                        }
                    });
        }
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
        addBankAccount();
    }

    @OnClick(R.id.tv_bank_name)
    public void onTvBankNameClicked() {
        if (dialog == null) {
            dialog = createDialog();
        } else {
            dialog.show();
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
            tvBankName.setText(result);
            return false;
        });
        dialog.setItems(initItems(), null, null, null, null);
        dialog.setSelected(0, 0, 0, 0, 0);
        return dialog;
    }

    private CategoryWheelItem[] initItems() {
        final CategoryWheelItem[] items;
        try {
            List<String> stringList = ProductLogic.getBankList();
            items = new CategoryWheelItem[stringList.size()];
            for (int i = 0; i < stringList.size(); i++) {
                items[i] = new CategoryWheelItem(stringList.get(i), 0);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CategoryWheelItem[1];
    }

    @OnClick(R.id.tv_agreement)
    public void onTvAgreementClicked() {
        WebViewActivity.startLoadAssetsHtml(AddBankActivity.this, "protocol.html", "服务协议");
    }
}
