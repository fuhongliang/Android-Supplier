package cn.ifhu.supplier.net;


import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.DeliverGoodsDataBean;
import cn.ifhu.supplier.model.newbean.data.DeliverGoodsDetailDataBean;
import cn.ifhu.supplier.model.newbean.data.PickListDataBean;
import cn.ifhu.supplier.model.newbean.data.PickingDetailsDataBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.SetDeliverPostBean;
import cn.ifhu.supplier.model.newbean.post.SetPickingPostBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface DistributionService {


    /**
     * 新接口 获取商品拣货单
     *
     * @return 分类列表
     */
    @POST("pick_goods")
    public Observable<BaseEntity<PickListDataBean>> pickGoods(@Body BasePostBean basePostBean);
    /**
     * 新接口 获取商品拣货单详情
     *
     * @return 分类列表
     */
    @POST("pick_detail")
    public Observable<BaseEntity<PickingDetailsDataBean>> pickDetail(@Body SetPickingPostBean setPickingPostBean);
    /**
     * 新接口 设置拣货
     */
    @POST("set_pick")
    public Observable<BaseEntity<Object>> setPick(@Body SetPickingPostBean setPickingPostBean);

    /**
     * 新接口 获取配货单列表
     *
     * @return 分类列表
     */
    @POST("deliver_goods")
    public Observable<BaseEntity<DeliverGoodsDataBean>> deliverGoods(@Body BasePostBean basePostBean);


    /**
     * 新接口 获取配货单详情
     *
     * @return 分类列表
     */
    @POST("deliver_goods_detail")
    public Observable<BaseEntity<DeliverGoodsDetailDataBean>> deliverGoodsDetail(@Body SetDeliverPostBean setDeliverPostBean);
    /**
     * 新接口 设置发货
     */
    @POST("set_deliver")
    public Observable<BaseEntity<Object>> setDeliver(@Body SetDeliverPostBean setDeliverPostBean);


}
