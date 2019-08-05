package cn.ifhu.supplier.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

/**
 * @author fuhongliang
 */
public class ImageChooseUtil {


    public static final int REQUEST_CODE = 1001;
    public static final int REQUEST_CODE2 = 1002;

    public static void startChooseImage(final Activity context, int request_code) {
        AndPermission.with(context)
                .permission(Permission.CAMERA,Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Matisse.from(context)
                                .choose(MimeType.ofImage())
                                .countable(true)
                                .capture(true)
                                .captureStrategy(new CaptureStrategy(true, context.getPackageName() + ".fileprovider"))
                                .maxSelectable(1)
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f)
                                .imageEngine(new ImageGildeEngine())
                                .forResult(request_code);
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {

            }
        }).start();
    }

//    public static void uploadBusinessCard(String path, BaseObserver<FileModel> baseObserver) {
//        File file = new File(path);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        RetrofitAPIManager.getLinkUploadClientApi().create(FileUploadService.class)
//                .uploadBusinessCard(body).compose(SchedulerUtils.ioMainScheduler())
//                .subscribe(baseObserver);
//    }

}
