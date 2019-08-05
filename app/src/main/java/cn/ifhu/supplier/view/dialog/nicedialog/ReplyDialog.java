package cn.ifhu.supplier.view.dialog.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import cn.ifhu.supplier.R;

public class ReplyDialog extends BaseNiceDialog {
    public ReplyDialog.ButtonOnclick buttonOnclick;
    public String title;
    public String ok;
    public String cancel;

    public static ReplyDialog newInstance() {
        Bundle bundle = new Bundle();
        ReplyDialog dialog = new ReplyDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static ReplyDialog newInstance(String title, String cancel, String ok) {
        Bundle bundle = new Bundle();
        ReplyDialog dialog = new ReplyDialog();
        bundle.putString("title",title);
        bundle.putString("cancel",cancel);
        bundle.putString("ok",ok);
        dialog.setArguments(bundle);
        return dialog;
    }
    public void setButtonOnclick(ReplyDialog.ButtonOnclick buttonOnclick) {
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
        cancel = bundle.getString("cancel");
        ok = bundle.getString("ok");

    }

    @Override
    public int intLayoutId() {
        return R.layout.reply_price_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

        holder.setText(R.id.title,title);
        holder.setText(R.id.cancel,cancel);
        holder.setText(R.id.ok,ok);

        holder.setOnClickListener(R.id.cancel, v -> dialog.dismiss());

        holder.setOnClickListener(R.id.ok, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                EditText content = holder.getView(R.id.content);
                buttonOnclick.ok(content.getText().toString());
            }
        });
    }


    public interface ButtonOnclick{
        void ok(String content);
    }
}
