package cn.ifhu.supplier.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.GaodeMapActivity;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.utils.StringUtils;

import static cn.ifhu.supplier.activity.GaodeMapActivity.ADDRESS;

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.swh_auto_print)
    Switch swhAutoPrint;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
    }

    public boolean checkair() {
        if (StringUtils.isEmpty(etName.getText().toString())) {
            return false;
        }
        if (StringUtils.isEmpty(etPhone.getText().toString())) {
            return false;
        }
        if (StringUtils.isEmpty(tvAddress.getText().toString())) {
            return false;
        }
        if (StringUtils.isEmpty(etAddress.getText().toString())) {
            return false;
        }
        return true;
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @OnClick(R.id.ok)
    public void onOkClicked() {
        if (checkair()) {

        }
    }

    @OnClick(R.id.rl_address)
    public void onRlAddressClicked() {
        startActivityForResult(new Intent(AddAddressActivity.this,GaodeMapActivity.class),888);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            tvAddress.setText(data.getStringExtra(ADDRESS));
        }
    }
}
