package cn.ifhu.supplier.activity.join;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class DepositAgreementActivity extends BaseActivity {
    public int PROGRESS_MAX_VALUE = 100;

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.wv_agreement)
    WebView mWvAgreement;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.ll_agreement)
    LinearLayout mLlAgreement;
    @BindView(R.id.tv_agree)
    TextView mTvAgree;
    @BindView(R.id.progress_web)
    ProgressBar mProgressWeb;
    @BindView(R.id.iv_agree)
    ImageView mIvAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depost_agreement);
        ButterKnife.bind(this);
        mTvTitle.setText("保证金协议声明");
        initWebView();
        mWvAgreement.loadUrl("file:///android_asset/" + "depost_agreement.html");
//        mWvAgreement.loadUrl("");
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        WebSettings setting = mWvAgreement.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setAllowFileAccess(true);
        setting.setAppCacheEnabled(true);
        setting.setSupportMultipleWindows(false);
        mWvAgreement.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("mailto:")) {
                    Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWvAgreement.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWvAgreement.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == PROGRESS_MAX_VALUE) {
                    mProgressWeb.setVisibility(View.GONE);
                } else {
                    mProgressWeb.setVisibility(View.VISIBLE);
                    mProgressWeb.setProgress(newProgress);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWvAgreement.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWvAgreement.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWvAgreement.destroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWvAgreement.stopLoading();
    }


    @OnClick(R.id.iv_back)
    public void onMIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_agree)
    public void onMTvAgreeClicked() {
        if (mIvAgree.isSelected()){
            goToActivity(DepositPostActivity.class);
            finish();
        }else {
            ToastHelper.makeText("请先勾选我已阅读并同意保证金协议").show();
        }
    }


    @OnClick(R.id.iv_agree)
    public void onMIvAgreeClicked() {
        mIvAgree.setSelected(!mIvAgree.isSelected());
    }
}
