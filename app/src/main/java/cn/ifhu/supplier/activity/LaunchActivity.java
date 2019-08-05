package cn.ifhu.supplier.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.login.LoginActivity;
import cn.ifhu.supplier.utils.AudioUtil;
import cn.ifhu.supplier.utils.Constants;
import cn.ifhu.supplier.utils.IrReference;
import cn.ifhu.supplier.utils.MchInfoLogic;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(() -> {
            if (MchInfoLogic.isLogin()) {
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
            } else {
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
            }
            LaunchActivity.this.finish();
        },1000);

        boolean isRingMost = IrReference.getInstance().getBoolean(Constants.RINGMOST, false);
        if (isRingMost){
            AudioUtil.getInstance(LaunchActivity.this).setMediaVolume(100);
        }
    }

}
