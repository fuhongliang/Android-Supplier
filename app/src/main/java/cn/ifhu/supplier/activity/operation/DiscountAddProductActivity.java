package cn.ifhu.supplier.activity.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.CategoryAdapter;
import cn.ifhu.supplier.adapter.DiscountProductAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.ProductManageBean;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmInputDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.DiscountInputDialog;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class DiscountAddProductActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_category)
    ListView lvCategory;
    @BindView(R.id.lv_product)
    ListView lvProduct;
//    List<ProductManageBean.ClassListBean> mDataArray = new ArrayList<>();
    List<ProductCatsBean.CatsBean> mDataArray = new ArrayList<>();
    List<ProductManageBean.GoodsListBean> mProductArray = new ArrayList<>();
    CategoryAdapter mCategoryAdapter;
    DiscountProductAdapter mProductAdapter;

    public int mCurId = 0;
    public int classPosition = 0;
    List<ProductManageBean.GoodsListBean> mSelectedGoodsBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_add_product);
        ButterKnife.bind(this);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.neworder_empty, null);
        lvProduct.setEmptyView(emptyView);
        mCategoryAdapter = new CategoryAdapter(mDataArray, this, new CategoryAdapter.ItemOnclick() {
            @Override
            public void onClickItem(int position) {
                mProductData(mDataArray.get(position).getMch_id());
                mCurId = mDataArray.get(position).getMch_id();
                classPosition = position;
            }
        });
        lvCategory.setAdapter(mCategoryAdapter);
        mProductAdapter = new DiscountProductAdapter(mProductArray, this);
        mProductAdapter.setOnClickItem(new DiscountProductAdapter.onClickItem() {
            @Override
            public void setDiscountPrice(int position) {
                if (getIntent().getBooleanExtra("isFromLimitDiscount",false)){
                    View view = getLayoutInflater().inflate(R.layout.discount_input_dialog, null);
                    DialogUtils.showDiscountConfirmDialog("修改价格",mProductArray.get(position).getGoods_price(), getSupportFragmentManager(),new DiscountInputDialog.ButtonOnclick() {
                        @Override
                        public void ok(String discount_price) {
                            if (!StringUtils.isEmpty(discount_price)) {
                                ProductManageBean.GoodsListBean goodsListBean = mProductArray.get(position);
                                if (Double.parseDouble(discount_price) > Double.parseDouble(goodsListBean.getGoods_price())){
                                    ToastHelper.makeText("折扣价格不能大于商品价格！", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                                }else {
                                    goodsListBean.setGoods_dicountprice(discount_price);
                                    addSelectedGoods(goodsListBean);
                                }
                            }
                            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                        }
                    });
                }else {
                    View view = getLayoutInflater().inflate(R.layout.confirm_input_layout, null);
                    DialogUtils.showInputConfirmDialog("修改价格",mProductArray.get(position).getGoods_price(), getSupportFragmentManager(),new ConfirmInputDialog.ButtonOnclick() {
                        @Override
                        public void ok(String discount_price) {
                            if (!StringUtils.isEmpty(discount_price)) {
                                ProductManageBean.GoodsListBean goodsListBean = mProductArray.get(position);
                                if (Double.parseDouble(discount_price) > Double.parseDouble(goodsListBean.getGoods_price())){
                                    ToastHelper.makeText("优惠价格不能大于商品价格！", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                                }else {
                                    goodsListBean.setGoods_dicountprice(discount_price);
                                    addSelectedGoods(goodsListBean);
                                }
                            }
                            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                        }
                    });
                }

            }

            @Override
            public void unselectedGoods(int position) {
                removeSelectedGoods(mProductArray.get(position).getGoods_id());
            }
        });
        lvProduct.setAdapter(mProductAdapter);
        tvTitle.setText("添加商品");
    }



    public void addSelectedGoods(ProductManageBean.GoodsListBean goodsListBean) {
        try {

            mSelectedGoodsBeans = ProductLogic.getDiscountGoods();
            if (mSelectedGoodsBeans == null){
                mSelectedGoodsBeans = new ArrayList<>();
            }
            mSelectedGoodsBeans.add(goodsListBean);
            ProductLogic.saveDiscountGoods(mSelectedGoodsBeans);
            mProductAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeSelectedGoods(int goodId) {
        try {
            mSelectedGoodsBeans = ProductLogic.getDiscountGoods();
            for (ProductManageBean.GoodsListBean goodsListBean : mSelectedGoodsBeans) {
                if (goodsListBean.getGoods_id() == goodId) {
                    mSelectedGoodsBeans.remove(goodsListBean);
                }
            }
            ProductLogic.saveDiscountGoods(mSelectedGoodsBeans);
            mProductAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(mCurId);
    }

    public void getData(int class_id) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getXianshiGoodsList(MchInfoLogic.getMchId(), class_id)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductCatsBean>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ProductCatsBean> t) throws Exception {
                if (t.getCode()==-1){
                    DialogUtils.showConfirmDialog("温馨提示", "您的店铺还没有商品分类 \n 是否新建商品分类？", "否", "是", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                        @Override
                        public void cancel() {
                            finish();
                        }

                        @Override
                        public void ok() {
                            startActivity(new Intent(DiscountAddProductActivity.this, AddOrEditCategoryActivity.class));
                        }
                    });
                } else {
                    mDataArray = t.getData().getCats();
                    mCategoryAdapter.setmDataList(mDataArray);
//                    mProductArray = t.getCatsData().getGoods_list();
//                    mProductAdapter.setmDataList(mProductArray);
//                    ProductLogic.saveClass(mDataArray);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastHelper.makeText(e.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                finish();
            }
        });
    }


    public void mProductData(int class_id) {
        getData(class_id);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @OnClick(R.id.tv_finish)
    public void onTvFinishClicked() {
        ProductLogic.saveDiscountGoods(mSelectedGoodsBeans);
        finish();
    }
}
