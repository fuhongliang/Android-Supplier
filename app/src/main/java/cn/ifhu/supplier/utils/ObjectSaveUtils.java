package cn.ifhu.supplier.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.ifhu.supplier.model.newbean.data.SystemClassBean;

import static cn.ifhu.supplier.utils.Constants.GOODSDECR;
import static cn.ifhu.supplier.utils.Constants.PICKDATABEAN;
import static cn.ifhu.supplier.utils.Constants.SYSTEMCLASSLIST;

/**
 * @author fuhongliang
 * 存储分类
 */
public class ObjectSaveUtils {

    public static void savePickBean(List<SystemClassBean.PtCatsBean> dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(PICKDATABEAN, json);
        }
    }

    public static List<SystemClassBean.PtCatsBean> getPickBeanList() throws Exception {
        String json = IrReference.getInstance().getString(PICKDATABEAN, "");
        if (!TextUtils.isEmpty(json)) {
            ArrayList<SystemClassBean.PtCatsBean> classListBean = GsonUtils.fromJsonArrayToArrayList(json,SystemClassBean.PtCatsBean.class);
            return classListBean;
        }
        return null;
    }

}
