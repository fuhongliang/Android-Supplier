package cn.ifhu.supplier.view.dialog.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import cn.ifhu.supplier.R;

/**
 * @author fuhongliang
 */
public class ModifyPriceDialog extends BaseNiceDialog {
    public ButtonOnclick buttonOnclick;
    public String title;
    public String message;
    public String ok;

    public static ModifyPriceDialog newInstance() {
        Bundle bundle = new Bundle();
        ModifyPriceDialog dialog = new ModifyPriceDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static ModifyPriceDialog newInstance(String title, String message, String cancel, String ok) {
        Bundle bundle = new Bundle();
        ModifyPriceDialog dialog = new ModifyPriceDialog();
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
    }

    @Override
    public int intLayoutId() {
        return R.layout.modify_price_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
        holder.setOnClickListener(R.id.iv_close, v -> {
            dialog.dismiss();
        });

        holder.setOnClickListener(R.id.tv_add, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                EditText editTextGoodsPrice = holder.getView(R.id.et_goods_price);
                EditText editTextShippingFee = holder.getView(R.id.et_shipping_fee);
                buttonOnclick.addPrice(editTextGoodsPrice.getText().toString(),editTextShippingFee.getText().toString());
            }
        });
        holder.setOnClickListener(R.id.tv_discount, v -> {
            dialog.dismiss();
            if (buttonOnclick != null){
                EditText editTextGoodsPrice = holder.getView(R.id.et_goods_price);
                EditText editTextShippingFee = holder.getView(R.id.et_shipping_fee);
                buttonOnclick.discount(editTextGoodsPrice.getText().toString(),editTextShippingFee.getText().toString());
            }
        });
    }


    public interface ButtonOnclick{
        void addPrice(String goodsPrice,String shippingFee);
        void discount(String goodsPrice,String shippingFee);
    }
}