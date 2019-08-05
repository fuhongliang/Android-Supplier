package cn.ifhu.supplier.activity.operation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.CategoryWheelItem;
import cn.ifhu.supplier.model.newbean.data.FileBean;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.model.newbean.data.ProductDetailBean;
import cn.ifhu.supplier.model.newbean.data.ProductListBean;
import cn.ifhu.supplier.model.newbean.data.SystemClassBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.GoodsShelvesBean;
import cn.ifhu.supplier.model.newbean.post.ImagePostBean;
import cn.ifhu.supplier.model.newbean.post.ProductAdditionBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UploadFileService;
import cn.ifhu.supplier.utils.DeviceUtil;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.ImageChooseUtil;
import cn.ifhu.supplier.utils.ImagePressUtils;
import cn.ifhu.supplier.utils.PostGoodsUtils;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import jsc.kit.wheel.dialog.ColumnWheelDialog;

import static cn.ifhu.supplier.utils.Base64Utils.encode;


/**
 * 发布商品页面
 * @author fuhongliang
 */
public class AddProductOneActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_product_name)
    EditText etProductName;

    @BindView(R.id.iv_cover_pic)
    GlideImageView ivCoverPic;
    @BindView(R.id.iv_goods_pic)
    GlideImageView ivGoodsPic;
    @BindView(R.id.tv_pt_cat_id)
    TextView tvPtCatId;
    @BindView(R.id.tv_goods_cat_id)
    TextView tvGoodsCatId;
    @BindView(R.id.et_unit)
    EditText etUnit;
    @BindView(R.id.et_virtual_sales)
    EditText etVirtualSales;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.et_original_price)
    EditText etOriginalPrice;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_pieces)
    EditText etPieces;
    @BindView(R.id.et_forehead)
    EditText etForehead;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.et_goods_num)
    EditText etGoodsNum;

    @BindView(R.id.tv_btn_one)
    TextView tvBtnOne;
    @BindView(R.id.tv_btn_two)
    TextView tvBtnTwo;
    @BindView(R.id.tv_btn_three)
    TextView tvBtnThree;
    @BindView(R.id.tv_btn_four)
    TextView tvBtnFour;
    @BindView(R.id.tv_btn_fives)
    TextView tvBtnFives;
    @BindView(R.id.tv_btn_six)
    TextView tvBtnSix;
    @BindView(R.id.tv_btn_seven)
    TextView tvBtnSeven;
    @BindView(R.id.tv_btn_eight)
    TextView tvBtnEight;

    //编辑页面
    ProductListBean.GoodsBean goodsBean;

    boolean isChooseCoverPic = true;
    ColumnWheelDialog dialog = null;
    ColumnWheelDialog dialogSystem = null;
    //平台分类
    String mClassId;

    //商品分类
    String mCategoryId;

    //存储选择的状态
    HashMap<TextView, Boolean> hashMap = new HashMap<>();
    @BindView(R.id.iv_delete_cover_pic)
    ImageView ivDeleteCoverPic;
    @BindView(R.id.iv_delete_goods_pic)
    ImageView ivDeleteGoodsPic;

    String mCoverPicPath = "";
    String mGoodsPicPath = "";
    @BindView(R.id.ll_label)
    LinearLayout mLlLabel;
    @BindView(R.id.ll_goods_num)
    LinearLayout mLlGoodsNum;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_one);
        ButterKnife.bind(this);
        initHashmap();
        getSystemClass();
        if (getDataInt() == 0) {
            tvTitle.setText("新建商品");
            PostGoodsUtils.saveGoodsDescr("请输入商品描述");
        } else {
            tvTitle.setText("编辑商品");
            getProductDetail(getIntent().getIntExtra("edit_goods_id", 0));
            mLlLabel.setVisibility(View.GONE);
            mLlGoodsNum.setVisibility(View.GONE);
            mTvLabel.setVisibility(View.GONE);
        }
    }

    public void initHashmap() {
        hashMap.put(tvBtnOne, false);
        hashMap.put(tvBtnTwo, false);
        hashMap.put(tvBtnThree, false);
        hashMap.put(tvBtnFour, false);
        hashMap.put(tvBtnFives, false);
        hashMap.put(tvBtnSix, false);
        hashMap.put(tvBtnSeven, false);
        hashMap.put(tvBtnEight, false);
    }

    /**
     * 获取平台分类
     */
    public void getSystemClass() {
        setLoadingMessageIndicator(true);
        BasePostBean basePostBean = new BasePostBean();
        RetrofitAPIManager.create(OperationService.class).getSystemClass(basePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<SystemClassBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<SystemClassBean> t) throws Exception {
                PostGoodsUtils.saveSystemClass(t.getData().getPt_cats());
            }
        });
    }

    /**
     * 获取商品详情
     */
    public void getProductDetail(int goodsId) {
        setLoadingMessageIndicator(true);
        GoodsShelvesBean goodsShelvesBean = new GoodsShelvesBean();
        goodsShelvesBean.setGoods_id(goodsId);
        RetrofitAPIManager.create(OperationService.class).getGoodsDetail(goodsShelvesBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductDetailBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ProductDetailBean> t) throws Exception {
                initGoodsInfo(t.getData().getGoods());
            }
        });
    }

    String mService = "";
    String mGoodsNum = "";

    public void initGoodsInfo(ProductDetailBean.GoodsBean goods) {
        // 商品名称
        etProductName.setText(goods.getGoods_name());
        // 售价
        etPrice.setText(goods.getPrice());
        // 原价
        etOriginalPrice.setText(goods.getOriginal_price());
        // 商品描述
        mTvDesc.setText("已添加");
        PostGoodsUtils.saveGoodsDescr(goods.getDetail());
        // 缩略图
        ivCoverPic.load(goods.getCover_pic());
        mCoverPicPath = goods.getCover_pic();
        // 主图
        ivGoodsPic.load(goods.getGoods_pic().get(0));
        mGoodsPicPath = goods.getGoods_pic().get(0);
        etUnit.setText(goods.getUnit());
        etVirtualSales.setText(goods.getVirtual_sales() + "");
        etWeight.setText(goods.getWeight() + "");
        etGoodsNum.setText(goods.getGoods_num() + "");
        etPieces.setText(goods.getFull_cut().getPieces() + "");
        etForehead.setText(goods.getFull_cut().getForehead() + "");
        mClassId = goods.getPt_cat_id() + "";
        mCategoryId = goods.getGoods_cat_id() + "";
        try {
            List<ProductCatsBean.CatsBean> classListBeanList = ProductLogic.getClassList();
            for (ProductCatsBean.CatsBean catsBean : classListBeanList) {
                if (mCategoryId.equals(catsBean.getId() + "")) {
                    tvGoodsCatId.setText(catsBean.getName());
                }
            }
            List<SystemClassBean.PtCatsBean> ptCatsBeanList = PostGoodsUtils.getSystemClassList();
            for (SystemClassBean.PtCatsBean catsBean : ptCatsBeanList) {
                if (mClassId.equals(catsBean.getId() + "")) {
                    tvPtCatId.setText(catsBean.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mService = goods.getService();
        mGoodsNum = goods.getGoods_num() + "";
    }

    /**
     * 商品添加接口
     */
    public void uploadPublishGoods(boolean isModifyGoods) {
        setLoadingMessageIndicator(true);
        ProductAdditionBean productAdditionBean = new ProductAdditionBean();
        if (isModifyGoods) {
            productAdditionBean.setGoods_id(getIntent().getIntExtra("edit_goods_id", 0) + "");
        }
        productAdditionBean.setName(getEditTextString(etProductName));
        productAdditionBean.setDetail(PostGoodsUtils.getGoodsDescr());
        productAdditionBean.setCover_pic(mCoverPicPath);
        List<String> goodsPics = new ArrayList<>();
        goodsPics.add(mGoodsPicPath);
        productAdditionBean.setGoods_pic(goodsPics);
        productAdditionBean.setPt_cat_id(mClassId);
        productAdditionBean.setGoods_cat_id(mCategoryId);
        productAdditionBean.setUnit(getEditTextString(etUnit));
        productAdditionBean.setVirtual_sales(getEditTextString(etVirtualSales));
        productAdditionBean.setWeight(getEditTextString(etWeight));
        productAdditionBean.setOriginal_price(getEditTextString(etOriginalPrice));
        productAdditionBean.setPrice(getEditTextString(etPrice));
        productAdditionBean.setPieces(getEditTextString(etPieces));
        productAdditionBean.setForehead(getEditTextString(etForehead));
        if (getDataInt() == 0) {
            productAdditionBean.setService(getTags());
            productAdditionBean.setGoods_num(getEditTextString(etGoodsNum));
        } else {
            productAdditionBean.setService(mService);
            productAdditionBean.setGoods_num(mGoodsNum);
        }
        productAdditionBean.setService(getTags());
        if (isModifyGoods) {
            RetrofitAPIManager.create(OperationService.class).modifyGoods(productAdditionBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity t) throws Exception {
                    ToastHelper.makeText(t.getMessage()).show();
                    finish();
                }
            });
        } else {
            RetrofitAPIManager.create(OperationService.class).publishGoods(productAdditionBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity t) throws Exception {
                    ToastHelper.makeText(t.getMessage()).show();
                    finish();
                }
            });
        }

    }

    public String getEditTextString(EditText editText) {
        if (editText != null) {
            return editText.getText().toString();
        }
        return "";
    }

    /**
     * 检查是否为空
     */
    public boolean examination() {
        if (StringUtils.isEmpty(etProductName.getText().toString())) {
            ToastHelper.makeText("请输入商品名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(PostGoodsUtils.getGoodsDescr())) {
            ToastHelper.makeText("请输入商品描述", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(tvPtCatId.getText().toString().trim())) {
            ToastHelper.makeText("请输入平台分类id", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(tvGoodsCatId.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品分类id", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(etWeight.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品重量", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(etOriginalPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品原价", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品售价", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(etGoodsNum.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品库存", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(etForehead.getText().toString())) {
            etForehead.setText("0");
        }
        if (StringUtils.isEmpty(etPieces.getText().toString())) {
            etPieces.setText("0");
        }

        if (StringUtils.isEmpty(mCoverPicPath)) {
            ToastHelper.makeText("请选择缩略图", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }
        if (StringUtils.isEmpty(mGoodsPicPath)) {
            ToastHelper.makeText("请选择商品图片", Toast.LENGTH_SHORT, ToastHelper.CURRENTTOAST).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (examination()) {
            DialogUtils.showConfirmDialog("温馨提示", "如需对商品更多设置\n请至电脑端后台设置", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                @Override
                public void cancel() {
                }

                @Override
                public void ok() {
                    if (getDataInt() == 0) {
                        uploadPublishGoods(false);
                    } else {
                        uploadPublishGoods(true);
                    }
                }
            });
        }
    }

    public String getTags() {
        String tags = "";
        for (Map.Entry<TextView, Boolean> entry : hashMap.entrySet()) {
            if (entry.getValue()) {
                tags = tags + entry.getKey().getText().toString() + ",";
            }
        }

        try {
            int index = tags.lastIndexOf(",");
            tags = tags.substring(0, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tags;
    }

    //标签字符串
    public boolean overFourInHashMap() {
        int amout = 0;
        for (Map.Entry<TextView, Boolean> entry : hashMap.entrySet()) {
            if (entry.getValue()) {
                amout += 1;
            }
        }
        return amout >= 4 ? true : false;
    }

    public void chooseTags(View view) {
        if (hashMap.get(view)) {
            ((TextView) view).setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_btn_label));
            hashMap.put((TextView) view, !hashMap.get(view));
        } else {
            if (overFourInHashMap()) {
                //提示不能超过四个
                ToastHelper.makeText("选择不能超过四个").show();
            } else {
                ((TextView) view).setTextColor(Color.WHITE);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_blue));
                hashMap.put((TextView) view, !hashMap.get(view));
            }
        }
    }


    @OnClick({R.id.tv_btn_one, R.id.tv_btn_two, R.id.tv_btn_three, R.id.tv_btn_four, R.id.tv_btn_fives, R.id.tv_btn_six, R.id.tv_btn_seven, R.id.tv_btn_eight, R.id.iv_cover_pic, R.id.iv_goods_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_one:
            case R.id.tv_btn_two:
            case R.id.tv_btn_three:
            case R.id.tv_btn_four:
            case R.id.tv_btn_fives:
            case R.id.tv_btn_six:
            case R.id.tv_btn_seven:
            case R.id.tv_btn_eight:
                chooseTags(view);
                break;
            case R.id.iv_cover_pic:
                isChooseCoverPic = true;
                showSelectPicPage();
                break;
            case R.id.iv_goods_pic:
                isChooseCoverPic = false;
                showSelectPicPage();
                break;
            default:
                break;

        }
    }

    /**
     * 跳转选择图片页面
     */
    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(AddProductOneActivity.this, ImageChooseUtil.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageChooseUtil.REQUEST_CODE:
                    List<Uri> stringList = Matisse.obtainResult(data);
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

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            if (isChooseCoverPic) {
                ivCoverPic.load(resultUri.getPath());
                ivDeleteCoverPic.setVisibility(View.VISIBLE);
                upLoadImage(true, resultUri.getPath());
            } else {
                ivGoodsPic.load(resultUri.getPath());
                ivDeleteGoodsPic.setVisibility(View.VISIBLE);
                upLoadImage(false, resultUri.getPath());
            }
        } else {
            Toast.makeText(this, "剪切失败，请重新选择", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.tv_pt_cat_id)
    public void onTvPtCatIdClicked() {
        showStoreClass(false);
    }


    @OnClick(R.id.tv_goods_cat_id)
    public void onTvGoodsCatIdClicked() {
        showStoreClass(true);
    }

    public void showStoreClass(boolean isGoodsCatId) {
        if (isGoodsCatId) {
            if (dialog == null) {
                dialog = createDialog(isGoodsCatId);
            } else {
                dialog.show();
            }
        } else {
            if (dialogSystem == null) {
                dialogSystem = createDialog(isGoodsCatId);
            } else {
                dialogSystem.show();
            }
        }
    }

    private ColumnWheelDialog createDialog(boolean isGoodsCatId) {
        ColumnWheelDialog<CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem> dialog = new ColumnWheelDialog<>(this);
        dialog.show();
        dialog.setTextSize(DeviceUtil.dip2px(18));
        dialog.setTitle("");
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", (v, item0, item1, item2, item3, item4) -> {
            String result = "";
            if (item0 != null) {
                result += item0.getShowText();
                if (isGoodsCatId) {
                    mCategoryId = item0.getId() + "";
                    tvGoodsCatId.setText(result);
                } else {
                    mClassId = item0.getId() + "";
                    tvPtCatId.setText(result);
                }
            }

            return false;
        });
        dialog.setItems(initItems(isGoodsCatId), null, null, null, null);
        dialog.setSelected(0, 0, 0, 0, 0);
        return dialog;
    }

    private CategoryWheelItem[] initItems(boolean isGoodsCatId) {
        final CategoryWheelItem[] items;
        try {
            if (isGoodsCatId) {
                List<ProductCatsBean.CatsBean> classListBeanList = ProductLogic.getClassList();
                items = new CategoryWheelItem[classListBeanList.size()];
                for (int i = 0; i < classListBeanList.size(); i++) {
                    items[i] = new CategoryWheelItem(classListBeanList.get(i).getName(), classListBeanList.get(i).getId());
                }
            } else {
                List<SystemClassBean.PtCatsBean> classListBeanList = PostGoodsUtils.getSystemClassList();
                items = new CategoryWheelItem[classListBeanList.size()];
                for (int i = 0; i < classListBeanList.size(); i++) {
                    items[i] = new CategoryWheelItem(classListBeanList.get(i).getName(), classListBeanList.get(i).getId());
                }
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CategoryWheelItem[1];
    }

    @OnClick(R.id.iv_delete_cover_pic)
    public void onIvDeleteCoverPicClicked() {
        ivCoverPic.load("");
        mCoverPicPath = "";
        ivDeleteCoverPic.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_delete_goods_pic)
    public void onIvDeleteGoodsPicClicked() {
        ivGoodsPic.load("");
        mGoodsPicPath = "";
        ivDeleteGoodsPic.setVisibility(View.INVISIBLE);
    }

    public void upLoadImage(boolean isCoverPic, String cardPath) {
        setLoadingMessageIndicator(true);
        ImagePostBean imagePostBean = new ImagePostBean();
        byte[] byteArray = ImagePressUtils.getCompressedbyteArray(cardPath);
        try {
            imagePostBean.setImage(encode(byteArray));
            imagePostBean.setSize(byteArray.length + "");
            imagePostBean.setExt(".jpg");
            imagePostBean.setType("image");
        } catch (Exception e) {
            e.printStackTrace();
        }

        RetrofitAPIManager.createUpload(UploadFileService.class).imageUpload(imagePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<FileBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<FileBean> t) throws Exception {
                if (isCoverPic) {
                    mCoverPicPath = t.getData().getUrl();
                } else {
                    mGoodsPicPath = t.getData().getUrl();
                }
            }
        });
    }

    @OnClick(R.id.ll_product_desc)
    public void onMLlProductDescClicked() {
        goToActivity(GoodsDescriptionActivity.class);
    }
}
