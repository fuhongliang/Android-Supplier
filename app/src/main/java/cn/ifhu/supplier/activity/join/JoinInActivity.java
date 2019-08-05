package cn.ifhu.supplier.activity.join;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.GaodeMapActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.CategoryWheelItem;
import cn.ifhu.supplier.model.newbean.data.FileBean;
import cn.ifhu.supplier.model.newbean.data.JoinClassBean;
import cn.ifhu.supplier.model.newbean.data.JoinResultBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.ImagePostBean;
import cn.ifhu.supplier.model.newbean.post.JoinPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UploadFileService;
import cn.ifhu.supplier.net.UserService;
import cn.ifhu.supplier.utils.ApplyUtils;
import cn.ifhu.supplier.utils.DeviceUtil;
import cn.ifhu.supplier.utils.ImageChooseUtil;
import cn.ifhu.supplier.utils.ImagePressUtils;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;
import jsc.kit.wheel.dialog.ColumnWheelDialog;

import static cn.ifhu.supplier.activity.GaodeMapActivity.ADDRESS;
import static cn.ifhu.supplier.activity.GaodeMapActivity.BLOCK;
import static cn.ifhu.supplier.activity.GaodeMapActivity.CITY;
import static cn.ifhu.supplier.activity.GaodeMapActivity.LATITUDE;
import static cn.ifhu.supplier.activity.GaodeMapActivity.LONGITUDE;
import static cn.ifhu.supplier.activity.GaodeMapActivity.PROVINCE;
import static cn.ifhu.supplier.utils.Base64Utils.encode;

/**
 * @author fuhongliang
 */
