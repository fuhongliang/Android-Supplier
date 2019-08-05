package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class WithdrawSuccessActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_save)
    Button btnSave;

    int count = 3;

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_successk);
        ButterKnife.bind(this);
        tvTitle.setText("提现详情");
        btn = (Button) findViewById(R.id.btn_save);


        handler.sendEmptyMessage(1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WithdrawSuccessActivity.this, FinanceActivity.class));

                handler.sendEmptyMessage(1);
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count--;

            if (count == 0) {
                startActivity(new Intent(WithdrawSuccessActivity.this, FinanceActivity.class));
            } else {
                handler.sendEmptyMessageDelayed(1, 1000);
                btnSave.setText("返回(" + count + ")");
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);

        if (handler != null) {
            handler = null;
        }
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        startActivity(new Intent(WithdrawSuccessActivity.this, FinanceActivity.class));
    }
}
