package cn.ifhu.supplier.net;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.FileBean;
import cn.ifhu.supplier.model.newbean.post.ImagePostBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author fuhongliang
 */
public interface UploadFileService {

    @Multipart
    @POST("upload_pic")
    public Observable<BaseEntity<FileBean>> imageUpload(@Part() MultipartBody.Part file );

    @POST("upload_pic")
    public Observable<BaseEntity<FileBean>> imageUpload(@Body ImagePostBean imagePostBean);

}
