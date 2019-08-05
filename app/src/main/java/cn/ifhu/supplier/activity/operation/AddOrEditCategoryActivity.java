package cn.ifhu.supplier.activity.operation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.post.GoodsCatsAddBean;
import cn.ifhu.supplier.model.newbean.post.ModifyGoodsCat;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;

import static cn.ifhu.supplier.utils.MchInfoLogic.getMchAccessToken;

/**
 * 添加商品分类页面
 *
 * @author fuhongliang
 */
public class AddOrEditCategoryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_cat_name)
    EditText etCatName;
    @BindView(R.id.btn_save)
    Button btnSave;
    int mClassId = 0;
    String mClassName = "";
    int mClassSort = 0;
    @BindView(R.id.et_cat_sort)
    EditText etCatSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_management);
        ButterKnife.bind(this);
        mClassId = getIntent().getIntExtra("ClassId", 0);
        mClassName = getIntent().getStringExtra("ClassName");
        mClassSort = getIntent().getIntExtra("ClassSort",0);
        if (mClassId == 0){
            tvTitle.setText("新建分类");
        }else {
            tvTitle.setText("分类编辑");
            etCatName.setText(mClassName);
            etCatSort.setText(mClassSort+"");
        }

    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (checkContent()) {
            setLoadingMessageIndicator(true);
            if (mClassId == 0) {
                GoodsCatsAddBean goodsCatsAddBean = new GoodsCatsAddBean();
                goodsCatsAddBean.setMch_id(MchInfoLogic.getMchId());
                goodsCatsAddBean.setAccess_token(getMchAccessToken());
                goodsCatsAddBean.setName(etCatName.getText().toString().trim());
                if (!StringUtils.isEmpty(etCatSort.getText().toString().trim())) {
                    goodsCatsAddBean.setSort(etCatSort.getText().toString().trim());
                }
                RetrofitAPIManager.create(OperationService.class).addGoodsCat(goodsCatsAddBean)
                        .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<Object>>(true) {
                    @Override
                    protected void onApiComplete() {
                        setLoadingMessageIndicator(false);
                    }

                    @Override
                    protected void onSuccees(BaseEntity<List<Object>> t) throws Exception {
                        ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                        finish();
                    }
                });
            } else {
                ModifyGoodsCat modifyGoodsCat = new ModifyGoodsCat();
                modifyGoodsCat.setCat_id(mClassId);
                modifyGoodsCat.setName(etCatName.getText().toString().trim());
                modifyGoodsCat.setSort(etCatSort.getText().toString().trim());
                RetrofitAPIManager.create(OperationService.class).modifyGoodsCat(modifyGoodsCat)
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
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(etCatName.getText().toString().trim())) {
            ToastHelper.makeText("分类名称不能为空", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }
        return true;
    }
}
