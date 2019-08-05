package cn.ifhu.supplier.view.dialog.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.utils.StringUtils;

/**
 * @author fuhongliang
 */
public class ConfirmInputDialog extends BaseNiceDialog {
    public ButtonOnclick buttonOnclick;
    public String title;
    public String message;
    public String ok;

    public static ConfirmInputDialog newInstance(String title, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        ConfirmInputDialog dialog = new ConfirmInputDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static ConfirmInputDialog newInstance(String title, String message, String cancel, String ok) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("ok", ok);
        ConfirmInputDialog dialog = new ConfirmInputDialog();
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
        ok = bundle.getString("ok");
    }

    @Override
    public int intLayoutId() {
        return R.layout.confirm_input_layout;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
        holder.setText(R.id.title, title);
        holder.setText(R.id.message, message);
        if (!StringUtils.isEmpty(ok)){
            holder.setText(R.id.ok, ok);
        }

        holder.setOnClickListener(R.id.ok, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                EditText editText = holder.getView(R.id.dialog_edit);
                buttonOnclick.ok(editText.getText().toString());
            }
        });
    }


    public interface ButtonOnclick{
        void ok(String discount_price);
    }
}