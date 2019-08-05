package cn.ifhu.supplier.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
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
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.NoticeDetailBean;
import cn.ifhu.supplier.net.NoticeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.StringUtils;

/**
 * @author fuhongliang
 */
public class NoticeDetailActivity extends BaseActivity {

    @BindView(R.id.progress_web)
    ProgressBar mProgressWeb;
    @BindView(R.id.wv_view)
    WebView mWvView;
    boolean loadError = false;

    public static final int PROGRESS_MAX_VALUE = 100;
    public static final String URL = "url";
    public static final String FILE_NAME = "fileName";
    public static final String TITLE = "title";
    public static final String HTML_DATA = "html_data";
    public static final String ISNEEDTOKEN = "need_token";
    @BindView(R.id.ll_web_content)
    LinearLayout mLlWebContent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        showWebTitle("");
        initWebView();
        getNoticeDetail(getIntent().getIntExtra("smId", 0));
    }

    public void getNoticeDetail(int smId) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(NoticeService.class).getMsgInfo(MchInfoLogic.getMchId(), smId)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<NoticeDetailBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<NoticeDetailBean> t) throws Exception {
                loadData(t.getData().getSm_content());
                showWebTitle(t.getData().getSm_title());
                tvTime.setText(t.getData().getSm_addtime());
            }
        });

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        mWvView.setWebViewClient(new WebViewClient() {
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
                loadError = true;
            }
        });
        WebSettings setting = mWvView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setAllowFileAccess(true);
        setting.setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWvView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWvView.setWebChromeClient(new WebChromeClient() {
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

    public void showWebTitle(String title) {
        if (!StringUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    public void loadData(String noticeContent) {
        mWvView.loadData(noticeContent, "text/html; charset=UTF-8", null);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWvView.canGoBack()) {
                mWvView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
