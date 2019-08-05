package cn.ifhu.supplier.view.dialog.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import cn.ifhu.supplier.R;

/**
 * @author fuhongliang
 */
public class BuyDiscountDialog extends BaseNiceDialog {
    public ButtonOnclick buttonOnclick;
    public String title;
    public String message;
    public String ok;

    public static BuyDiscountDialog newInstance() {
        BuyDiscountDialog dialog = new BuyDiscountDialog();
        return dialog;
    }

    public void setButtonOnclick(ButtonOnclick buttonOnclick) {
        this.buttonOnclick = buttonOnclick;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int intLayoutId() {
        return R.layout.buy_discount_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

        holder.setOnClickListener(R.id.ok, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                EditText editText = holder.getView(R.id.dialog_edit);
                buttonOnclick.ok(editText.getText().toString());
            }
        });

        holder.setOnClickListener(R.id.tv_cancel, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                buttonOnclick.cancel();
            }
        });
    }


    public interface ButtonOnclick{
        void ok(String amount);
        void cancel();
    }
}