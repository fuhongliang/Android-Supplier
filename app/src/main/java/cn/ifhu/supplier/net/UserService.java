package cn.ifhu.supplier.net;

import java.util.List;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.RegisterBean;
import cn.ifhu.supplier.model.bean.UserServiceBean;
import cn.ifhu.supplier.model.newbean.data.DepostListBean;
import cn.ifhu.supplier.model.newbean.data.JoinClassBean;
import cn.ifhu.supplier.model.newbean.data.JoinResultBean;
import cn.ifhu.supplier.model.newbean.data.MchBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.DepositPostBean;
import cn.ifhu.supplier.model.newbean.post.JoinPostBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface UserService {
    @POST("login")
    public Observable<BaseEntity<MchBean>> login(@Body UserServiceBean.LoginForm loginForm);


    @POST("apply_cat")
    public Observable<BaseEntity<List<JoinClassBean>>> applyCat(@Body BasePostBean basePostBean);


    @POST("mch_apply")
    public Observable<BaseEntity<JoinResultBean>> mchApply(@Body JoinPostBean joinPostBean);

    @POST("mch_deposit/create")
    public Observable<BaseEntity<DepostListBean>> mchDeposit(@Body DepositPostBean joinPostBean);




    /**
     * 获取短信验证码
     * 只需要设置username就行
     * @return
     */
    @POST("get_captcha_login")
    public Observable<BaseEntity> getCaptchaLogin(@Body UserServiceBean.LoginForm loginForm);

    @FormUrlEncoded
    @POST("check_mobile")
    public Observable<BaseEntity<Object>> checkMobile(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("member_register")
    public Observable<BaseEntity<RegisterBean>> memberRegister(@Field("mobile") String mobile, @Field("password") String password, @Field("verify_code") String verify_code, @Field("app_type") String app_type, @Field("device_tokens") String device_tokens);


}
