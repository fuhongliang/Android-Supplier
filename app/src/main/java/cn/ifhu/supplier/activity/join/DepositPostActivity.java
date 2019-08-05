package cn.ifhu.supplier.activity.join;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.ifhu.supplier.model.newbean.data.DepostListBean;
import cn.ifhu.supplier.model.newbean.data.FileBean;
import cn.ifhu.supplier.model.newbean.post.DepositPostBean;
import cn.ifhu.supplier.model.newbean.post.ImagePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UploadFileService;
import cn.ifhu.supplier.net.UserService;
import cn.ifhu.supplier.utils.DepostListBeanLogic;
import cn.ifhu.supplier.utils.ImageChooseUtil;
import cn.ifhu.supplier.utils.ImagePressUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

import static cn.ifhu.supplier.utils.Base64Utils.encode;

/**
 * @author fuhongliang
 */
public class DepositPostActivity extends BaseActivity {

    String depostPath = "";
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.ll_choose)
    LinearLayout mLlChoose;
    @BindView(R.id.iv_depost_pic)
    GlideImageView mIvDepostPic;
    @BindView(R.id.submit)
    TextView mSubmit;
    @BindView(R.id.et_depost)
    EditText mEtDepost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_depost);
        ButterKnife.bind(this);
        mTvTitle.setText("提交保证金");
    }


    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(DepositPostActivity.this, ImageChooseUtil.REQUEST_CODE);
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
            mLlChoose.setVisibility(View.GONE);
            mIvDepostPic.setVisibility(View.VISIBLE);
            mIvDepostPic.load(resultUri.getPath());

            upLoadImage(resultUri.getPath());
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
                depostPath = t.getData().getUrl();
            }
        });
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(depostPath)) {
            ToastHelper.makeText("请上传保证金转账凭证").show();
            return false;
        }

        if (StringUtils.isEmpty(mEtDepost.getText().toString())) {
            ToastHelper.makeText("请输入保证金金额").show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.iv_back)
    public void onMIvBackClicked() {
        finish();
    }

    @OnClick(R.id.ll_choose)
    public void onMLlChooseClicked() {
        showSelectPicPage();
    }

    @OnClick(R.id.iv_depost_pic)
    public void onMIvDepostPicClicked() {
        showSelectPicPage();
    }

    @OnClick(R.id.submit)
    public void onMSubmitClicked() {
        if (checkContent()) {
            setLoadingMessageIndicator(true);
            DepositPostBean depositPostBean = new DepositPostBean();
            depositPostBean.setMch_id(MchInfoLogic.getMchId());
            depositPostBean.setStore_id(MchInfoLogic.getStoreId() + "");
            depositPostBean.setImage_url(depostPath);
            depositPostBean.setPrice(mEtDepost.getText().toString());
            depositPostBean.setAccess_token(MchInfoLogic.getMchAccessToken());
            RetrofitAPIManager.create(UserService.class).mchDeposit(depositPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<DepostListBean>(true) {

                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<DepostListBean> t) throws Exception {
                    ToastHelper.makeText("提交成功").show();
                    DepostListBeanLogic.saveDepostListBean(t.getData());
                    goToActivity(MyDepositActivity.class);
                    finish();
                }
            });
        }
    }
}
