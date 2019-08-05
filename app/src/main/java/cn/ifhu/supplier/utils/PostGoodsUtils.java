package cn.ifhu.supplier.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.ifhu.supplier.model.newbean.data.SystemClassBean;

import static cn.ifhu.supplier.utils.Constants.GOODSDECR;
import static cn.ifhu.supplier.utils.Constants.SYSTEMCLASSLIST;

/**
 * @author fuhongliang
 * 存储分类
 */
public class PostGoodsUtils {

    public static void saveSystemClass(List<SystemClassBean.PtCatsBean> dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(SYSTEMCLASSLIST, json);
        }
    }

    public static List<SystemClassBean.PtCatsBean> getSystemClassList() throws Exception {
        String json = IrReference.getInstance().getString(SYSTEMCLASSLIST, "");
        if (!TextUtils.isEmpty(json)) {
            ArrayList<SystemClassBean.PtCatsBean> classListBean = GsonUtils.fromJsonArrayToArrayList(json,SystemClassBean.PtCatsBean.class);
            return classListBean;
        }
        return null;
    }

    public static void saveGoodsDescr(String goodsDescr) {
        IrReference.getInstance().saveString(GOODSDECR, goodsDescr);
    }

    public static String getGoodsDescr() {
        String string = IrReference.getInstance().getString(GOODSDECR, "");
        return string;
    }
}
