package cn.ifhu.supplier.activity;

import android.os.Bundle;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.newbean.post.DepositPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.net.UserService;
import cn.ifhu.supplier.utils.MchInfoLogic;


public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_phone);

    }

    public void onMTvPostClicked() {
        if (checkContent()){
            setLoadingMessageIndicator(true);
            DepositPostBean depositPostBean = new DepositPostBean();
            depositPostBean.setMch_id(MchInfoLogic.getMchId());
            depositPostBean.setStore_id(MchInfoLogic.getStoreId()+"");
            depositPostBean.setImage_url("");
            depositPostBean.setAccess_token("");
            RetrofitAPIManager.create(UserService.class).mchDeposit(depositPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Object> t) throws Exception {

                }
            });
        }
    }

    public boolean checkContent(){
        return true;
    }
}
