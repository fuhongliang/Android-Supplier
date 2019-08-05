package cn.ifhu.supplier.net;

import java.util.List;

import cn.ifhu.supplier.model.bean.AccoutInformationBean;
import cn.ifhu.supplier.model.bean.AddGoodsBean;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.DiscountBean;
import cn.ifhu.supplier.model.bean.DiscountInfoBean;
import cn.ifhu.supplier.model.bean.DiscountPackageBean;
import cn.ifhu.supplier.model.bean.DiscountPackageInfoBean;
import cn.ifhu.supplier.model.bean.DiscountPackagePostBean;
import cn.ifhu.supplier.model.bean.DiscountPostBean;
import cn.ifhu.supplier.model.bean.FinanceBean;
import cn.ifhu.supplier.model.bean.FullCutBean;
import cn.ifhu.supplier.model.bean.FullCutPostBean;
import cn.ifhu.supplier.model.bean.JSBean;
import cn.ifhu.supplier.model.newbean.data.DepostListBean;
import cn.ifhu.supplier.model.newbean.data.HomeDataBean;
import cn.ifhu.supplier.model.newbean.data.ProductDetailBean;
import cn.ifhu.supplier.model.newbean.data.StoreInfoBean;
import cn.ifhu.supplier.model.newbean.data.SystemClassBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.ModifyGoodsCat;
import cn.ifhu.supplier.model.newbean.post.ProductAdditionBean;
import cn.ifhu.supplier.model.newbean.post.GoodsCatsAddBean;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.model.newbean.data.ProductListBean;
import cn.ifhu.supplier.model.bean.ProductManageBean;
import cn.ifhu.supplier.model.bean.ReleaseBankBean;
import cn.ifhu.supplier.model.bean.ReviewBean;
import cn.ifhu.supplier.model.bean.ValueBean;
import cn.ifhu.supplier.model.bean.ValuePostBean;
import cn.ifhu.supplier.model.bean.VouCherBean;
import cn.ifhu.supplier.model.bean.VouCherInfoBean;
import cn.ifhu.supplier.model.bean.WithDrawBean;
import cn.ifhu.supplier.model.newbean.post.GoodsListPostBean;
import cn.ifhu.supplier.model.newbean.post.GoodsShelvesBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface OperationService {

    /**
     * 新接口 获取商品列表
     *
     * @return
     */
    @POST("list_goods")
    public Observable<BaseEntity<ProductListBean>> goodsList(@Body GoodsListPostBean goodsListPostBean);


    /**
     * 新接口 获取分类列表
     *
     * @return 分类列表
     */
    @POST("list_cats")
    public Observable<BaseEntity<ProductCatsBean>> catsList(@Body BasePostBean basePostBean);

    /**
     * 获取平台分类
     *
     * @return 分类列表
     */
    @POST("mch_pt_cats")
    public Observable<BaseEntity<SystemClassBean>> getSystemClass(@Body BasePostBean basePostBean);



//    @FormUrlEncoded
//    @POST("add_goods_class")
//    public Observable<BaseEntity<List<ProductManageBean.ClassListBean>>> addGoodsClass(@Field("store_id") int store_id, @Field("class_name") String class_name);

    /**
     * 新接口 添加分类
     * <p>
     * /**
     * 新接口 添加分类
     *
     * @return
     */
    @POST("add_goods_cat")
    public Observable<BaseEntity<Object>> addGoodsCat(@Body GoodsCatsAddBean json);

    /**
     * 商品上架、下架
     *
     * @return
     */
    @POST("modify_goods_status")
    Observable<BaseEntity<Object>> goodsShelves(@Body GoodsShelvesBean goodsShelvesBean);

    /**
     * 编辑商品详情
     *
     * @param goodsShelvesBean 请求bean
     * @return
     */
    @POST("goods_detail")
    public Observable<BaseEntity<ProductDetailBean>> getGoodsDetail(@Body GoodsShelvesBean goodsShelvesBean);

    @POST("delete_goods")
    Observable<BaseEntity<Object>> deleteGoods(@Body GoodsShelvesBean goodsShelvesBean);

    /**
     * 新增商品添加接口
     *
     * @return
     */
    @POST("add_goods")
    public Observable<BaseEntity<Object>> publishGoods(@Body ProductAdditionBean productAdditionBean);


    /**
     * 编辑商品添加接口
     *
     * @return
     */
    @POST("modify_goods")
    public Observable<BaseEntity<Object>> modifyGoods(@Body ProductAdditionBean productAdditionBean);

    /**
     * 修改商品分类信息
     *
     * @return
     */
    @POST("modify_goods_cat")
    public Observable<BaseEntity<Object>> modifyGoodsCat(@Body ModifyGoodsCat modifyGoodsCat);

    /**
     * 商品分类信息详情
     *
     * @return
     */
    @POST("goods_cat_detail")
    public Observable<BaseEntity<Object>> goodsCatDetail(@Body GoodsListPostBean goodsListPostBean);

    /**
     * 商品分类信息详情
     *
     * @return
     */
    @POST("delete_goods_cat")
    public Observable<BaseEntity<Object>> deleteCat(@Body GoodsListPostBean goodsListPostBean);


    @FormUrlEncoded
    @POST("add_goods_class")
    public Observable<BaseEntity<List<ProductManageBean.ClassListBean>>> updateGoodsClass(@Field("store_id") int store_id, @Field("class_id") int class_id, @Field("class_name") String class_name);

    @POST("add_goods")
    public Observable<BaseEntity<Object>> addGoods(@Body AddGoodsBean addGoodsBean);


//    @POST("edit_goods")
//    public Observable<BaseEntity<Object>> editGoods(@Body EditGoodsBean editGoodsBean);


    @FormUrlEncoded
    @POST("sort_goods_class")
    public Observable<BaseEntity<List<ProductManageBean.ClassListBean>>> sortGoodsClass(@Field("class_ids") String sortCategoryBean, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("get_store_com")
    public Observable<BaseEntity<ReviewBean>> getStoreReviews(@Field("store_id") int store_id);


    @FormUrlEncoded
    @POST("get_store_com")
    public Observable<BaseEntity<ReviewBean>> getNoReplyReviews(@Field("store_id") int store_id, @Field("no_com") int no_com);


    @FormUrlEncoded
    @POST("store_feedback")
    public Observable<BaseEntity<Object>> storeFeedback(@Field("store_id") int store_id, @Field("content") String content, @Field("parent_id") int parent_id);

    @POST("store_operate_data")
    public Observable<BaseEntity<HomeDataBean>> storeOperateData(@Body BasePostBean basePostBean);


    @POST("mch_deposit/depositlist")
    public Observable<BaseEntity<DepostListBean>> getDepositList(@Body BasePostBean basePostBean);





    @FormUrlEncoded
    @POST("del_goods_class")
    public Observable<BaseEntity<List<Object>>> delGoodsClass(@Field("class_id") int class_id, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("chgoods_state")
    public Observable<BaseEntity<Object>> chGoodsState(@Field("goods_id") int goods_id, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("del_goods")
    public Observable<BaseEntity<Object>> delGoods(@Field("goods_id") int goods_id, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("xianshi_list")
    public Observable<BaseEntity<List<DiscountBean>>> getDiscountList(@Field("store_id") int store_id);


    @POST("xianshi_edit")
    public Observable<BaseEntity<Object>> xianshiAddOrEdit(@Body DiscountPostBean discountPostBean);


    @FormUrlEncoded
    @POST("xianshi_del")
    public Observable<BaseEntity<Object>> delDiscount(@Field("xianshi_id") String xianshi_id, @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("xianshi_info")
    public Observable<BaseEntity<DiscountInfoBean>> getDiscountInfo(@Field("xianshi_id") String xianshi_id, @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("mamsong_list")
    public Observable<BaseEntity<List<FullCutBean>>> getFullCutList(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("mamsong_del")
    public Observable<BaseEntity<Object>> delFullCut(@Field("mansong_id") String xianshi_id, @Field("store_id") String store_id);


    @POST("mamsong_edit")
    public Observable<BaseEntity<Object>> mamsongEditOrAdd(@Body FullCutPostBean fullCutPostBean);

    @FormUrlEncoded
    @POST("bundling_list")
    public Observable<BaseEntity<List<DiscountPackageBean>>> getDiscountPackageList(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("bundling_info")
    public Observable<BaseEntity<DiscountPackageInfoBean>> getDiscountPackageinfo(@Field("bundling_id") String bundling_id, @Field("store_id") String store_id);

    @POST("bundling_edit")
    public Observable<BaseEntity<Object>> AddOrEditDiscountPackage(@Body DiscountPackagePostBean discountPackagePostBean);


    @FormUrlEncoded
    @POST("bundling_del")
    public Observable<BaseEntity<Object>> delDiscountPackage(@Field("bundling_id") String bundling_id, @Field("store_id") String store_id);

    @POST("mianzhi_list")
    public Observable<BaseEntity<List<ValueBean>>> getMianzhiList();


    @POST("voucher_edit")
    public Observable<BaseEntity<Object>> voucherEdit(@Body ValuePostBean valuePostBean);


    @FormUrlEncoded
    @POST("voucher_list")
    public Observable<BaseEntity<List<VouCherBean>>> getVoucherList(@Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("voucher_info")
    public Observable<BaseEntity<VouCherInfoBean>> getVoucherInfo(@Field("voucher_id") String voucher_id, @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("voucher_del")
    public Observable<BaseEntity<Object>> delVouCher(@Field("voucher_id") String voucher_id, @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("add_xianshi_quota")
    public Observable<BaseEntity<Object>> buyDiscount_quota(@Field("month") int month, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("add_mansong_quota")
    public Observable<BaseEntity<Object>> buy_mansong_quota(@Field("month") int month, @Field("store_id") int store_id);


    @FormUrlEncoded
    @POST("add_bundling_quota")
    public Observable<BaseEntity<Object>> buy_bundling_quota(@Field("month") int month, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("add_voucher_quota")
    public Observable<BaseEntity<Object>> buy_voucher_quota(@Field("month") int month, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("pd_cash_list")
    public Observable<BaseEntity<WithDrawBean>> pdCashList(@Field("keyword") String keyword, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("all_store_jiesuan")
    public Observable<BaseEntity<JSBean>> allStoreJiesuan(@Field("keyword") String keyword, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("xianshi_goods_list")
    public Observable<BaseEntity<ProductManageBean>> getXianshiGoodsList(@Field("store_id") int store_id, @Field("class_id") int class_id);

    @FormUrlEncoded
    @POST("add_bank_account")
    public Observable<BaseEntity<Object>> addBankAccount(@Field("store_id") String store_id, @Field("account_name") String account_name, @Field("account_number") String account_number, @Field("bank_name") String bank_name, @Field("bank_type") String bank_type);

    @FormUrlEncoded
    @POST("bank_account_list")
    public Observable<BaseEntity<ReleaseBankBean>> bankAccountList(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("del_bank_account")
    public Observable<BaseEntity<Object>> delBankAccount(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("store_jiesuan")
    public Observable<BaseEntity<FinanceBean>> storeJiesuan(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("bank_account_info")
    public Observable<BaseEntity<AccoutInformationBean>> bankAccountInfo(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("pd_cash_add")
    public Observable<BaseEntity<Object>> pdCashAdd(@Field("store_id") String store_id, @Field("money") String money);


    @POST("store_info")
    public Observable<BaseEntity<StoreInfoBean>> getStoreInfo(@Body BasePostBean basePostBean);


}
