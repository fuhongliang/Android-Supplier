package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.DiscountPackageGoodsAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.DiscountPackageGoods;
import cn.ifhu.supplier.model.bean.DiscountPackageInfoBean;
import cn.ifhu.supplier.model.bean.DiscountPackagePostBean;
import cn.ifhu.supplier.model.bean.ProductManageBean;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.ExpandListView;
import io.reactivex.Observer;

/**
 * @author fuhongliang
 */
public class AddDiscountPackageActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.listView)
    ExpandListView listView;

    @BindView(R.id.swh_status)
    Switch swhStatus;
    @BindView(R.id.tv_package_price)
    TextView tvPackagePrice;
    @BindView(R.id.tv_save)
    TextView tvSave;

    String bundling_id;
    DiscountPackageGoodsAdapter discountPackageGoodsAdapter;
    List<ProductManageBean.GoodsListBean> mDataList;
    double price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_package);
        ButterKnife.bind(this);
        bundling_id = getIntent().getStringExtra("bundling_id");
        if (StringUtils.isEmpty(bundling_id)) {
            tvTitle.setText("添加活动");
        } else {
            tvTitle.setText("编辑活动");
            getDiscountInfo();
        }
        mDataList = new ArrayList<>();
        discountPackageGoodsAdapter = new DiscountPackageGoodsAdapter(mDataList, this);
        discountPackageGoodsAdapter.setOnClickItem(position -> {
            mDataList.remove(position);
            discountPackageGoodsAdapter.setmDataList(mDataList);
            setTvPackagePrice(mDataList);
            ProductLogic.saveDiscountGoods(mDataList);
        });
        listView.setAdapter(discountPackageGoodsAdapter);

    }

    public void getDiscountInfo() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getDiscountPackageinfo(bundling_id, MchInfoLogic.getMchId() + "")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<DiscountPackageInfoBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<DiscountPackageInfoBean> t) throws Exception {
                initData(t.getData());
            }
        });
    }

    public void initData(DiscountPackageInfoBean discountInfoBean) {
        etName.setText(discountInfoBean.getBl_name());
        tvPackagePrice.setText(discountInfoBean.getBl_price());
        swhStatus.setChecked(discountInfoBean.getBl_state() == 1 ? true : false);
        for (DiscountPackageInfoBean.GoodsListBean goodsListBean : discountInfoBean.getGoods_list()) {
            ProductManageBean.GoodsListBean goodsbean = new ProductManageBean.GoodsListBean();
            goodsbean.setGoods_id(goodsListBean.getGoods_id());
            goodsbean.setGoods_name(goodsListBean.getGoods_name());
            goodsbean.setGoods_dicountprice(goodsListBean.getGoods_price());
            goodsbean.setGoods_price(goodsListBean.getGoods_origin_price());
            goodsbean.setImg_name(goodsListBean.getImg_name());
            goodsbean.setImg_path(discountInfoBean.getImg_path());
            mDataList.add(goodsbean);
        }
        discountPackageGoodsAdapter.setmDataList(mDataList);
        ProductLogic.saveDiscountGoods(mDataList);
    }


    @OnClick(R.id.tv_save)
    public void onBtnSaveClicked() {
        if (checkContent()) {
            setLoadingMessageIndicator(true);
            DiscountPackagePostBean discountPostBean = new DiscountPackagePostBean();
            discountPostBean.setBundling_name(etName.getText().toString().trim());
            discountPostBean.setDiscount_price(tvPackagePrice.getText().toString().trim());
            discountPostBean.setStore_id(MchInfoLogic.getMchId()+ "");
            discountPostBean.setBl_state(swhStatus.isChecked() ? 1 : 0);
            if (!StringUtils.isEmpty(bundling_id)) {
                discountPostBean.setBundling_id(bundling_id);
            }

            List<DiscountPackageGoods> goodsList = new ArrayList<>();
            for (ProductManageBean.GoodsListBean bean : mDataList) {
                DiscountPackageGoods discountGoods = new DiscountPackageGoods();
                discountGoods.setGoods_id(bean.getGoods_id() + "");
                discountGoods.setGoods_price(bean.getGoods_dicountprice());
                goodsList.add(discountGoods);
            }
            discountPostBean.setGoods_list(goodsList);
            RetrofitAPIManager.create(OperationService.class).AddOrEditDiscountPackage(discountPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Observer> t) throws Exception {
                    ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                    ProductLogic.clearDiscountGoods();
                    finish();
                }
            });
        }
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(etName.getText().toString().trim())) {
            ToastHelper.makeText("请输入活动名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (mDataList == null || mDataList.size() < 1) {
            ToastHelper.makeText("请添加商品", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (ProductLogic.getDiscountGoods() != null) {
                mDataList = ProductLogic.getDiscountGoods();
            }
            discountPackageGoodsAdapter.setmDataList(mDataList);
            setTvPackagePrice(mDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setTvPackagePrice(List<ProductManageBean.GoodsListBean> mDataList){
        price = 0.0;
        if (mDataList != null && mDataList.size()>0){
            for (ProductManageBean.GoodsListBean goodsListBean :mDataList){
                price = price + Double.parseDouble(goodsListBean.getGoods_dicountprice());
            }
        }
        tvPackagePrice.setText(""+price);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
        ProductLogic.clearDiscountGoods();
    }

    @OnClick(R.id.ll_add)
    public void onLlAddClicked() {
        startActivity(new Intent(AddDiscountPackageActivity.this, DiscountAddProductActivity.class));
    }

}
