package cn.ifhu.supplier.net;

import java.util.ArrayList;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.NewOrderBean;
import cn.ifhu.supplier.model.bean.OrderBean;
import cn.ifhu.supplier.model.newbean.data.AllEvaluationDataBean;
import cn.ifhu.supplier.model.newbean.data.OrderDetailBean;
import cn.ifhu.supplier.model.newbean.data.OrdersDataBean;
import cn.ifhu.supplier.model.newbean.data.RefundOrdersDataBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationDeletePostBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationPostBean;
import cn.ifhu.supplier.model.newbean.post.CommentReplyBean;
import cn.ifhu.supplier.model.newbean.post.ModifyPricePostBean;
import cn.ifhu.supplier.model.newbean.post.OrderDetailPostBean;
import cn.ifhu.supplier.model.newbean.post.OrdersPostBean;
import cn.ifhu.supplier.model.newbean.post.RefundHandlePostBean;
import cn.ifhu.supplier.model.newbean.post.RefundOrdersPostBean;
import cn.ifhu.supplier.model.newbean.post.ShippingPostBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface OrderService {

    /**
     * 获取订单详情
     */
    @POST("get_order_detail")
    public Observable<BaseEntity<OrderDetailBean>> getOrderDetail(@Body OrderDetailPostBean detailPostBean);

    @POST("list_order")
    public Observable<BaseEntity<OrdersDataBean>> getOrders(@Body OrdersPostBean ordersPostBean);

    @POST("list_refund_order")
    public Observable<BaseEntity<RefundOrdersDataBean>> getRefundOrders(@Body RefundOrdersPostBean refundOrdersPostBean);

    @POST("refund_handle")
    public Observable<BaseEntity<Object>> refundHandle(@Body RefundHandlePostBean refundHandlePostBean);

    @POST("modify_price")
    public Observable<BaseEntity<Object>> modifyOrderPrice(@Body ModifyPricePostBean modifyPricePostBean);

    @POST("delivery_goods")
    public Observable<BaseEntity<Object>> deliveryGoods(@Body ShippingPostBean shippingPostBean);

    @FormUrlEncoded
    @POST("receive_order")
    public Observable<BaseEntity<Object>> receiveOrder(@Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("order_list")
    public Observable<BaseEntity<ArrayList<OrderBean>>> getOrder(@Field("order_state") int order_state, @Field("store_id") int store_id, @Field("page") int page);

    @FormUrlEncoded
    @POST("pending_order_list")
    public Observable<BaseEntity<NewOrderBean>> getPendingOrderList(@Field("store_id") int store_id);

    @POST("comment_list")
    public Observable<BaseEntity<AllEvaluationDataBean>> getCommentList(@Body AllEvaluationPostBean allEvaluationPostBean);

    @POST("comment_hod")
    public Observable<BaseEntity<Object>> commentHod(@Body AllEvaluationDeletePostBean allEvaluationDeletePostBean);

    @POST("comment_reply")
    public Observable<BaseEntity<Object>> commentReply(@Body CommentReplyBean commentReplyBean);

}
