package cn.ifhu.supplier.view.dialog;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import cn.ifhu.supplier.view.dialog.nicedialog.BaseNiceDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ViewHolder;

/**
 * TODO 待完成弹窗封装
 */
public class GeneralDialog extends BaseNiceDialog {

    public String title = "温馨提示";//标题
    public String cancel = "取消";//取消按钮
    public String ok = "确定";//确定按钮
    public int layoutId;//布局ID

    public static GeneralDialog newInstance(@IdRes int layoutId, String ok,String cancel){
        Bundle bundle = new Bundle();
        bundle.putString("ok", ok);
        bundle.putString("cancel", cancel);
        bundle.putInt("layoutId", layoutId);
        GeneralDialog dialog = new GeneralDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static GeneralDialog newInstance(@IdRes int layoutId,String title, String ok,String cancel){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("ok", ok);
        bundle.putString("cancel", cancel);
        bundle.putInt("layoutId", layoutId);
        GeneralDialog dialog = new GeneralDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        title = bundle.getString("title");
        cancel = bundle.getString("cancel");
        ok = bundle.getString("ok");
    }

    @Override
    public int intLayoutId() {
        return layoutId;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {

    }
}
