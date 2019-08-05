package cn.ifhu.supplier.view.dialog.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.utils.StringUtils;

/**
 * @author fuhongliang
 */
public class ConfirmDialog extends BaseNiceDialog {
    public ButtonOnclick buttonOnclick;
    public String title;
    public String message;
    public String cancel;
    public String ok;

    public static ConfirmDialog newInstance(String title, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static ConfirmDialog newInstance(String title, String message,String cancel,String ok) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("cancel", cancel);
        bundle.putString("ok", ok);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }
    public void setButtonOnclick(ButtonOnclick buttonOnclick) {
        this.buttonOnclick = buttonOnclick;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        title = bundle.getString("title");
        message = bundle.getString("message");
        cancel = bundle.getString("cancel");
        ok = bundle.getString("ok");
    }

    @Override
    public int intLayoutId() {
        return R.layout.confirm_layout;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
        holder.setText(R.id.title, title);
        holder.setText(R.id.message, message);
        if (!StringUtils.isEmpty(ok) && !StringUtils.isEmpty(cancel)){
            holder.setText(R.id.cancel, cancel);
            holder.setText(R.id.ok, ok);
        }
        holder.setOnClickListener(R.id.cancel, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                buttonOnclick.cancel();
            }
        });

        holder.setOnClickListener(R.id.ok, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                buttonOnclick.ok();
            }
        });
    }


    public interface ButtonOnclick{
        void cancel();
        void ok();
    }
}