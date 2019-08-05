package cn.ifhu.supplier.activity.me;

import android.os.Bundle;
import android.widget.Button;
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
import cn.ifhu.supplier.model.bean.UserBean;
import cn.ifhu.supplier.net.MeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class StoreNoticeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_notice)
    EditText etNotice;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_announcement);
        ButterKnife.bind(this);
        tvTitle.setText("餐厅公告");
        etNotice.setText(UserLogic.getUser().getStore_description()+"");
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        goBack();
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (!StringUtils.isEmpty(etNotice.getText().toString().trim())) {
            setLoadingMessageIndicator(true);
            RetrofitAPIManager.create(MeService.class).storeSetDesc(MchInfoLogic.getMchId(),etNotice.getText().toString().trim())
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Object> t) throws Exception {
                    ToastHelper.makeText(t.getMessage()+"", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                    UserBean loginResponse = UserLogic.getUser();
                    loginResponse.setStore_description(etNotice.getText().toString().trim());
                    UserLogic.saveUser(loginResponse);
                }
            });
        }
    }

}
