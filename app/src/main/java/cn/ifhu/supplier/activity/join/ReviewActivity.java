package cn.ifhu.supplier.activity.join;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.login.LoginActivity;
import cn.ifhu.supplier.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class ReviewActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    int mApplyStatus = 0;
    @BindView(R.id.tv_tips)
    TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        mTvTitle.setText("申请状态");
        mApplyStatus = getIntent().getIntExtra("Apply_status", 0);
        showInfo();
    }

    public void showInfo() {
        switch (mApplyStatus) {
            case 0:
                mTvTips.setText(getResources().getString(R.string.apply_tips_going_on));
                break;
            case 1:
                mTvTips.setText(getResources().getString(R.string.apply_tips_success));
                break;
            case 2:
                mTvTips.setText(getResources().getString(R.string.apply_tips_fail));
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void onMIvBackClicked() {
        startActivity(new Intent(ReviewActivity.this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.btn_ok)
    public void onMBtnOkClicked() {
        startActivity(new Intent(ReviewActivity.this, LoginActivity.class));
        finish();
    }
}
