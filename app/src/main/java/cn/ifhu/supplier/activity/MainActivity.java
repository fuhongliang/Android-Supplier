package cn.ifhu.supplier.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.login.LoginActivity;
import cn.ifhu.supplier.adapter.FragmentAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.base.ViewManager;
import cn.ifhu.supplier.fragments.distribution.DistributionFragment;
import cn.ifhu.supplier.fragments.goods.GoodsFragment;
import cn.ifhu.supplier.fragments.home.HomeFragment;
import cn.ifhu.supplier.fragments.orders.OrdersFragment;
import cn.ifhu.supplier.fragments.settings.SettingsFragment;
import cn.ifhu.supplier.model.bean.MessageEvent;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.jpush.android.api.JPushInterface;

import static cn.ifhu.supplier.utils.Constants.LOGOUT;
import static cn.ifhu.supplier.utils.Constants.UNNORMALORDER;
import static cn.ifhu.supplier.utils.Constants.UNPAYORDER;
import static cn.ifhu.supplier.utils.Constants.UNSHIPPINGORDER;
import static cn.ifhu.supplier.jpush.JpushUtil.SEQUENCE;


/**
 * @author fuhongliang
 */
public class MainActivity extends BaseActivity {

    private ViewPager mPager;
    private List<BaseFragment> mFragments;
    private FragmentAdapter mAdapter;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        resetToDefaultIcon();
        return setCurrentItemIcon(item);
    };

    public boolean setCurrentItemIcon(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.navigation_home) {
            item.setIcon(R.drawable.dibu_ic_sy_m);
            mPager.setCurrentItem(0,false);
            return true;
        } else if (i == R.id.navigation_order) {
            item.setIcon(R.drawable.dibu_ic_dd_m);
            mPager.setCurrentItem(1,false);
            return true;
        } else if (i == R.id.navigation_goods) {
            item.setIcon(R.drawable.dibu_ic_sp_m);
            mPager.setCurrentItem(2,false);
            ((GoodsFragment)mFragments.get(2)).getCatsData();
            return true;
//        }else if (i == R.id.navigation_settings) {
//            item.setIcon(R.drawable.dibu_ic_sz_m);
//            mPager.setCurrentItem(3,false);
//            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initViewPager();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        navigation.setItemIconTintList(null);
        navigation.setSelectedItemId(R.id.navigation_home);
        Resources resource = getBaseContext().getResources();
        ColorStateList csl = resource.getColorStateList(R.color.bottom_navigation_color);
        navigation.setItemTextColor(csl);
        //注册监听蓝牙
        registerReceiver(mReceiver, makeFilter());

        if (!MchInfoLogic.getMchTel().equals("") ) {
            Log.e("JIGUANG-JPush", "设置别名" + MchInfoLogic.getMchTel());
            JPushInterface.setAlias(this, SEQUENCE, MchInfoLogic.getMchTel());
        }

    }


    private void resetToDefaultIcon() {
        MenuItem home = navigation.getMenu().findItem(R.id.navigation_home);
        MenuItem orders = navigation.getMenu().findItem(R.id.navigation_order);
        MenuItem goods = navigation.getMenu().findItem(R.id.navigation_goods);
//        MenuItem settings = navigation.getMenu().findItem(R.id.navigation_settings);

        home.setIcon(R.drawable.dibu_ic_sy_x);
        orders.setIcon(R.drawable.dibu_ic_dd_x);
        goods.setIcon(R.drawable.dibu_ic_sp_x);
//        settings.setIcon(R.drawable.dibu_ic_sz_x);
    }

    private void initViewPager() {
        ViewManager.getInstance().addFragment(0, HomeFragment.newInstance());
        ViewManager.getInstance().addFragment(1, DistributionFragment.newInstance());
        ViewManager.getInstance().addFragment(2, GoodsFragment.newInstance());
//        ViewManager.getInstance().addFragment(3, SettingsFragment.newInstance());
        mFragments = ViewManager.getInstance().getAllFragment();
        mPager = findViewById(R.id.container_pager);
        mPager.setOffscreenPageLimit(3);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                resetToDefaultIcon();
                setCurrentItemIcon(navigation.getMenu().getItem(i));
                navigation.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void logout() {
        MchInfoLogic.loginOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(mReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case LOGOUT:
                logout();
            case UNSHIPPINGORDER:
            case UNNORMALORDER:
            case UNPAYORDER:
                resetToDefaultIcon();
                setCurrentItemIcon(navigation.getMenu().getItem(1));
                navigation.getMenu().getItem(1).setChecked(true);
                break;
            default:
        }
    }


    private IntentFilter makeFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return filter;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Log.e("TAG", "TURNING_ON");
                            break;
                        case BluetoothAdapter.STATE_ON:
//                            AudioUtil.getInstance(MainActivity.this).openRawMusicS(MainActivity.this,false,R.raw.ring);
                            Log.e("TAG", "STATE_ON");
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.e("TAG", "STATE_TURNING_OFF");
                            break;
                        case BluetoothAdapter.STATE_OFF:
//                            AudioUtil.getInstance(MainActivity.this).openRawMusicS(MainActivity.this,false,R.raw.ring);
                            Log.e("TAG", "STATE_OFF");
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
