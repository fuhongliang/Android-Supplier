package cn.ifhu.supplier.net;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.UserBean;
import cn.ifhu.supplier.model.newbean.post.GetModifyPwdSmsBean;
import cn.ifhu.supplier.model.newbean.post.ModifyPwdBean;
import cn.ifhu.supplier.model.newbean.post.ModifyStoreInfoBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface MeService {

    @FormUrlEncoded
    @POST("store_msg_feedback")
    public Observable<BaseEntity<Object>> storeMsgFeedback(@Field("store_id") int store_id,@Field("content") String content,@Field("type") int type);

    @FormUrlEncoded
    @POST("store_set_workstate")
    public Observable<BaseEntity<Object>> storeSetWorkstate(@Field("store_id") int store_id,@Field("store_state") int store_state);


    @FormUrlEncoded
    @POST("store_set_desc")
    public Observable<BaseEntity<Object>> storeSetDesc(@Field("store_id") int store_id,@Field("store_desc") String store_desc);

    @FormUrlEncoded
    @POST("store_set_worktime")
    public Observable<BaseEntity<Object>> storeSetWorktime(@Field("store_id") int store_id,@Field("work_start_time") String work_start_time,@Field("work_end_time") String work_end_time);

    @POST("modify_pwd")
    public Observable<BaseEntity<Object>> modifyPwd(@Body ModifyPwdBean modifyPwdBean);

    @FormUrlEncoded
    @POST("store_set_desc")
    public Observable<BaseEntity<Object>> storeSetSesc(@Field("store_id") String store_id,@Field("store_desc") String store_desc);

    @FormUrlEncoded
    @POST("change_avator")
    public Observable<BaseEntity<UserBean>> changeAvator(@Field("store_id") String store_id, @Field("avator") String avator);

    @FormUrlEncoded
    @POST("auto_receive_order")
    public Observable<BaseEntity<Object>> autoReceiveOrder(@Field("store_id") int store_id, @Field("is_open") int is_open);

    @FormUrlEncoded
    @POST("member_logout")
    public Observable<BaseEntity<Object>> memberLogout(@Field("store_id") int store_id);

    @POST("users/")
    public Observable<BaseEntity<Object>> register(@Body RequestBody testBean );

    @POST("get_captcha_modify_pwd")
    public Observable<BaseEntity> getCaptchaModifyPwd(@Body GetModifyPwdSmsBean getModifyPwdSmsBean);

    @POST("modify_store_info")
    public Observable<BaseEntity<Object>> modifyStoreInfo(@Body ModifyStoreInfoBean modifyStoreInfoBean);

}
