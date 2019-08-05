package cn.ifhu.supplier.activity.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.FileBean;
import cn.ifhu.supplier.model.newbean.data.StoreInfoBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.ImagePostBean;
import cn.ifhu.supplier.model.newbean.post.ModifyStoreInfoBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.MeService;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UploadFileService;
import cn.ifhu.supplier.utils.ImageChooseUtil;
import cn.ifhu.supplier.utils.ImagePressUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

import static cn.ifhu.supplier.utils.Base64Utils.encode;

/**
 * @author fuhongliang
 */
public class StoreSetUpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_cat_name)
    TextView tvCatName;
    @BindView(R.id.iv_logo)
    GlideImageView ivLogo;
    @BindView(R.id.rl_logo)
    RelativeLayout rlLogo;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_store_phone)
    TextView tvStorePhone;
    @BindView(R.id.tv_service_tel)
    TextView tvServiceTel;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_city)
    TextView tvCity;

    @BindView(R.id.iv_store_banner)
    GlideImageView ivStoreBanner;

    String cardPath;
    StoreInfoBean mStoreInfoBean;

    boolean isUpdateLogo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setup);
        ButterKnife.bind(this);
        tvTitle.setText("店铺设置");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStoreInfo();
    }

    public void getStoreInfo() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getStoreInfo(new BasePostBean())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<StoreInfoBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<StoreInfoBean> t) throws Exception {
                mStoreInfoBean = t.getData();
                initData();
            }

        });
    }

    public void initData() {
        tvStoreName.setText(mStoreInfoBean.getMch().getName());
        tvCatName.setText(mStoreInfoBean.getMch().getCat_name());
        ivLogo.loadCircle(mStoreInfoBean.getMch().getLogo());
        tvContact.setText(mStoreInfoBean.getMch().getRealname());
        tvStorePhone.setText(mStoreInfoBean.getMch().getTel());
        tvServiceTel.setText(mStoreInfoBean.getMch().getService_tel());
        tvCity.setText(mStoreInfoBean.getMch().getRegion());
        tvAddress.setText(mStoreInfoBean.getMch().getAddress());
        ivStoreBanner.load(mStoreInfoBean.getMch().getHeader_bg());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    //店铺名字
    @OnClick(R.id.tv_store_name)
    public void onLlStoreStateClicked() {
        Intent intent = new Intent(StoreSetUpActivity.this, ModifyStoreNamePhoneActivity.class);
        intent.putExtra(ModifyStoreNamePhoneActivity.TITLE, "店铺名字");
        intent.putExtra(ModifyStoreNamePhoneActivity.CONTENT, mStoreInfoBean.getMch().getName());
        intent.putExtra(ModifyStoreNamePhoneActivity.TYPE, 0);
        startActivity(intent);
    }

    @OnClick(R.id.tv_contact)
    public void onMTvContactClicked() {
        Intent intent = new Intent(StoreSetUpActivity.this, ModifyStoreNamePhoneActivity.class);
        intent.putExtra(ModifyStoreNamePhoneActivity.TITLE, "联系人");
        intent.putExtra(ModifyStoreNamePhoneActivity.CONTENT, mStoreInfoBean.getMch().getRealname());
        intent.putExtra(ModifyStoreNamePhoneActivity.TYPE, 1);
        startActivity(intent);
    }

    @OnClick(R.id.tv_store_phone)
    public void onMTvStorePhoneClicked() {
        Intent intent = new Intent(StoreSetUpActivity.this, ModifyStoreNamePhoneActivity.class);
        intent.putExtra(ModifyStoreNamePhoneActivity.TITLE, "联系电话");
        intent.putExtra(ModifyStoreNamePhoneActivity.CONTENT, mStoreInfoBean.getMch().getTel());
        intent.putExtra(ModifyStoreNamePhoneActivity.TYPE, 2);
        startActivity(intent);
    }

    @OnClick(R.id.tv_service_tel)
    public void onMTvServiceTelClicked() {
        Intent intent = new Intent(StoreSetUpActivity.this, ModifyStoreNamePhoneActivity.class);
        intent.putExtra(ModifyStoreNamePhoneActivity.TITLE, "客服电话");
        intent.putExtra(ModifyStoreNamePhoneActivity.CONTENT, mStoreInfoBean.getMch().getService_tel());
        intent.putExtra(ModifyStoreNamePhoneActivity.TYPE, 3);
        startActivity(intent);
    }

    @OnClick(R.id.tv_address)
    public void onMTvAddressClicked() {
        Intent intent = new Intent(StoreSetUpActivity.this, ModifyStoreNamePhoneActivity.class);
        intent.putExtra(ModifyStoreNamePhoneActivity.TITLE, "详细地址");
        intent.putExtra(ModifyStoreNamePhoneActivity.CONTENT, mStoreInfoBean.getMch().getAddress());
        intent.putExtra(ModifyStoreNamePhoneActivity.TYPE, 4);
        startActivity(intent);
    }

    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(StoreSetUpActivity.this, ImageChooseUtil.REQUEST_CODE);
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
            cardPath = resultUri.getPath();
            ivLogo.load(cardPath);
            upLoadImage();
        } else {
            Toast.makeText(this, "剪切失败，请重新选择", Toast.LENGTH_SHORT).show();
        }
    }

    public void upLoadImage() {
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
                changeStoreInfo( t.getData().getUrl());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                setLoadingMessageIndicator(false);
            }
        });
    }

    public void changeStoreInfo(String url) {
        setLoadingMessageIndicator(true);
        ModifyStoreInfoBean modifyStoreInfoBean = new ModifyStoreInfoBean();

        if (isUpdateLogo) {
            modifyStoreInfoBean.setLogo(url);
        }else {
            modifyStoreInfoBean.setHeader_bg(url);
        }

        RetrofitAPIManager.create(MeService.class).modifyStoreInfo(modifyStoreInfoBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }

    @OnClick(R.id.rl_logo)
    public void onMRlLogoClicked() {
        isUpdateLogo = true;
        showSelectPicPage();
    }

    @OnClick(R.id.rl_bg)
    public void onMRlBgClicked() {
        isUpdateLogo = false;
        showSelectPicPage();
    }
}
