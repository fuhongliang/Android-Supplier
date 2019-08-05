package cn.ifhu.supplier.utils;

import android.support.v4.app.FragmentManager;

import cn.ifhu.supplier.view.dialog.nicedialog.BuyDiscountDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmInputDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.DiscountInputDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ModifyPriceDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ReplyDialog;

public class DialogUtils {

    private DialogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showBuyDiscountDialog(FragmentManager manager, BuyDiscountDialog.ButtonOnclick buttonOnclick) {
        BuyDiscountDialog confirmDialog = BuyDiscountDialog.newInstance();
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showInputConfirmDialog(String title, String message, FragmentManager manager, ConfirmInputDialog.ButtonOnclick buttonOnclick) {
        ConfirmInputDialog confirmDialog = ConfirmInputDialog.newInstance(title, message);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showDiscountConfirmDialog(String title, String message, FragmentManager manager, DiscountInputDialog.ButtonOnclick buttonOnclick) {
        DiscountInputDialog confirmDialog = DiscountInputDialog.newInstance(title, message);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showModifyPriceDialog(FragmentManager manager, ModifyPriceDialog.ButtonOnclick buttonOnclick) {
        ModifyPriceDialog confirmDialog = ModifyPriceDialog.newInstance();
        confirmDialog.setMargin(28);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showConfirmDialog(String title, String message, FragmentManager manager, ConfirmDialog.ButtonOnclick buttonOnclick) {
        ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, message);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }

    public static void showConfirmDialog(String title, String message, String cancel, String ok, FragmentManager manager, ConfirmDialog.ButtonOnclick buttonOnclick) {
        ConfirmDialog confirmDialog = ConfirmDialog.newInstance(title, message,cancel,ok);
        confirmDialog.setMargin(48);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }


    public static void showReplyDialog(String title, String cancel, String ok,  FragmentManager manager, ReplyDialog.ButtonOnclick buttonOnclick) {
        ReplyDialog confirmDialog = ReplyDialog.newInstance(title,cancel,ok);
        confirmDialog.setMargin(28);
        confirmDialog.setOutCancel(false);
        confirmDialog.setButtonOnclick(buttonOnclick);
        confirmDialog.show(manager);
    }
}