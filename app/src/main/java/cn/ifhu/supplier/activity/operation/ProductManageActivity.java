package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import cn.ifhu.supplier.adapter.CategoryAdapter;
import cn.ifhu.supplier.adapter.ProductAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.model.newbean.data.ProductListBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.GoodsListPostBean;
import cn.ifhu.supplier.model.newbean.post.GoodsShelvesBean;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ToastHelper;

import static cn.ifhu.supplier.utils.Constants.DATA;
import static cn.ifhu.supplier.utils.MchInfoLogic.getMchAccessToken;

/**
 * 商品管理页面
 *
 * @author fuhongliang
 */
public class ProductManageActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_category)
    ListView lvCategory;
    @BindView(R.id.lv_product)
    ListView lvProduct;
    List<ProductCatsBean.CatsBean> mDataArray = new ArrayList<>();
    List<ProductListBean.GoodsBean> mProductArray = new ArrayList<>();
    CategoryAdapter mCategoryAdapter;
    ProductAdapter mProductAdapter;
    @BindView(R.id.rl_manage_category)
    RelativeLayout rlManageCategory;
    @BindView(R.id.rl_add_product)
    RelativeLayout rlAddProduct;
    public int mCatId = 0;
    //分类商品的ID
    public int classPosition = 0;
    //当前商品分类的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.neworder_empty, null);
        lvProduct.setEmptyView(emptyView);
        mCategoryAdapter = new CategoryAdapter(mDataArray, this, position -> {
            mCatId = mDataArray.get(position).getId();
            classPosition = position;
            mCategoryAdapter.notifyDataSetChanged();
            getProductListData(mCatId);
        });
        lvCategory.setAdapter(mCategoryAdapter);

        mProductAdapter = new ProductAdapter(mProductArray, this);
        mProductAdapter.setOnClickItem(new ProductAdapter.onClickItem() {
            @Override
            public void changeState(int position) {
                int operation = mProductArray.get(position).getStatus();
                DialogUtils.showConfirmDialog("温馨提示", "是否确认对该商品" + (operation == 0 ? "上架?" : "下架?"),
                        getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok() {
                                changeProductState(position);
                            }
                        });
            }

            @Override
            public void editProduct(int position) {
                goToEditProduct(position, classPosition);
            }

            @Override
            public void deleteProduct(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否确定删除该商品？", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
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
        lvProduct.setAdapter(mProductAdapter);
        tvTitle.setText("商品管理");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCatsData();
    }

    /**
     * 获取商品分类数据
     */
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
                            startActivity(new Intent(ProductManageActivity.this, AddOrEditCategoryActivity.class));
                        }
                    });
                } else {
                    mDataArray = t.getData().getCats();
                    mCategoryAdapter.setmDataList(mDataArray);
                    if (mDataArray != null && mDataArray.size() > 0) {
                        mCatId = mDataArray.get(classPosition).getId();
                        getProductListData(mCatId);
                    }
                    ProductLogic.saveClass(mDataArray);
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


    /**
     * 获取指定分类的商品列表数据
     *
     * @param catsId 分类ID
     */
    public void getProductListData(int catsId) {
        setLoadingMessageIndicator(true);
        GoodsListPostBean goodsListPostBean = new GoodsListPostBean();
        goodsListPostBean.setCat_id(catsId);
        RetrofitAPIManager.create(OperationService.class).goodsList(goodsListPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductListBean>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ProductListBean> t) throws Exception {
                mProductArray = t.getData().getGoods();
                mProductAdapter.setmDataList(mProductArray);
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
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_manage_category)
    public void onRlManageCategoryClicked() {
        goToActivity(ManageCategoryActivity.class, mCatId);
    }

    @OnClick(R.id.rl_add_product)
    public void onRlAddProductClicked() {
        startActivity(new Intent(ProductManageActivity.this, AddOrEditCategoryActivity.class));
    }

    /**
     * 上下架商品
     *
     * @param position 操作商品的索引
     */
    public void changeProductState(int position) {
        setLoadingMessageIndicator(true);
        GoodsShelvesBean goodsShelvesBean = new GoodsShelvesBean();
        goodsShelvesBean.setGoods_id(mProductArray.get(position).getGoods_id());
        goodsShelvesBean.setStatus(mProductArray.get(position).getStatus() == 0 ? 1 : 0);
        RetrofitAPIManager.create(OperationService.class).goodsShelves(goodsShelvesBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                mProductAdapter.changeProductState(position);
            }
        });
    }

    /**
     * 删除商品
     *
     * @param position 删除商品的索引
     */
    public void delProduct(int position) {
        setLoadingMessageIndicator(true);
        GoodsShelvesBean goodsShelvesBean = new GoodsShelvesBean();
        goodsShelvesBean.setGoods_id(mProductArray.get(position).getGoods_id());
        RetrofitAPIManager.create(OperationService.class).deleteGoods(goodsShelvesBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                mProductArray.remove(position);
                mProductAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 编辑商品
     *
     * @param position         商品列表索引
     * @param curClassPosition
     */
    public void goToEditProduct(int position, int curClassPosition) {
        Intent intent = new Intent(ProductManageActivity.this, AddProductOneActivity.class);
        intent.putExtra(DATA, 1);
        intent.putExtra("edit_goods_id", mProductArray.get(position).getGoods_id());
        startActivity(intent);
    }


    @OnClick(R.id.rl_publish_goods)
    public void onRlPublishGoodsClicked() {
        goToActivity(AddProductOneActivity.class);
    }


    @OnClick(R.id.rl_sort)
    public void onRlSortClicked() {
        goToActivity(SortCategoryActivity.class, getDataInt());
    }
}
