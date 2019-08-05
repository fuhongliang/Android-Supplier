package cn.ifhu.supplier.activity.operation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.CategoryWheelItem;
import cn.ifhu.supplier.model.bean.EditGoodsBean;
import cn.ifhu.supplier.model.bean.SellingTime;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UploadFileService;
import cn.ifhu.supplier.model.newbean.data.ProductDetailBean;
import cn.ifhu.supplier.model.newbean.data.ProductListBean;
import cn.ifhu.supplier.model.newbean.post.GoodsShelvesBean;
import cn.ifhu.supplier.utils.DeviceUtil;
import cn.ifhu.supplier.utils.ImageChooseUtil;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;
import jsc.kit.wheel.dialog.ColumnWheelDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author fuhongliang
 */
public class EditProductActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_product_name)
    EditText etProductName;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_original_price)
    EditText etOriginalPrice;
//    @BindView(R.id.tv_selling_time)
//    TextView tvSellingTime;

    @BindView(R.id.et_product_desr)
    EditText etProductDesr;
    @BindView(R.id.btn_save)
    Button btnSave;
    ColumnWheelDialog dialog = null;
    int categoryId;
    @BindView(R.id.swh_shock)
    Switch swhShock;

    List<SellingTime> sellingTimeList = new ArrayList<>();
    @BindView(R.id.iv_product_image)
    GlideImageView ivProductImage;

    String cardPath = "";
    @BindView(R.id.ll_reserve)
    LinearLayout llReserve;
    @BindView(R.id.et_kucun)
    EditText etKucun;
    ProductListBean.GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        goodsBean = bundle.getParcelable("product");
        int position = bundle.getInt("position", 0);
        setTvCategory(position);
        swhShock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                llReserve.setVisibility(View.GONE);
            } else {
                llReserve.setVisibility(View.VISIBLE);
            }
        });
        initData();
    }

    public void initData() {
        if (goodsBean != null) {
            etProductName.setText(goodsBean.getName());
            etPrice.setText(goodsBean.getPrice());
            etOriginalPrice.setText(goodsBean.getOriginal_price());
            tvTitle.setFocusable(true);
            tvTitle.setFocusableInTouchMode(true);
            tvTitle.requestFocus();
            tvTitle.requestFocusFromTouch();
        }
    }

    /**
     * 获取商品详情
     */
    public void initGoodsDetail() {

        GoodsShelvesBean goodsShelvesBean = new GoodsShelvesBean();
        goodsShelvesBean.setGoods_id(getDataInt());

        RetrofitAPIManager.create(OperationService.class).getGoodsDetail(goodsShelvesBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductDetailBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ProductDetailBean> t) throws Exception {
                ProductDetailBean.GoodsBean goods = t.getData().getGoods();
                etProductName.setText(goods.getGoods_name());
                etPrice.setText(goods.getPrice());
                etOriginalPrice.setText(goods.getOriginal_price());
                etProductDesr.setText(goods.getDetail());
                ivProductImage.load(goods.getCover_pic());// TODO: 2019-06-25 图片接口待改
                tvTitle.setFocusable(true);
                tvTitle.setFocusableInTouchMode(true);
                tvTitle.requestFocus();
                tvTitle.requestFocusFromTouch();
//                if (goods.get() == 2){
//                    swhShock.setChecked(true);
//                } else {
//                    swhShock.setChecked(false);
//                }

                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });

