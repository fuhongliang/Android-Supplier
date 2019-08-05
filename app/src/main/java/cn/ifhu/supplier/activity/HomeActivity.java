package cn.ifhu.supplier.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;

public class HomeActivity  extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        ButterKnife.bind(this);
    }
}