public class JoinInActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.et_contact_name)
    EditText mEtContactName;
    @BindView(R.id.et_contact_phone)
    EditText mEtContactPhone;
    @BindView(R.id.et_shop_name)
    EditText mEtShopName;
    @BindView(R.id.tv_class)
    TextView mTvClass;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.et_service_tel)
    EditText mEtServiceTel;
    @BindView(R.id.tv_background)
    TextView mTvBackground;
    @BindView(R.id.iv_store_bg)
    GlideImageView mIvStoreBg;
    @BindView(R.id.tv_store_bg)
    TextView mTvStoreBg;
    @BindView(R.id.tv_logo)
    TextView mTvLogo;
    @BindView(R.id.iv_logo)
    GlideImageView mIvLogo;
    @BindView(R.id.tv_store_logo)
    TextView mTvStoreLogo;
    @BindView(R.id.tv_post)
    TextView mTvPost;
    ColumnWheelDialog dialog = null;
    String categoryId = "0";
    String logoPath;
    String bgPath;
    String licensePath;
    int choosePicType = 0;
    String mProvince = "";
    String mCity = "";
    String mBlock = "";
    String mAddress = "";
    /**
     * 默认经纬度
     */
    String mLongitude = "113.913761";
    String mLatitude = "22.572242";
    @BindView(R.id.tv_license)
    TextView mTvLicense;
    @BindView(R.id.iv_license)
    GlideImageView mIvLicense;
    @BindView(R.id.tv_store_license)
    TextView mTvStoreLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_merchants_settled);
        ButterKnife.bind(this);
        mTvTitle.setText("申请入驻");
        getStoreClass();
    }

    @OnClick(R.id.iv_back)
    public void onMIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_class)
    public void onMTvClassClicked() {
        showStoreClass();
    }

    @OnClick(R.id.tv_city)
    public void onMTvCityClicked() {
        startActivityForResult(new Intent(JoinInActivity.this, GaodeMapActivity.class).putExtra("title", "选择店铺地址"), 888);
    }

    @OnClick(R.id.iv_store_bg)
    public void onMIvStoreBgClicked() {
        choosePicType = 0;
        showSelectPicPage();
    }

    @OnClick(R.id.tv_store_bg)
    public void onMTvStoreBgClicked() {
        choosePicType = 0;
        showSelectPicPage();
    }

    @OnClick(R.id.iv_logo)
    public void onMIvLogoClicked() {
        choosePicType = 1;
        showSelectPicPage();
    }

    @OnClick(R.id.tv_store_logo)
    public void onMTvStoreLogoClicked() {
        choosePicType = 1;
        showSelectPicPage();
    }

    public void showStoreClass() {
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
                categoryId = item0.getId() + "";
            }
            mTvClass.setText(result);
            return false;
        });
        dialog.setItems(initItems(), null, null, null, null);
        dialog.setSelected(0, 0, 0, 0, 0);
        return dialog;
    }

    private CategoryWheelItem[] initItems() {
        final CategoryWheelItem[] items;
        try {
            List<JoinClassBean> classListBeanList = ApplyUtils.getApplyClassList();
            items = new CategoryWheelItem[classListBeanList.size()];
            for (int i = 0; i < classListBeanList.size(); i++) {
                items[i] = new CategoryWheelItem(classListBeanList.get(i).getName(), classListBeanList.get(i).getId());
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CategoryWheelItem[1];
    }

    public void getStoreClass() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(UserService.class).applyCat(new BasePostBean())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<JoinClassBean>>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<JoinClassBean>> t) throws Exception {
                ApplyUtils.saveApplyClass(t.getData());
            }
        });
    }


    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(JoinInActivity.this, ImageChooseUtil.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageChooseUtil.REQUEST_CODE:
                    List<Uri> stringList = Matisse.obtainResult(data);
                    startCrop(stringList.get(0));
                    break;
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;
                default:
                    mProvince = data.getStringExtra(PROVINCE);
                    mCity = data.getStringExtra(CITY);
                    mBlock = data.getStringExtra(BLOCK);
                    mTvCity.setText(mProvince + mCity + mBlock);
                    mAddress = data.getStringExtra(ADDRESS);
                    mEtAddress.setText(mAddress);
                    mLongitude = data.getStringExtra(LONGITUDE);
                    mLatitude = data.getStringExtra(LATITUDE);
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
            switch (choosePicType) {
                case 0:
                    mIvStoreBg.load(resultUri.getPath());
                    upLoadImage( resultUri.getPath());
                    break;
                case 1:
                    mIvLogo.load(resultUri.getPath());
                    upLoadImage(resultUri.getPath());
                    break;
                case 2:
                    mIvLicense.load(resultUri.getPath());
                    upLoadImage( resultUri.getPath());
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(this, "剪切失败，请重新选择", Toast.LENGTH_SHORT).show();
        }
    }

    public void upLoadImage(String cardPath) {
        setLoadingMessageIndicator(true);
        ImagePostBean imagePostBean = new ImagePostBean();
        byte[] byteArray = ImagePressUtils.getCompressedbyteArray(cardPath);
        try {
            imagePostBean.setImage(encode(byteArray));
            imagePostBean.setSize(byteArray.length + "");
            imagePostBean.setExt("jpg");
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
                switch (choosePicType) {
                    case 0:
                        bgPath = t.getData().getUrl();
                        break;
                    case 1:
                        logoPath = t.getData().getUrl();
                        break;
                    case 2:
                        licensePath = t.getData().getUrl();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @OnClick(R.id.tv_post)
    public void onMTvPostClicked() {
        if (checkContent()) {
            setLoadingMessageIndicator(true);
            JoinPostBean joinPostBean = new JoinPostBean();
            joinPostBean.setRealname(mEtContactName.getText().toString());
            joinPostBean.setTel(mEtContactPhone.getText().toString());
            joinPostBean.setUsername(mEtContactPhone.getText().toString());
            String phoneNumber = mEtContactPhone.getText().toString();
            joinPostBean.setPassword(phoneNumber.substring(phoneNumber.length() - 6) + "");
            joinPostBean.setName(mEtShopName.getText().toString());
            joinPostBean.setMch_common_cat_id(categoryId + "");
            joinPostBean.setProvince(mProvince);
            joinPostBean.setCity(mCity);
            joinPostBean.setDistrict(mBlock);
            joinPostBean.setAddress(mAddress);
            joinPostBean.setService_tel(mEtServiceTel.getText().toString());
            joinPostBean.setLogo(logoPath);
            joinPostBean.setHeader_bg(bgPath);
            joinPostBean.setBusiness_license(licensePath);
            joinPostBean.setLongitude(mLongitude);
            joinPostBean.setLatitude(mLatitude);
            RetrofitAPIManager.create(UserService.class).mchApply(joinPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<JoinResultBean>(true) {

                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<JoinResultBean> t) throws Exception {
                    Intent intent = new Intent(JoinInActivity.this, ReviewActivity.class);
                    intent.putExtra("Apply_status", t.getData().getApply_result().getApply_status());
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(mEtContactName.getText().toString())) {
            ToastHelper.makeText("请输入联系人").show();
            return false;
        }
        if (StringUtils.isEmpty(mEtContactPhone.getText().toString()) || mEtContactPhone.getText().length() < 6) {
            ToastHelper.makeText("请输入合法的手机号").show();
            return false;
        }
        if (StringUtils.isEmpty(mEtShopName.getText().toString())) {
            ToastHelper.makeText("请输入店铺名").show();
            return false;
        }

        if ("0".equals(categoryId)) {
            ToastHelper.makeText("请输入选择店铺分类").show();
            return false;
        }

        if (StringUtils.isEmpty(mTvCity.getText().toString())) {
            ToastHelper.makeText("请选择店铺地址").show();
            return false;
        }

        if (StringUtils.isEmpty(mEtServiceTel.getText().toString())) {
            ToastHelper.makeText("请输入客服电话").show();
            return false;
        }

        if (StringUtils.isEmpty(logoPath)) {
            ToastHelper.makeText("请上传Logo").show();
            return false;
        }
        if (StringUtils.isEmpty(bgPath)) {
            ToastHelper.makeText("请上传店铺背景").show();
            return false;
        }
        if (StringUtils.isEmpty(licensePath)) {
            ToastHelper.makeText("请上传营业执照").show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.iv_license)
    public void onMIvLicenseClicked() {
        choosePicType = 2;
        showSelectPicPage();
    }

    @OnClick(R.id.tv_store_license)
    public void onMTvStoreLicenseClicked() {
        choosePicType = 2;
        showSelectPicPage();
    }

}
