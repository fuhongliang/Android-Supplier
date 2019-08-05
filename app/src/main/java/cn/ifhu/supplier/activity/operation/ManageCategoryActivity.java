package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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
import cn.ifhu.supplier.adapter.ManageCategoryAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.model.newbean.post.GoodsListPostBean;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

import static cn.ifhu.supplier.utils.MchInfoLogic.getMchAccessToken;

/**
 * @author fuhongliang
 */
public class ManageCategoryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_category)
    ListView lvCategory;
    @BindView(R.id.rl_manage_category)
    RelativeLayout rlManageCategory;
    ManageCategoryAdapter manageCategoryAdapter;
    List<ProductCatsBean.CatsBean> mDataArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageproduct_category);
        ButterKnife.bind(this);
        tvTitle.setText("管理分类");
        manageCategoryAdapter = new ManageCategoryAdapter(mDataArray, this, new ManageCategoryAdapter.ItemOnclick() {
            @Override
            public void onClickEditItem(int position) {
                Intent intent = new Intent(ManageCategoryActivity.this,AddOrEditCategoryActivity.class);
                intent.putExtra("ClassId",mDataArray.get(position).getId());
                intent.putExtra("ClassName",mDataArray.get(position).getName());
                intent.putExtra("ClassSort",mDataArray.get(position).getSort());
                startActivity(intent);
            }

            @Override
            public void onClickAddItem(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否确定删除该分类？", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {
                    }

                    @Override
                    public void ok() {
                        delProduct(position);
                    }
                });
            }
        });

        lvCategory.setAdapter(manageCategoryAdapter);
    }

    private void delProduct(int position){
        GoodsListPostBean goodsListPostBean = new GoodsListPostBean();
        goodsListPostBean.setCat_id(mDataArray.get(position).getId());
        RetrofitAPIManager.create(OperationService.class).deleteCat(goodsListPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                mDataArray.remove(position);
                manageCategoryAdapter.setmDataList(mDataArray);
                ProductLogic.saveClass(mDataArray);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastHelper.makeText("出现异常错误了呢！快联系工程师修复吧", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                // 2019-06-24 当解析出现问题时提示
                finish();
            }
        });
    }

    public void initData() {
        try {
            mDataArray = ProductLogic.getClassList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        getCatsData();
        manageCategoryAdapter.setmDataList(mDataArray);
    }

    public void getCatsData() {
        setLoadingMessageIndicator(true);
        BasePostBean basePostBean = new BasePostBean();
        basePostBean.setAccess_token(getMchAccessToken());
        basePostBean.setMch_id(MchInfoLogic.getMchId());
        RetrofitAPIManager.create(OperationService.class).catsList(basePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductCatsBean>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ProductCatsBean> t) throws Exception {
                if (t.getData().getCats() == null || t.getData().getCats().size() == 0) {
                    DialogUtils.showConfirmDialog("温馨提示", "您的店铺还没有商品分类 \n 是否新建商品分类？", "否", "是", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                        @Override
                        public void cancel() {
                            finish();
                        }

                        @Override
                        public void ok() {
                            startActivity(new Intent(ManageCategoryActivity.this, AddOrEditCategoryActivity.class));
                        }
                    });
                } else {
                    mDataArray = t.getData().getCats();
                    ProductLogic.saveClass(mDataArray);
                    manageCategoryAdapter.setmDataList(mDataArray);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastHelper.makeText("出现异常错误了呢！快联系工程师修复吧", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                finish();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick(R.id.rl_manage_category)
    public void onRlManageCategoryClicked() {
        startActivity(new Intent(ManageCategoryActivity.this,SortCategoryActivity.class));
    }

    @OnClick(R.id.rl_add_category)
    public void onRlAddProductClicked() {
        startActivity(new Intent(ManageCategoryActivity.this, AddOrEditCategoryActivity.class));
    }
}
