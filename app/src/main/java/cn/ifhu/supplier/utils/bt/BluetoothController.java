package cn.ifhu.supplier.utils.bt;

import android.bluetooth.BluetoothAdapter;
import android.text.TextUtils;

import cn.ifhu.supplier.activity.me.SearchBluetoothActivity;
import cn.ifhu.supplier.utils.print.PrintUtil;

/**
 * @author fuhongliang
 */
public class BluetoothController {

    public static void init(SearchBluetoothActivity activity) {
        if (null == activity.bluetoothAdapter) {
            activity.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (null == activity.bluetoothAdapter) {
//            activity.tv_title.setText("该设备没有蓝牙模块");
            return;
        }

        if (!activity.bluetoothAdapter.isEnabled()) {
            //没有在开启中也没有打开
//            if ( activity.mAdapter.getState()!=BluetoothAdapter.STATE_TURNING_ON  && activity.mAdapter.getState()!=BluetoothAdapter.STATE_ON ){
            if (activity.bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {//蓝牙被关闭时强制打开
                activity.bluetoothAdapter.enable();
//                activity.ivWkq.setBackgroundResource(R.drawable.dyj_ic_ykq);
            } else {
//                activity.tv_title.setText("蓝牙未打开");
//                activity.ivWkq.setBackgroundResource(R.drawable.dyj_ic_wkq);
                return;
            }
        }
        String address = PrintUtil.getDefaultBluethoothDeviceAddress(activity.getApplicationContext());
        if (TextUtils.isEmpty(address)) {
//            activity.tv_title.setText("正在搜索打印机");
//            activity.tv_tips2.setText("新设备");
            return;
        }
        String name = PrintUtil.getDefaultBluetoothDeviceName(activity.getApplicationContext());
//        activity.tv_title.setText(name);
//        activity.tv_tips1.setText("已连接");
//        activity.tv_tips1.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
//        activity.ivWkq.setBackgroundResource(R.drawable.dyj_ic_ykq);
    }

    public static boolean turnOnBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }
        return false;
    }
}
