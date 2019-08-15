package cn.ifhu.supplier.activity.distribution;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.PickingDetailsAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.PickingDetailsDataBean;
import cn.ifhu.supplier.model.newbean.post.SetPickingPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.DistributionService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.view.ExpandListView;

/**
 * 商品拣货单详情页面
 */
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
    @BindView(R.id.rl_customer)
    RelativeLayout rlCustomer;
    @BindView(R.id.listView)
    ExpandListView listView;
    List<PickingDetailsDataBean.GoodsListBean> mData = new ArrayList<>();

    PickingDetailsAdapter newpickingDetailsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_details);
        ButterKnife.bind(this);
        tvTitle.setText("拣货单详情");
        newpickingDetailsAdapter = new PickingDetailsAdapter(mData,this);
        listView.setAdapter(newpickingDetailsAdapter);
        getPickingDetails();
    }

//    public void setDatas() {
//        Intent intent = getIntent();
//        tvOrderSn.setText(intent.getIntExtra("Huodan_id", 0) + "");
//        tvCreateTime.setText(DateUtil.stampToDate(intent.getIntExtra("Create_time", 0), " "));
//        tvGoodsNum.setText(intent.getIntExtra("Goods_num", 0) + "件");
//        tvGoodsCount.setText(intent.getIntExtra("Goods_count", 0) + "" + "种");
//        tvTotalPayPrice.setText("￥" + intent.getStringExtra("Total_pay_price"));
//        if (intent.getIntExtra("pick_status", 0) == 0) {
//            tvOrderState.setText("待拣货");
//            tvOrderState.setTextColor(getResources().getColor(R.color.order_service_color));
//            rlCustomer.setVisibility(View.GONE);
//            tvSave.setVisibility(View.VISIBLE);
//
//        } else {
//            tvOrderState.setText("已拣货");
//            tvOrderState.setTextColor(getResources().getColor(R.color.order_comfirm_color));
//            rlCustomer.setVisibility(View.GONE);
//            tvSave.setVisibility(View.GONE);
//        }
//    }

    /**
     * 拣货单详情接口
     */

    public void getPickingDetails() {
        setLoadingMessageIndicator(true);
        Intent intent = getIntent();
        SetPickingPostBean setPickingPostBean = new SetPickingPostBean();
        setPickingPostBean.setHuodan_id(intent.getIntExtra("Huodan_id", 0));
        tvCreateTime.setText(DateUtil.stampToDate(intent.getIntExtra("Create_time", 0), " "));
        tvGoodsNum.setText(intent.getIntExtra("Goods_num", 0) + "件");
        tvGoodsCount.setText(intent.getIntExtra("Goods_count", 0) + "" + "种");
        tvTotalPayPrice.setText("￥" + intent.getStringExtra("Total_pay_price"));
        if (intent.getIntExtra("pick_status", 0) == 0) {
            tvOrderState.setText("待拣货");
            tvOrderState.setTextColor(getResources().getColor(R.color.order_service_color));
            rlCustomer.setVisibility(View.GONE);
            tvSave.setVisibility(View.VISIBLE);

        } else {
            tvOrderState.setText("已拣货");
            tvOrderState.setTextColor(getResources().getColor(R.color.order_comfirm_color));
            rlCustomer.setVisibility(View.GONE);
            tvSave.setVisibility(View.GONE);
        }
        RetrofitAPIManager.create(DistributionService.class).pickDetail(setPickingPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<PickingDetailsDataBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }
            @Override
            protected void onSuccees(BaseEntity<PickingDetailsDataBean> t) throws Exception {
                newpickingDetailsAdapter.setmData(t.getData().getGoods_list());
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {

    }
}