//        if (goodsBean != null) {
//            etProductName.setText(goodsBean.getName());
//            etPrice.setText(goodsBean.getPrice());
//            etOriginalPrice.setText(goodsBean.getOriginal_price());
//            etProductDesr.setText(goodsBean.getGoods_desc());
//            ivProductImage.load(goodsBean.getImg_name());
//            tvTitle.setFocusable(true);
//            tvTitle.setFocusableInTouchMode(true);
//            tvTitle.requestFocus();
//            tvTitle.requestFocusFromTouch();
//            if (goodsBean.getIs_much() == 2){
//                swhShock.setChecked(true);
//            }else {
//                swhShock.setChecked(false);
//            }
//        }
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        goBack();
    }

    @OnClick(R.id.tv_category)
    public void onTvCategoryClicked() {
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
                categoryId = item0.getId();
            }
            tvCategory.setText(result);
            return false;
        });
        dialog.setItems(initItems(), null, null, null, null);
        dialog.setSelected(0, 0, 0, 0, 0);
        return dialog;
    }

    private CategoryWheelItem[] initItems() {
        final CategoryWheelItem[] items;
        try {
            List<ProductCatsBean.CatsBean> classListBeanList = ProductLogic.getClassList();
            items = new CategoryWheelItem[classListBeanList.size()];
            for (int i = 0; i < classListBeanList.size(); i++) {
                items[i] = new CategoryWheelItem(classListBeanList.get(i).getName(), classListBeanList.get(i).getMch_id());
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CategoryWheelItem[1];
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (!checkContentEmpty()) {
            if (StringUtils.isEmpty(cardPath)) {
                // TODO: 2019-06-25 修改信息接口
//                postProduct(goodsBean.getImg_name());
            } else {
                upLoadImage();

            }
        }
    }

    public void postProduct(String url) {
        EditGoodsBean editGoodsBean = new EditGoodsBean();
        editGoodsBean.setGoods_name(etProductName.getText().toString());
        editGoodsBean.setGoods_id(goodsBean.getGoods_id());
        editGoodsBean.setGoods_price(Double.parseDouble(etPrice.getText().toString().trim()));
        editGoodsBean.setOrigin_price(Double.parseDouble(etOriginalPrice.getText().toString().trim()));
        editGoodsBean.setStore_id(MchInfoLogic.getMchId());
        editGoodsBean.setClass_id(categoryId);
        editGoodsBean.setGoods_desc(etProductDesr.getText().toString().trim());
        if (swhShock.isChecked()) {

        } else {
            try {
                editGoodsBean.setGoods_storage(Integer.parseInt(etKucun.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SellingTime sellingTime = new SellingTime();
        sellingTime.setStart_time("00:00");
        sellingTime.setEnd_time("23:59");
        List<SellingTime> sellingTimes = new ArrayList<>();
        sellingTimes.add(sellingTime);
        editGoodsBean.setSell_time(sellingTimes);
        editGoodsBean.setImg_name(url);
        // TODO: 2019-06-25 提交修改后的商品
//        RetrofitAPIManager.create(OperationService.class).editGoods(editGoodsBean)
//                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
//            @Override
//            protected void onApiComplete() {
//                setLoadingMessageIndicator(false);
//            }
//
//            @Override
//            protected void onSuccees(BaseEntity<Object> t) throws Exception {
//                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
//                finish();
//            }
//        });
    }

    public void upLoadImage() {
        setLoadingMessageIndicator(true);
        File file = new File(cardPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitAPIManager.createUpload(UploadFileService.class).imageUpload(body)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<String>(true) {
            @Override
            protected void onApiComplete() {
            }

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                postProduct(t.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                setLoadingMessageIndicator(false);
            }
        });
    }

    public boolean checkContentEmpty() {
        if (StringUtils.isEmpty(etProductName.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品价格", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(etOriginalPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品原价", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (Double.parseDouble(etOriginalPrice.getText().toString().trim()) < Double.parseDouble(etPrice.getText().toString().trim())) {
            ToastHelper.makeText("商品原价不能小于商品价格", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        return false;
    }

    public boolean clearContent() {
        etProductName.setText("");
        etPrice.setText("");
        etOriginalPrice.setText("");
        etProductDesr.setText("");
        return false;
    }

    @OnClick(R.id.ll_selling_time)
    public void onViewClicked() {
        startActivity(new Intent(EditProductActivity.this, SellingTimeActivity.class));
    }

    public void setTvCategory(int position) {
        try {
            tvCategory.setText(ProductLogic.getClassList().get(position).getName());
            categoryId = ProductLogic.getClassList().get(position).getMch_id();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(EditProductActivity.this, ImageChooseUtil.REQUEST_CODE);
    }


    @OnClick(R.id.rl_choose_pic)
    public void onRlChoosePicClicked() {
        showSelectPicPage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageChooseUtil.REQUEST_CODE:
                    List<Uri> stringList = Matisse.obtainResult(data);
                    if (stringList != null && stringList.size() > 0) {
                        Glide.with(this).load(stringList.get(0)).into(ivProductImage);
                    }
                    startCrop(stringList.get(0));
                    break;
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;
                default:
                    break;
            }
        }
    }

    public void startCrop(@NonNull Uri uri) {
        String destinationFileName = System.currentTimeMillis() + ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
        uCrop.start(this);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);
        return uCrop.withOptions(options);
    }

    private UCrop basisConfig(@NonNull UCrop uCrop) {
        uCrop = uCrop.useSourceImageAspectRatio();
        return uCrop;
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            ivProductImage.load(resultUri.getPath());
            cardPath = resultUri.getPath();
        } else {
            Toast.makeText(this, "剪切失败，请重新选择", Toast.LENGTH_SHORT).show();
        }
    }
}
