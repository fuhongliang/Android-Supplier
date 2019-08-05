package cn.ifhu.supplier.activity.me;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.WebViewActivity;
import cn.ifhu.supplier.utils.bt.BluetoothActivity;
import cn.ifhu.supplier.utils.bt.BtUtil;
import cn.ifhu.supplier.utils.bt.SearchBleAdapter;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.supplier.utils.print.PrintQueue;
import cn.ifhu.supplier.utils.print.PrintUtil;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * 蓝牙搜索界面
 *
 * @author liuguirong
 * @date 2017/8/3
 */

public class SearchBluetoothActivity extends BluetoothActivity implements AdapterView.OnItemClickListener {
    int PERMISSION_REQUEST_COARSE_LOCATION = 2;
    public BluetoothAdapter bluetoothAdapter;
    public SearchBleAdapter searchBleAdapter;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.ll_my_device)
    LinearLayout llMyDevice;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.lv_search_blt)
    ListView lvSearchBlt;
    @BindView(R.id.rl_research)
    RelativeLayout rlResearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_search_text)
    TextView tvSearchText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bluetooth);
        ButterKnife.bind(this);
        //初始化蓝牙适配器
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        searchBleAdapter = new SearchBleAdapter(SearchBluetoothActivity.this, null);
        lvSearchBlt.setAdapter(searchBleAdapter);
        lvSearchBlt.setOnItemClickListener(this);
        searchDeviceOrOpenBluetooth();
        //6.0以上的手机要地理位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        init();
    }

    @Override
    public void btStatusChanged(Intent intent) {
        //蓝牙被关闭时强制打开
        if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
            bluetoothAdapter.enable();
        }
        //蓝牙打开时搜索蓝牙
        if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
            searchDeviceOrOpenBluetooth();
        }
    }

    private String getPrinterName() {
        String dName = PrintUtil.getDefaultBluetoothDeviceName(this);
        if (TextUtils.isEmpty(dName)) {
            dName = "未知设备";
        }
        return dName;
    }

    private String getPrinterName(String dName) {
        if (TextUtils.isEmpty(dName)) {
            dName = "未知设备";
        }
        return dName;
    }

    /**
     * 开始搜索
     * search device
     */
    private void searchDeviceOrOpenBluetooth() {
        if (BtUtil.isOpen(bluetoothAdapter)) {
            BtUtil.searchDevices(bluetoothAdapter);
        }
    }

    /**
     * 关闭搜索
     * cancel search
     */
    @Override
    protected void onStop() {
        super.onStop();
        BtUtil.cancelDiscovery(bluetoothAdapter);
    }

    @Override
    public void btStartDiscovery(Intent intent) {
        progressBar.setVisibility(View.VISIBLE);
        ivSearch.setSelected(false);
        ivSearch.setClickable(false);
        tvSearchText.setClickable(false);
        tvSearchText.setSelected(false);
    }

    @Override
    public void btFinishDiscovery(Intent intent) {
        progressBar.setVisibility(View.INVISIBLE);
        ivSearch.setSelected(true);
        ivSearch.setClickable(true);
        tvSearchText.setClickable(true);
        tvSearchText.setSelected(true);
    }

    @Override
    public void btFoundDevice(Intent intent) {
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if (null != bluetoothAdapter && device != null) {
            searchBleAdapter.addDevices(device);
        }
    }

    @Override
    public void btBondStatusChange(Intent intent) {
        super.btBondStatusChange(intent);
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        switch (device.getBondState()) {
            case BluetoothDevice.BOND_BONDING://正在配对
                Log.d("BlueToothTestActivity", "正在配对......");
                break;
            case BluetoothDevice.BOND_BONDED://配对结束
                Log.d("BlueToothTestActivity", "完成配对");
                connectBlt(device);
                break;
            case BluetoothDevice.BOND_NONE://取消配对/未配对
                Log.d("BlueToothTestActivity", "取消配对");
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        BluetoothDevice bluetoothDevice = searchBleAdapter.getItem(position);

        if (null == bluetoothDevice) {
            return;
        }
        DialogUtils.showConfirmDialog("蓝牙配对请求", "是否与" + getPrinterName(bluetoothDevice.getName()) + "配对", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                try {
                    BtUtil.cancelDiscovery(bluetoothAdapter);
                    if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                        connectBlt(bluetoothDevice);
                    } else {
                        Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                        createBondMethod.invoke(bluetoothDevice);
                    }
                    PrintQueue.getQueue(getApplicationContext()).disconnect();
                    searchBleAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    PrintUtil.setDefaultBluetoothDeviceAddress(getApplicationContext(), "");
                    PrintUtil.setDefaultBluetoothDeviceName(getApplicationContext(), "");
                    ToastHelper.makeText("蓝牙绑定失败,请重试", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                }
            }
        });

    }

    /***
     * 配对成功连接蓝牙
     * @param bluetoothDevice
     */

    private void connectBlt(BluetoothDevice bluetoothDevice) {
        if (null != searchBleAdapter) {
            searchBleAdapter.setConnectedDeviceAddress(bluetoothDevice.getAddress());
        }
        searchBleAdapter.notifyDataSetChanged();
        PrintUtil.setDefaultBluetoothDeviceAddress(getApplicationContext(), bluetoothDevice.getAddress());
        PrintUtil.setDefaultBluetoothDeviceName(getApplicationContext(), bluetoothDevice.getName());
        init();
    }

    private void init() {
        if (!BtUtil.isOpen(bluetoothAdapter)) {
            if (!bluetoothAdapter.isEnabled()) {
                //蓝牙被关闭时强制打开
                if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                    bluetoothAdapter.enable();
                }
            }
        } else {
            if (!PrintUtil.isBondPrinter(this, bluetoothAdapter)) {
                //未绑定蓝牙打印机器
                llMyDevice.setVisibility(View.GONE);
            } else {
                //已绑定蓝牙设备
                llMyDevice.setVisibility(View.VISIBLE);
                tvName.setText(getPrinterName());
                tvState.setText("已连接");
            }
        }
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_research)
    public void onRlResearchClicked() {
        searchDeviceOrOpenBluetooth();
    }

    @OnClick(R.id.tv_help)
    public void onTvHelpClicked() {
        WebViewActivity.startLoadAssetsHtml(SearchBluetoothActivity.this, "bluetooth_help.html", "帮助");
    }

    @OnClick(R.id.iv_settings)
    public void onIvSettingsClicked() {
        startActivity(new Intent(SearchBluetoothActivity.this, PrintingSettingsActivity.class));
    }
}
