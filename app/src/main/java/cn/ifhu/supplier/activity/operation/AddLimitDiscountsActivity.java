package cn.ifhu.supplier.activity.operation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.SelectedDiscountGoodsAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.DiscountGoods;
import cn.ifhu.supplier.model.bean.DiscountInfoBean;
import cn.ifhu.supplier.model.bean.DiscountPostBean;
import cn.ifhu.supplier.model.bean.ProductManageBean;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DateUtil;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.StringUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import io.reactivex.Observer;

/**
 * @author fuhongliang
 */
public class AddLimitDiscountsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_discount_name)
    EditText etDiscountName;
    @BindView(R.id.et_tag)
    EditText etTag;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.iv_less)
    ImageView ivLess;
    @BindView(R.id.tv_limit_less)
    TextView tvLimitLess;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_add_goods)
    LinearLayout llAddGoods;
    @BindView(R.id.btn_save)
    Button btnSave;
    int limitNumber = 1;
    List<ProductManageBean.GoodsListBean> listBeans = new ArrayList<>();
    @BindView(R.id.lv_discount_goods)
    ListView lvDiscountGoods;
    SelectedDiscountGoodsAdapter selectedDiscountGoodsAdapter;
    String xianshi_id;
    int type = 0;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_limit_discounts);
        ButterKnife.bind(this);
        xianshi_id = getIntent().getStringExtra("xianshi_id");
        if (StringUtils.isEmpty(xianshi_id)) {
            tvTitle.setText("添加活动");
        } else {
            tvTitle.setText("编辑活动");
            getDiscountInfo();
        }
        selectedDiscountGoodsAdapter = new SelectedDiscountGoodsAdapter(listBeans, this);
        lvDiscountGoods.setAdapter(selectedDiscountGoodsAdapter);

        type = 0;
        initTimePicker();
        tvStartTime.setOnClickListener(v -> {
            type = 0;
            pvTime.show(v);
        });
        tvEndTime.setOnClickListener(v -> {
            type = 1;
            pvTime.show(v);
        });

        selectedDiscountGoodsAdapter.setOnClickItem(new SelectedDiscountGoodsAdapter.onClickItem() {
            @Override
            public void setDiscountPrice(int position) {

            }

            @Override
            public void unselectedGoods(int position) {
                listBeans.remove(position);
                selectedDiscountGoodsAdapter.setmDataList(listBeans);
                ProductLogic.saveDiscountGoods(listBeans);
            }
        });
    }

    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, (date, v) -> {
            if (type == 0) {
                tvStartTime.setText(DateUtil.getDateToString(date));
            } else {
                tvEndTime.setText(DateUtil.getDateToString(date));
            }
        })
                .setTimeSelectChangeListener(date -> {
                    if (date.before(Calendar.getInstance().getTime())) {
                        pvTime.setDate(Calendar.getInstance());
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true)
                .addOnCancelClickListener(view -> {
                })
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }


    public void getDiscountInfo() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getDiscountInfo(xianshi_id, MchInfoLogic.getMchId() + "")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<DiscountInfoBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<DiscountInfoBean> t) throws Exception {
                initData(t.getData());
            }
        });
    }

    public void initData(DiscountInfoBean discountInfoBean) {
        etDiscountName.setText(discountInfoBean.getXianshi_name());
        etTag.setText(discountInfoBean.getXianshi_title());
        etDescription.setText(discountInfoBean.getXianshi_explain());
        tvStartTime.setText(DateUtil.getLongToStringMinute(discountInfoBean.getStart_time()));
        tvEndTime.setText(DateUtil.getLongToStringMinute(discountInfoBean.getEnd_time()));
        tvLimitLess.setText(discountInfoBean.getLower_limit() + "");
        limitNumber = discountInfoBean.getLower_limit();
        listBeans.clear();
        for (DiscountInfoBean.GoodsListBean goodsListBean : discountInfoBean.getGoods_list()) {
            ProductManageBean.GoodsListBean goodsbean = new ProductManageBean.GoodsListBean();
            goodsbean.setGoods_id(goodsListBean.getGoods_id());
            goodsbean.setGoods_name(goodsListBean.getGoods_name());
            goodsbean.setGoods_dicountprice(goodsListBean.getXianshi_price());
            goodsbean.setGoods_price(goodsListBean.getGoods_price());
            goodsbean.setImg_name(goodsListBean.getImg_name());
            goodsbean.setImg_path(goodsListBean.getImg_path());
            listBeans.add(goodsbean);
        }
        selectedDiscountGoodsAdapter.setmDataList(listBeans);
        ProductLogic.saveDiscountGoods(listBeans);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
        ProductLogic.clearDiscountGoods();
    }


    @OnClick(R.id.iv_less)
    public void onIvLessClicked() {
        if (limitNumber != 1) {
            limitNumber = limitNumber - 1;
            tvLimitLess.setText(limitNumber + "");
        }
    }

    @OnClick(R.id.iv_more)
    public void onIvMoreClicked() {
        limitNumber = limitNumber + 1;
        tvLimitLess.setText(limitNumber + "");
    }

    @OnClick(R.id.ll_add_goods)
    public void onLlAddGoodsClicked() {
        Intent intent = new Intent(AddLimitDiscountsActivity.this, DiscountAddProductActivity.class);
        intent.putExtra("isFromLimitDiscount",true);
        startActivity(intent);
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (checkContent()) {
            setLoadingMessageIndicator(true);
            DiscountPostBean discountPostBean = new DiscountPostBean();
            discountPostBean.setXianshi_name(etDiscountName.getText().toString().trim());
            discountPostBean.setXianshi_title(etTag.getText().toString().trim());
            discountPostBean.setStore_id(MchInfoLogic.getMchId() + "");
            discountPostBean.setXianshi_explain(etDescription.getText().toString().trim());
            discountPostBean.setStart_time(tvStartTime.getText().toString());
            discountPostBean.setEnd_time(tvEndTime.getText().toString());
            discountPostBean.setLower_limit(tvLimitLess.getText().toString().trim());
            if (!StringUtils.isEmpty(xianshi_id)) {
                discountPostBean.setXianshi_id(xianshi_id);
            }
            List<DiscountGoods> goodsList = new ArrayList<>();
            for (ProductManageBean.GoodsListBean bean : listBeans) {
                DiscountGoods discountGoods = new DiscountGoods();
                discountGoods.setGoods_id(bean.getGoods_id() + "");
                discountGoods.setXianshi_price(bean.getGoods_dicountprice());
                goodsList.add(discountGoods);
            }
            discountPostBean.setGoods_list(goodsList);
            RetrofitAPIManager.create(OperationService.class).xianshiAddOrEdit(discountPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Observer> t) throws Exception {
                    ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                    finish();
                }
            });
        }
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(etDiscountName.getText().toString().trim())) {
            ToastHelper.makeText("请输入活动名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvStartTime.getText().toString().trim())) {
            ToastHelper.makeText("请选择开始时间", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvEndTime.getText().toString().trim())) {
            ToastHelper.makeText("请选择结束时间", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }


        if (listBeans == null || listBeans.size() < 1) {
            ToastHelper.makeText("请添加商品", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (ProductLogic.getDiscountGoods() != null) {
                listBeans = ProductLogic.getDiscountGoods();
            }
            selectedDiscountGoodsAdapter.setmDataList(listBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
