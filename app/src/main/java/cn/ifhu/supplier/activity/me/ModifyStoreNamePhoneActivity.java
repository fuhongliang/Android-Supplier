package cn.ifhu.supplier.activity.me;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.post.ModifyStoreInfoBean;
import cn.ifhu.supplier.net.MeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class ModifyStoreNamePhoneActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_input)
    EditText etInput;

    public static final String TITLE = "TITLE";
    public static final String CONTENT = "CONTENT";
    public static final String TYPE = "TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_phone);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra(TITLE));
        etInput.setText(getIntent().getStringExtra(CONTENT));
    }

    /**
     * 0 店铺名字
     * 1 联系人
     * 2 联系电话
     * 3 客服电话
     * 4 详细地址
     */
    public void changeStoreInfo() {
        setLoadingMessageIndicator(true);
        ModifyStoreInfoBean modifyStoreInfoBean = new ModifyStoreInfoBean();
        switch (getIntent().getIntExtra(TYPE, 0)) {
            case 0:
                modifyStoreInfoBean.setName(etInput.getText().toString());
                break;
            case 1:
                modifyStoreInfoBean.setRealname(etInput.getText().toString());
                break;
            case 2:
                modifyStoreInfoBean.setTel(etInput.getText().toString());
                break;
            case 3:
                modifyStoreInfoBean.setService_tel(etInput.getText().toString());
                break;
            case 4:
                modifyStoreInfoBean.setAddress(etInput.getText().toString());
                break;
            default:
                break;
        }

        RetrofitAPIManager.create(MeService.class).modifyStoreInfo(modifyStoreInfoBean)
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

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        goBack();
    }

    @OnClick(R.id.btn_ok)
    public void onBtnFeedbackClicked() {
        if (checkContent()) {
            changeStoreInfo();
        }
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(etInput.getText().toString().trim())) {
            return false;
        }
        return true;
    }
}
