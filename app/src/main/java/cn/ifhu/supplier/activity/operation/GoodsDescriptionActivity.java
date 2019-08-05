package cn.ifhu.supplier.activity.operation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.sendtion.xrichtext.RichTextEditor;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.FileBean;
import cn.ifhu.supplier.model.newbean.post.ImagePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UploadFileService;
import cn.ifhu.supplier.utils.GlideSimpleLoader;
import cn.ifhu.supplier.utils.HtmlStringUtils;
import cn.ifhu.supplier.utils.ImageChooseUtil;
import cn.ifhu.supplier.utils.ImagePressUtils;
import cn.ifhu.supplier.utils.PostGoodsUtils;
import cn.ifhu.supplier.utils.StringUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static cn.ifhu.supplier.utils.Base64Utils.encode;

/**
 * @author KevinFu
 * @date 2019-07-16
 * Copyright (c) 2019 KevinFu
 */
public class GoodsDescriptionActivity extends BaseActivity {
    private RichTextEditor et_new_content;
    private String myContent;
    private ProgressDialog loadingDialog;
    private ProgressDialog insertDialog;
    private ImageWatcherHelper iwHelper;
    private Disposable subsLoading;
    private Disposable subsInsert;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        initView();
    }

    private void initView() {
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader());
        et_new_content = findViewById(R.id.et_new_content);
        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("商品描述");
        toolbar.setNavigationOnClickListener(v -> dealwithExit());
        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);
        openSoftKeyInput();//打开软键盘显示
        try {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage("数据加载中...");
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
            et_new_content.post(() -> dealWithContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dealWithContent() {
        et_new_content.clearAllLayout();
        showDataSync(PostGoodsUtils.getGoodsDescr());
        // 图片删除事件
        et_new_content.setOnRtImageDeleteListener(new RichTextEditor.OnRtImageDeleteListener() {

            @Override
            public void onRtImageDelete(String imagePath) {
                if (!TextUtils.isEmpty(imagePath)) {
//                    boolean isOK = SDCardUtil.deleteFile(imagePath);
//                    if (isOK) {
//                        showToast("删除成功：" + imagePath);
//                    }
                }
            }
        });

        // 图片点击事件
        et_new_content.setOnRtImageClickListener(new RichTextEditor.OnRtImageClickListener() {
            @Override
            public void onRtImageClick(View view, String imagePath) {
                try {
                    myContent = getEditData();
                    if (!TextUtils.isEmpty(myContent)) {
                        List<String> imageList = HtmlStringUtils.getTextFromHtml(myContent, true);
                        if (!TextUtils.isEmpty(imagePath)) {
                            int currentPosition = imageList.indexOf(imagePath);

                            List<Uri> dataList = new ArrayList<>();
                            for (int i = 0; i < imageList.size(); i++) {
                                dataList.add(getUriFromPath(imageList.get(i)));
                            }
                            iwHelper.show(dataList, currentPosition);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Uri getUriFromPath(String imagePath) {
        Uri uri = null;
        if (!TextUtils.isEmpty(imagePath)){
            if (imagePath.startsWith("http")){
                uri = Uri.parse(imagePath);
            } else {
                File file = new File(imagePath);
                if(file.exists()) {
                    uri = Uri.fromFile(file);
                }
            }
        }
        return uri;
    }

    /**
     * 异步方式显示数据
     * @param html
     */
    private void showDataSync(final String html){
        Observable.create((ObservableOnSubscribe<String>) emitter -> showEditData(emitter, html))
                //.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {
                        if (loadingDialog != null){
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (loadingDialog != null){
                            loadingDialog.dismiss();
                        }
                        showToast("解析错误：图片不存在或已损坏");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String text) {
                        try {
                            if (et_new_content !=null) {
                                if (text.contains("<img") && text.contains("src=")) {
                                    //imagePath可能是本地路径，也可能是网络地址
                                    String imagePath = HtmlStringUtils.getImgSrc(text);
                                    et_new_content.addImageViewAtIndex(et_new_content.getLastIndex(), imagePath);
                                } else {
                                    et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), text);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 显示数据
     */
    protected void showEditData(ObservableEmitter<String> emitter, String html) {
        try {
            List<String> textList = HtmlStringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                emitter.onNext(text);
            }
            emitter.onComplete();
        } catch (Exception e) {
            e.printStackTrace();
            emitter.onError(e);
        }
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        StringBuilder content = new StringBuilder();
        try {
            List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
            for (RichTextEditor.EditData itemData : editList) {
                if (itemData.inputStr != null) {
                    content.append(itemData.inputStr);
                } else if (itemData.imagePath != null) {
                    content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * 保存数据
     */
    private void saveData(){
        String mContent = getEditData();
        PostGoodsUtils.saveGoodsDescr(mContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_image:
                closeSoftKeyInput();//关闭软键盘
                callGallery();
                break;
            case R.id.action_new_save:
                try {
                    saveData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 调用图库选择
     */
    private void callGallery() {
        ImageChooseUtil.startChooseImage(GoodsDescriptionActivity.this, ImageChooseUtil.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
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

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            upLoadImage(resultUri.getPath());
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

    public void upLoadImage(String cardPath) {
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
                //异步方式插入图片
                insertImagesSync(t.getData().getUrl());
            }
        });
    }


    /**
     * 异步方式插入图片
     */
    private void insertImagesSync(String img_path) {
        if (!StringUtils.isEmpty(img_path)){
            insertDialog.show();
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                try {
                    et_new_content.measure(0, 0);
                    emitter.onNext(img_path);
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            })
                    .subscribeOn(Schedulers.io())//生产事件在io
                    .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onComplete() {
                            if (insertDialog != null && insertDialog.isShowing()) {
                                insertDialog.dismiss();
                            }
                            showToast("图片插入成功");
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (insertDialog != null && insertDialog.isShowing()) {
                                insertDialog.dismiss();
                            }
                            showToast("图片插入失败:" + e.getMessage());
                        }

                        @Override
                        public void onSubscribe(Disposable d) {
                            subsInsert = d;
                        }

                        @Override
                        public void onNext(String imagePath) {
                            et_new_content.insertImage(imagePath, et_new_content.getMeasuredWidth());
                        }
                    });
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (subsLoading != null && subsLoading.isDisposed()){
                subsLoading.dispose();
            }
            if (subsInsert != null && subsInsert.isDisposed()){
                subsInsert.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 退出处理
     */
    private void dealwithExit() {
        try {
            String noteContent = getEditData();
            if (noteContent.length() > 0) {
                saveData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!iwHelper.handleBackPressed()) {
            super.onBackPressed();
        }
        dealwithExit();
    }

    /**
     * 显示吐司
     **/
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭软键盘
     */
    private void closeSoftKeyInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive() && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开软键盘
     */
    private void openSoftKeyInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && !imm.isActive() && et_new_content != null) {
            et_new_content.requestFocus();
            imm.showSoftInputFromInputMethod(et_new_content.getWindowToken(),
                    InputMethodManager.SHOW_FORCED);
        }
    }

}
