package cn.ifhu.supplier.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import cn.ifhu.supplier.model.newbean.data.DepostListBean;

import static cn.ifhu.supplier.utils.Constants.DEPOSTLISTBEAN;

public class DepostListBeanLogic {

    public static void saveDepostListBean(DepostListBean dataBean) {
        Logger.d(dataBean);
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(DEPOSTLISTBEAN + MchInfoLogic.getMchId(), json);
        }
    }


    public static DepostListBean getDepostListBean() {
        String json = IrReference.getInstance().getString(DEPOSTLISTBEAN+ MchInfoLogic.getMchId(), "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            DepostListBean mchInfo = gson.fromJson(json, DepostListBean.class);
            return mchInfo;
        }
        return null;
    }

    public static Boolean isDepostReviewsEmpty() {
        if (getDepostListBean() == null){
            return true;
        }else {
            DepostListBean depostListBean = getDepostListBean();
            if (depostListBean.getReview() == null || depostListBean.getReview().isEmpty()){
                return true;
            }else {
                return false;
            }
        }
    }

    public static Boolean isDepostPassEmpty() {
        if (getDepostListBean() == null){
            return true;
        }else {
            DepostListBean depostListBean = getDepostListBean();
            if (depostListBean.getPass() == null || depostListBean.getPass().isEmpty()){
                return true;
            }else {
                return false;
            }
        }
    }
}
