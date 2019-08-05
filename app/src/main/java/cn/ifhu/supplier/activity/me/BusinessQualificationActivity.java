package cn.ifhu.supplier.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.utils.UserLogic;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;
import cn.ifhu.supplier.view.GlideImageView.progress.CircleProgressView;

/**
 * @author fuhongliang
 */
public class BusinessQualificationActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_business_qualification)
    GlideImageView ivBusinessQualification;
    @BindView(R.id.progressView)
    CircleProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_qualification);
        ButterKnife.bind(this);
        tvTitle.setText("营业执照");
        loadBusinessqualification();
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    public void loadBusinessqualification() {
        ivBusinessQualification.load(UserLogic.getUser().getBusiness_licence_number_electronic(), R.drawable.photo_zhizhao, (isComplete, percentage, bytesRead, totalBytes) -> {
            if (isComplete) {
                progressView.setVisibility(View.GONE);
            } else {
                progressView.setVisibility(View.VISIBLE);
                progressView.setProgress(percentage);
            }
        });
    }
}
