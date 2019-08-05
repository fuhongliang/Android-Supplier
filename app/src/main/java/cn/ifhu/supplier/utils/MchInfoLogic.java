package cn.ifhu.supplier.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import cn.ifhu.supplier.MyApplication;
import cn.ifhu.supplier.model.newbean.data.MchBean;
import cn.jpush.android.api.JPushInterface;

import static cn.ifhu.supplier.jpush.JpushUtil.SEQUENCE;
import static cn.ifhu.supplier.utils.Constants.MEICHANTINFO;

public class MchInfoLogic {

    public static void saveMchInfoBean(MchBean dataBean) {
        Logger.d(dataBean);
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(MEICHANTINFO, json);
        }
    }


    public static MchBean getMchInfoBean() {
        String json = IrReference.getInstance().getString(MEICHANTINFO, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            MchBean mchInfo = gson.fromJson(json, MchBean.class);
            return mchInfo;
        }
        return null;
    }

    public static String getMchAccessToken() {
        String json = IrReference.getInstance().getString(MEICHANTINFO, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            MchBean mchInfo = gson.fromJson(json, MchBean.class);
            try {
                return mchInfo.getMch_info().getAccess_token();
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public static int getStoreId() {
        String json = IrReference.getInstance().getString(MEICHANTINFO, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            MchBean mchInfo = gson.fromJson(json, MchBean.class);
            try {
                return mchInfo.getMch_info().getStore_id();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }


    public static int getMchId() {
        String json = IrReference.getInstance().getString(MEICHANTINFO, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            MchBean mchInfo = gson.fromJson(json, MchBean.class);
            try {
                return mchInfo.getMch_info().getMch_id();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    public static String getMchTel() {
        String json = IrReference.getInstance().getString(MEICHANTINFO, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            MchBean mchInfo = gson.fromJson(json, MchBean.class);
            try {
                return mchInfo.getMch_info().getTel();
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public static String getMchRealname() {
        String json = IrReference.getInstance().getString(MEICHANTINFO, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            MchBean mchInfo = gson.fromJson(json, MchBean.class);
            try {
                return mchInfo.getMch_info().getRealname();
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }



    public static boolean isLogin(){
        if (getMchInfoBean() == null){
            return false;
        }else {
            return true;
        }
    }

    public static void loginOut() {
        // 极光 JPush 退出登录删除别名
        Log.e("JIGUANG-JPush", "退出登录"+SEQUENCE);
        JPushInterface.deleteAlias(MyApplication.getApplication(), SEQUENCE);
        IrReference.getInstance().clearSingle(MEICHANTINFO);
    }
}
