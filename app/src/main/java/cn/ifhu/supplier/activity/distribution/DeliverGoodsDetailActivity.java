package cn.ifhu.supplier.activity.distribution;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.DeliverDetailAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.DeliverGoodsDetailDataBean;
import cn.ifhu.supplier.model.newbean.post.SetDeliverPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.DistributionService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.ExpandListView;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

public class DeliverGoodsDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    @BindView(R.id.rl_customer)
    RelativeLayout rlCustomer;
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
    @BindView(R.id.listView)
    ExpandListView listView;
    @BindView(R.id.tv_save)
    TextView tvSave;

    DeliverDetailAdapter deliverDetailAdapter;
    List<DeliverGoodsDetailDataBean.GoodsListBean> mData = new ArrayList<>();

    int deliverId;
    @BindView(R.id.sl_view)
    ScrollView slView;
    @BindView(R.id.ll_package)
    LinearLayout llPackage;
    @BindView(R.id.placeholder)
    View placeholder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_details);
        ButterKnife.bind(this);
        tvTitle.setText("配货单详情");
        deliverId = getIntent().getIntExtra("deliver_id", 0);
        deliverDetailAdapter = new DeliverDetailAdapter(mData, this);
        listView.setAdapter(deliverDetailAdapter);
        slView.smoothScrollTo(0, 0);
        getDeliverGoodsDatail();
    }

    private void setView(int isSend) {
        if (isSend == 0) {
            tvOrderState.setText("待配送");
            tvOrderState.setTextColor(getResources().getColor(R.color.order_service_color));
            placeholder.setVisibility(View.VISIBLE);
            tvSave.setVisibility(View.VISIBLE);
        } else {
            tvOrderState.setText("已配送");
            tvOrderState.setTextColor(getResources().getColor(R.color.order_comfirm_color));
            placeholder.setVisibility(View.GONE);
            tvSave.setVisibility(View.GONE);
        }
    }

    /**
     * 配货单详情接口
     */
    public void getDeliverGoodsDatail() {
        setLoadingMessageIndicator(true);
        SetDeliverPostBean setDeliverPostBean = new SetDeliverPostBean();
        setDeliverPostBean.setDeliver_id(deliverId);
        RetrofitAPIManager.create(DistributionService.class).deliverGoodsDetail(setDeliverPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<DeliverGoodsDetailDataBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<DeliverGoodsDetailDataBean> t) throws Exception {

                setView(t.data.getDeliver().getIs_send());

                tvCustomerName.setText(t.data.getDeliver().getName());
                tvCustomerPhone.setText(t.data.getDeliver().getMobile());
                tvCustomerAddress.setText("详细地址: " + t.data.getDeliver().getDistrict());
                tvOrderSn.setText(t.data.getDeliver().getDeliver_id() + "");
                tvCreateTime.setText(DateUtil.stampToDate(t.data.getDeliver().getAddtime(), " ") + "");
                tvGoodsCount.setText(t.data.getDeliver().getGoods_attr_count() + "" + "种");
                tvGoodsNum.setText(t.data.getDeliver().getNum() + "" + "件");
                tvTotalPayPrice.setText("￥" + t.data.getDeliver().getTotal_price() + "");
                tvCallCustomer.setOnClickListener(v -> DialogUtils.showConfirmDialog("温馨提示", "是否拨打团长电话", "取消", "确定", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + t.data.getDeliver().getMobile());
                        intent.setData(data);
                        startActivity(intent);
                    }
                }));
                ivCallCustomer.setOnClickListener(v -> DialogUtils.showConfirmDialog("温馨提示", "是否拨打团长电话", "取消", "确定", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + t.data.getDeliver().getMobile());
                        intent.setData(data);
                        startActivity(intent);
                    }
                }));
                deliverDetailAdapter.setmDatas(t.getData().getGoods_list());
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
        /**
         * 设置发货接口
         */
        setLoadingMessageIndicator(true);
        SetDeliverPostBean setDeliverPostBean = new SetDeliverPostBean();
        setDeliverPostBean.setDeliver_id(deliverId);
        RetrofitAPIManager.create(DistributionService.class).setDeliver(setDeliverPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                finish();
            }

        });
    }


}
