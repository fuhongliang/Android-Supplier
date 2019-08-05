package cn.ifhu.supplier.net;

import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.WithdrawalsRecordDataBean;
import cn.ifhu.supplier.model.newbean.data.IncomeDetailDataBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationPostBean;
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

}
