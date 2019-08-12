package cn.ifhu.supplier.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.PickingDetailsAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.view.ExpandListView;

public class PickingDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName;
    @BindView(R.id.tv_customer_phone)
    TextView tvCustomerPhone;
    @BindView(R.id.tv_customer_address)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_call_customer)
    TextView tvCallCustomer;
    @BindView(R.id.iv_call_customer)
    ImageView ivCallCustomer;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_total_pay_price)
    TextView tvTotalPayPrice;
    @BindView(R.id.ll_shipment_details)
    LinearLayout llShipmentDetails;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @BindView(R.id.listView)
    ExpandListView listView;
    PickingDetailsAdapter newpickingDetailsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_details);
        ButterKnife.bind(this);
        tvTitle.setText("拣货单详情");
        setDatas();
        listView.setAdapter(newpickingDetailsAdapter);
    }

    public void setDatas() {
        Intent intent = getIntent();
        tvOrderSn.setText(intent.getIntExtra("Huodan_id", 0) + "");
        tvCreateTime.setText(intent.getStringExtra("Create_time"));
        tvGoodsNum.setText(intent.getIntExtra("Goods_num", 0) + "");
        tvGoodsCount.setText(intent.getIntExtra("Goods_Count", 0) + "");
        tvTotalPayPrice.setText(intent.getStringExtra("Total_pay_price"));
        tvOrderState.setText(intent.getIntExtra("pick_status", 0) + "");
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
    }
}
