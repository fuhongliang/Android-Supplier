package cn.ifhu.supplier.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import cn.ifhu.supplier.model.bean.OrderBean;

import static cn.ifhu.supplier.utils.Constants.ORDERPRINTING;

/**
 * @author fuhongliang
 */
public class OrderLogic {

    public static void savePrintingOrder(OrderBean orderBean) {
        if (orderBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(orderBean);
            IrReference.getInstance().saveString(ORDERPRINTING, json);
        }
    }


    public static OrderBean getPrintingOrder() throws Exception {
        String json = IrReference.getInstance().getString(ORDERPRINTING, "");
        if (!TextUtils.isEmpty(json)) {
            OrderBean orderBean = GsonUtils.fromJson(json,OrderBean.class);
            return orderBean;
        }
        return null;
    }
}
