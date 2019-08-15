package cn.ifhu.supplier.activity.join;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.home.PhotoActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.newbean.data.DepostListBean;
import cn.ifhu.supplier.utils.DepostListBeanLogic;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * @author fuhongliang
 */
public class MyDepositActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.iv_status)
    ImageView mIvStatus;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.ll_amount)
    LinearLayout mLlAmount;
    @BindView(R.id.wv_agreement)
    WebView mWvAgreement;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    DepostListBean depostListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deposts);
        ButterKnife.bind(this);
        mTvTitle.setText("我的保证金");
        depostListBean = DepostListBeanLogic.getDepostListBean();
        initData();
        initWebView();
        mWvAgreement.loadUrl("file:///android_asset/" + "depost_agreement.html");
        mTvRightText.setText("追加");
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

    public void initData() {
        if (depostListBean.getReview() != null && depostListBean.getReview().size() > 0) {
            mIvStatus.setSelected(true);
            mTvStatus.setSelected(true);
            mTvStatus.setText("保证金审核中");
            showDepositImages(true);
        } else {
            mIvStatus.setSelected(false);
            mTvStatus.setSelected(false);
            mTvStatus.setText("已缴纳保证金");
            showDepositImages(false);
        }
    }

    public void showDepositImages(boolean isReViewing) {
        mLlContent.removeAllViews();
//        if (isReViewing) {
            double amount = 0;

            ArrayList<String> list = new ArrayList<>();
            for (DepostListBean.ReviewBean reviewBean : depostListBean.getReview()) {
                list.add(reviewBean.getImage_url());
            }

            for (int i = 0; i < depostListBean.getReview().size(); i++) {
                DepostListBean.ReviewBean reviewBean = depostListBean.getReview().get(i);
                View view = LayoutInflater.from(this).inflate(R.layout.item_depost_image, null);
                GlideImageView mPic = view.findViewById(R.id.iv_photo);
                int finalI = i;
                mPic.setOnClickListener(v -> {
                    Intent intent = new Intent(MyDepositActivity.this, PhotoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("url", list);
                    bundle.putInt("index", finalI);
                    intent.putExtra("url_list", bundle);
                    startActivity(intent);
                });
                mPic.load(reviewBean.getImage_url());
                mLlContent.addView(view);
                amount = amount + Double.parseDouble(reviewBean.getPrice());

            }
            mTvAmount.setText("￥" + amount);
//        }else {
//            double amount = 0;
//            ArrayList<String> list = new ArrayList<>();
//            for (DepostListBean.PassBean passBean : depostListBean.getPass()) {
//                View view = LayoutInflater.from(this).inflate(R.layout.item_depost_image, null);
//                GlideImageView mPic = view.findViewById(R.id.iv_photo);
//                int finalI = i;
//                mPic.setOnClickListener(v -> {
//                    Intent intent = new Intent(MyDepositActivity.this, PhotoActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putStringArrayList("url", list);
//                    bundle.putInt("index", finalI);
//                    intent.putExtra("url_list", bundle);
//                    startActivity(intent);
//                });
//                mPic.load(passBean.getImage_url());
//                mLlContent.addView(view);
//                amount = amount + Double.parseDouble(passBean.getPrice());
//            }
//            mTvAmount.setText("￥" + amount);
//        }
    }

    @OnClick(R.id.iv_back)
    public void onMIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_right_text)
    public void onMTvRightTextClicked() {
        goToActivity(DepositPostActivity.class);
    }

}
