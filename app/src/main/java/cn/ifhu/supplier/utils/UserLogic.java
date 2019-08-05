package cn.ifhu.supplier.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import cn.ifhu.supplier.model.bean.UserBean;

import static cn.ifhu.supplier.utils.Constants.USER;

public class UserLogic {

    public static void saveUser(UserBean dataBean) {
        Logger.d(dataBean);
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(USER, json);
        }
    }


    public static UserBean getUser() {
        String json = IrReference.getInstance().getString(USER, "");
        Log.e("JIGUANG-JPush--","user String = "+json);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UserBean mUser = gson.fromJson(json, UserBean.class);
            return mUser;
        }
        return null;
    }

}
