package cn.ifhu.supplier.net;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.ShareListDataBean;
import cn.ifhu.supplier.model.newbean.data.WithdrawalsRecordDataBean;
import cn.ifhu.supplier.model.newbean.data.IncomeDetailDataBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationPostBean;
import cn.ifhu.supplier.model.newbean.post.ShareListPostBean;
import cn.ifhu.supplier.model.newbean.post.SharePostBean;
import cn.ifhu.supplier.model.newbean.post.WithdrawPostBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface HomeService {

    /**
     * 收入明细
     *
     * @param allEvaluationPostBean
     * @return
     */
    @POST("income_detail")
    public Observable<BaseEntity<IncomeDetailDataBean>> incomeDetail(@Body AllEvaluationPostBean allEvaluationPostBean);

    /**
     * 提现记录
     *
     * @param allEvaluationPostBean
     * @return
     */
    @POST("cash_out_record")
    public Observable<BaseEntity<WithdrawalsRecordDataBean>> cashOutRecord(@Body AllEvaluationPostBean allEvaluationPostBean);

    /**
     * 提现申请
     */
    @POST("apply_cash_out")
    public Observable<BaseEntity<Object>> applyCashOut(@Body WithdrawPostBean withdrawPostBean);
    /**
     * 团长列表
     */
    @POST("list_share")
    public Observable<BaseEntity<ShareListDataBean>> listShare(@Body ShareListPostBean shareListPostBean);

    /**
     * 删除团长
     */
    @POST("del_share")
    public Observable<BaseEntity<Object>> delShare(@Body SharePostBean sharePostBean);

    /**
     * 删除团长
     */
    @POST("add_share")
    public Observable<BaseEntity<Object>> addShare(@Body SharePostBean sharePostBean);

}
