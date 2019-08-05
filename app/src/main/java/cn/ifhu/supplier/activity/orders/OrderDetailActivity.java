package cn.ifhu.supplier.activity.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.OrderDetailBean;
import cn.ifhu.supplier.model.newbean.post.OrderDetailPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OrderService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.Constants;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName;
    @BindView(R.id.tv_customer_phone)
    TextView tvCustomerPhone;
    @BindView(R.id.iv_call_customer)
    ImageView ivCallCustomer;
    @BindView(R.id.tv_customer_address)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_courier_name)
    TextView tvCourierName;
    @BindView(R.id.tv_courier_number)
    TextView tvCourierNumber;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_pay_method)
    TextView tvPayMethod;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.tv_buyer_message)
    TextView tvBuyerMessage;
    @BindView(R.id.et_businessmen_note)
    EditText etBusinessmenNote;
    @BindView(R.id.ok)
    TextView ok;

    int mOrderId;//订单id
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_pay_time_tip)
    TextView tvPayTimeTip;
    @BindView(R.id.tv_send_time_tip)
    TextView tvSendTimeTip;
    @BindView(R.id.tv_shipping_time_tip)
    TextView tvShippingTimeTip;
    @BindView(R.id.tv_add_time_tip)
    TextView tvAddTimeTip;

    //订单进度
    @BindView(R.id.iv_process_one)
    ImageView ivProcessOne;
    @BindView(R.id.iv_step_two)
    ImageView ivStepTwo;
    @BindView(R.id.iv_process_two)
    ImageView ivProcessTwo;
    @BindView(R.id.iv_process_three)
    ImageView ivProcessThree;
    @BindView(R.id.iv_step_three)
    ImageView ivStepThree;
    @BindView(R.id.iv_process_four)
    ImageView ivProcessFour;
    @BindView(R.id.iv_process_five)
    ImageView ivProcessFive;
    @BindView(R.id.iv_step_four)
    ImageView ivStepFour;
    @BindView(R.id.ll_goods_layout)
    LinearLayout llGoodsLayout;

    OrderDetailBean.OrderBean orderBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mOrderId = intent.getIntExtra("OrderId", 0);
        getOrderDetail();
    }

    public void getOrderDetail() {
        OrderDetailPostBean orderDetailPostBean = new OrderDetailPostBean();
        orderDetailPostBean.setOrder_id(mOrderId);
        RetrofitAPIManager.create(OrderService.class).getOrderDetail(orderDetailPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<OrderDetailBean>(true) {
            @Override
            protected void onSuccees(BaseEntity<OrderDetailBean> t) throws Exception {
                orderBean = t.getData().getOrder();
                setOrderProcess(); // 设置订单状态和状态
                tvCustomerName.setText(orderBean.getName());
                tvCustomerPhone.setText(orderBean.getMobile());
                tvCustomerAddress.setText(orderBean.getAddress());
                tvCourierName.setText(orderBean.getExpress().equals("")? "无" : orderBean.getExpress());// 物流公司
                tvCourierNumber.setText(orderBean.getExpress_no().equals("")?"无":orderBean.getExpress_no());// 订单单号
                tvOrderNumber.setText(orderBean.getOrder_no());
                tvOrderDate.setText(DateUtil.stampToDate(orderBean.getAddtime(), " "));
                tvPayMethod.setText(orderBean.getPay_type() == 1 ? "微信支付" : "无");
                if (orderBean.getPay_time() == 0){
                    tvPayTime.setText("无");
                }else {
                    tvPayTime.setText(DateUtil.stampToDate(orderBean.getPay_time(), " "));
                }
                tvFreight.setText(orderBean.getExpress_price());// 运费
                tvTotalAmount.setText(orderBean.getTotal_price());
                tvBuyerMessage.setText(orderBean.getRemark());// 买家留言
                setGoodsView();

            }

            @Override
            protected void onApiComplete() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    /**
     * 设置订单进度
     */
    private void setOrderProcess() {
        if (orderBean.getIs_pay() == 0) {
            /*return "订单待支付";*/
            tvOrderState.setText("订单待支付");
            tvAddTimeTip.setText(DateUtil.stampToDate(orderBean.getAddtime(), "\n"));
        } else if (orderBean.getIs_send() == 0) {
            /*return "订单已支付，还未发货";*/
            tvOrderState.setText("订单已支付，还未发货");
            tvPayTimeTip.setVisibility(View.VISIBLE);
            ivProcessOne.setImageResource(R.drawable.bu_button_blue);
            ivProcessTwo.setImageResource(R.drawable.bu_button_blue);
            ivStepTwo.setImageResource(R.drawable.xiangqing_dui);
            tvPayTimeTip.setText(DateUtil.stampToDate(orderBean.getPay_time(), "\n"));
            tvAddTimeTip.setText(DateUtil.stampToDate(orderBean.getAddtime(), "\n"));
        } else if (orderBean.getIs_confirm() == 0) {
            /*return "订单已支付，用户未确认收货";*/
            tvOrderState.setText("订单已支付，用户未确认收货");
            tvSendTimeTip.setVisibility(View.VISIBLE);
            tvPayTimeTip.setVisibility(View.VISIBLE);
            ivProcessOne.setImageResource(R.drawable.bu_button_blue);
            ivProcessTwo.setImageResource(R.drawable.bu_button_blue);
            ivStepTwo.setImageResource(R.drawable.xiangqing_dui);
            ivProcessThree.setImageResource(R.drawable.bu_button_blue);
            ivProcessFour.setImageResource(R.drawable.bu_button_blue);
            ivStepThree.setImageResource(R.drawable.xiangqing_dui);
            tvSendTimeTip.setText(DateUtil.stampToDate(orderBean.getSend_time(), "\n"));
            tvAddTimeTip.setText(DateUtil.stampToDate(orderBean.getAddtime(), "\n"));
        } else if (orderBean.getIs_confirm() == 1) {
            tvOrderState.setText("订单已完成");
            tvShippingTimeTip.setVisibility(View.VISIBLE);
            tvSendTimeTip.setVisibility(View.VISIBLE);
            tvPayTimeTip.setVisibility(View.VISIBLE);
            ivProcessOne.setImageResource(R.drawable.bu_button_blue);
            ivProcessTwo.setImageResource(R.drawable.bu_button_blue);
            ivStepTwo.setImageResource(R.drawable.xiangqing_dui);
            ivProcessThree.setImageResource(R.drawable.bu_button_blue);
            ivProcessFour.setImageResource(R.drawable.bu_button_blue);
            ivStepThree.setImageResource(R.drawable.xiangqing_dui);
            ivProcessFive.setImageResource(R.drawable.bu_button_blue);
            ivStepFour.setImageResource(R.drawable.xiangqing_dui);
            tvPayTimeTip.setText(DateUtil.stampToDate(orderBean.getPay_time(), "\n"));
            tvSendTimeTip.setText(DateUtil.stampToDate(orderBean.getSend_time(), "\n"));
            tvShippingTimeTip.setText(DateUtil.stampToDate(orderBean.getConfirm_time(), "\n"));
            tvAddTimeTip.setText(DateUtil.stampToDate(orderBean.getAddtime(), "\n"));
        }
    }

    /**
     * 添加商品数据到布局中
     */
    private void setGoodsView(){
        llGoodsLayout.removeAllViews();
        for (int i = 0; i < orderBean.getGoodsList().size(); i++) {
            OrderDetailBean.OrderBean.GoodsListBean extendOrderGoodsBean = orderBean.getGoodsList().get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_product, null);
            TextView mProductName = view.findViewById(R.id.tv_product_name);
            TextView mPrice = view.findViewById(R.id.tv_price);
            TextView mAmount = view.findViewById(R.id.tv_amount);
            TextView mSpecification = view.findViewById(R.id.tv_specification);
            GlideImageView mPic = view.findViewById(R.id.iv_photo);
            mProductName.setText(extendOrderGoodsBean.getName());
            mPrice.setText(Constants.unit + extendOrderGoodsBean.getTotal_price());
            StringBuilder attrText = new StringBuilder();
            for (OrderDetailBean.OrderBean.GoodsListBean.AttrBean attrBean: orderBean.getGoodsList().get(i).getAttr()) {
                attrText.append(attrBean.getAttr_group_name()).append(":").append(attrBean.getAttr_name()).append(" ");
            }
            mSpecification.setText(attrText);
            mAmount.setText("x " + 1);
            mPic.load(extendOrderGoodsBean.getPic());
            llGoodsLayout.addView(view);
        }

    }

    @OnClick({R.id.iv_back, R.id.iv_call_customer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_call_customer://电话
                break;
        }
    }
}
