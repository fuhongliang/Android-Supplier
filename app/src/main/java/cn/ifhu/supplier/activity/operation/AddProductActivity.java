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
import cn.ifhu.supplier.model.bean.AddGoodsBean;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.CategoryWheelItem;
import cn.ifhu.supplier.model.bean.SellingTime;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UploadFileService;
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
public class AddProductActivity extends BaseActivity {

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
    @BindView(R.id.iv_add_tip)
    ImageView ivAddTip;
    @BindView(R.id.tv_add_tip)
    TextView tvAddTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        int position = getIntent().getIntExtra("position", 0);
        tvTitle.setText("新增商品");
        setTvCategory(position);
        swhShock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                llReserve.setVisibility(View.GONE);
            } else {
                llReserve.setVisibility(View.VISIBLE);
            }
        });

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
            upLoadImage();
        }
    }

    public void postProduct(String url) {
        AddGoodsBean addGoodsBean = new AddGoodsBean();
        addGoodsBean.setGoods_name(etProductName.getText().toString());
        addGoodsBean.setGoods_price(Double.parseDouble(etPrice.getText().toString().trim()));
        addGoodsBean.setOrigin_price(Double.parseDouble(etOriginalPrice.getText().toString().trim()));
        addGoodsBean.setStore_id(MchInfoLogic.getMchId());
        addGoodsBean.setClass_id(categoryId);
        addGoodsBean.setGoods_desc(etProductDesr.getText().toString().trim());
        if (swhShock.isChecked()) {

        } else {
            try {
                addGoodsBean.setGoods_storage(Integer.parseInt(etKucun.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SellingTime sellingTime = new SellingTime();
        sellingTime.setStart_time("00:00");
        sellingTime.setEnd_time("23:59");
        List<SellingTime> sellingTimes = new ArrayList<>();
        sellingTimes.add(sellingTime);
        addGoodsBean.setSell_time(sellingTimes);
        addGoodsBean.setImg_name(url);
        RetrofitAPIManager.create(OperationService.class).addGoods(addGoodsBean)
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

    //图片
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
        if (StringUtils.isEmpty(cardPath)) {
            ToastHelper.makeText("请选择商品图片", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }

        if (StringUtils.isEmpty(etProductName.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品现价", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(etOriginalPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品原价", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }

        if (Double.parseDouble(etOriginalPrice.getText().toString().trim()) < Double.parseDouble(etPrice.getText().toString().trim())) {
            ToastHelper.makeText("商品原价不能小于商品现价", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
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
        startActivity(new Intent(AddProductActivity.this, SellingTimeActivity.class));
    }

    public void setTvCategory(int position) {
        try {
            tvCategory.setText(ProductLogic.getClassList().get(position).getName());
            categoryId = ProductLogic.getClassList().get(position).getMch_id();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<SellingTime> sellingTimes = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            sellingTimes = ProductLogic.getSellingTime();
//            if (sellingTimes == null || sellingTimes.size() == 0) {
//                tvSellingTime.setText("时间无限");
//            } else {
//                String mSellingTime = "";
//                for (SellingTime sellingTime : sellingTimes) {
//                    mSellingTime =   sellingTime + sellingTime.getStart_time() + "~" + sellingTime.getEnd_time();
//                }
//                tvSellingTime.setText(mSellingTime);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(AddProductActivity.this, ImageChooseUtil.REQUEST_CODE);
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
            ivAddTip.setVisibility(View.INVISIBLE);
            tvAddTip.setVisibility(View.INVISIBLE);
            cardPath = resultUri.getPath();
        } else {
            Toast.makeText(this, "剪切失败，请重新选择", Toast.LENGTH_SHORT).show();
        }
    }
}
