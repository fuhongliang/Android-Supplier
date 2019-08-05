package cn.ifhu.supplier.net;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.NoticeBean;
import cn.ifhu.supplier.model.bean.NoticeDetailBean;
import cn.ifhu.supplier.model.newbean.post.NoticePostBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface NoticeService {

    @POST("message_notice/orderNotice")
    public Observable<BaseEntity<NoticeBean>> getMsgList(@Body NoticePostBean noticePostBean);

    @FormUrlEncoded
    @POST("msg_info")
    public Observable<BaseEntity<NoticeDetailBean>> getMsgInfo(@Field("store_id") int store_id,@Field("sm_id") int sm_id);

}
