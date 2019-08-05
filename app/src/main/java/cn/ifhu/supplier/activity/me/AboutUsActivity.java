package cn.ifhu.supplier.activity.me;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.WebViewActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.utils.DeviceUtil;

/**
 * @author fuhongliang
 */
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version_code)
    TextView mTvVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        tvTitle.setText("关于平台");
        mTvVersionCode.setText("V"+DeviceUtil.getAppVersion(AboutUsActivity.this));
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_protocol)
    public void onTvProtocolClicked() {
        WebViewActivity.startLoadAssetsHtml(AboutUsActivity.this, "protocol.html", "服务协议");

    }
}
