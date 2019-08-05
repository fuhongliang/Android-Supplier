package cn.ifhu.supplier.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * @author tony
 * @date 2018/8/11
 */
public class NormalTextDialog extends DialogFragment {

    String msg = "";
    String comformBtn = "";
    String cancelBtn = "";


    /**
     * 创建Dialog时调用
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("普通提示Dialog信息")
                .setPositiveButton("确定", (dialog, which) -> Toast.makeText(getActivity(), "点击了确定", Toast.LENGTH_SHORT).show())
                .setNegativeButton("取消", (dialog, which) -> Toast.makeText(getActivity(), "点击了取消", Toast.LENGTH_SHORT).show());
        return builder.create();
    }
}
