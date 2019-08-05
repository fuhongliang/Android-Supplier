package cn.ifhu.supplier.activity.me;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.BtService;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.WebViewActivity;
import cn.ifhu.supplier.base.AppInfo;
import cn.ifhu.supplier.utils.bt.BluetoothActivity;
import cn.ifhu.supplier.utils.print.PrintUtil;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class PrintingSettingsActivity extends BluetoothActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printing_settings);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.tv_help)
    public void onTvHelpClicked() {
        WebViewActivity.startLoadAssetsHtml(PrintingSettingsActivity.this, "bluetooth_help.html", "帮助");
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_printing_test)
    public void onTvPrintingTestClicked() {
        if (TextUtils.isEmpty(AppInfo.btAddress)) {
            ToastHelper.makeText("请连接蓝牙...", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            startActivity(new Intent(PrintingSettingsActivity.this, SearchBluetoothActivity.class));
        } else {
            if (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_OFF) {
                //蓝牙被关闭时强制打开
                BluetoothAdapter.getDefaultAdapter().enable();
                ToastHelper.makeText("蓝牙被关闭请打开...", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            } else {
                ToastHelper.makeText("打印测试...", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                Intent intent = new Intent(getApplicationContext(), BtService.class);
                intent.setAction(PrintUtil.ACTION_PRINT_TEST);
                startService(intent);
            }
        }
    }
}
