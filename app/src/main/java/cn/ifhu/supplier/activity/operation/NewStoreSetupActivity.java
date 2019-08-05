package cn.ifhu.supplier.activity.operation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class NewStoreSetupActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.tv_store_state)
    TextView tvStoreState;
    @BindView(R.id.iv_logo)
    GlideImageView ivLogo;
    @BindView(R.id.tv_realname)
    TextView tvRealname;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.ll_store_phone)
    LinearLayout llStorePhone;
    @BindView(R.id.tv_service_tel)
    TextView tvServiceTel;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_header_bg)
    GlideImageView ivHeaderBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setup_new);
        ButterKnife.bind(this);
        tvTitle.setText("店铺设置");
    }





    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_address)
    public void onTvAddressClicked() {
    }
}
