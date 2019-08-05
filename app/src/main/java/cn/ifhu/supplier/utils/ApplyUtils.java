package cn.ifhu.supplier.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.ifhu.supplier.model.newbean.data.JoinClassBean;
import cn.ifhu.supplier.model.newbean.post.JoinPostBean;

import static cn.ifhu.supplier.utils.Constants.APPLYCLASSLIST;
import static cn.ifhu.supplier.utils.Constants.APPLYINFO;

/**
 * @author fuhongliang
 * 存储分类
 */
public class ApplyUtils {

    public static void saveApplyClass(List<JoinClassBean> dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(APPLYCLASSLIST, json);
        }
    }

    public static List<JoinClassBean> getApplyClassList() throws Exception {
        String json = IrReference.getInstance().getString(APPLYCLASSLIST, "");
        if (!TextUtils.isEmpty(json)) {
            ArrayList<JoinClassBean> classListBean = GsonUtils.fromJsonArrayToArrayList(json,JoinClassBean.class);
            return classListBean;
        }
        return null;
    }

    public static void saveJoinPostBean(JoinPostBean dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(APPLYINFO, json);
        }
    }

    public static JoinPostBean getJoinPostBean() throws Exception {
        String json = IrReference.getInstance().getString(APPLYINFO, "");
        if (!TextUtils.isEmpty(json)) {
            JoinPostBean joinPostBean = GsonUtils.fromJson(json,JoinPostBean.class);
            return joinPostBean;
        }
        return null;
    }

}
