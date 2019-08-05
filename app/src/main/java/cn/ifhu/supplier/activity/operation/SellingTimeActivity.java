package cn.ifhu.supplier.activity.operation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.SellingTime;
import cn.ifhu.supplier.view.dialog.DialogWheelFragment;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class SellingTimeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    List<SellingTime> sellingTimeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellging_time);
        ButterKnife.bind(this);
        tvTitle.setText("可售时间");
        ivSelect.setSelected(true);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.iv_select)
    public void onIvSelectClicked() {
        if (ivSelect.isSelected()) {
            ivSelect.setSelected(false);
        } else {
            ivSelect.setSelected(true);
        }
    }

    @OnClick(R.id.tv_add)
    public void onTvAddClicked() {
        if (ivSelect.isSelected()){
            ToastHelper.makeText("已勾选无限时间",Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("startTime", "07:00");
            bundle.putString("endTime", "09:00");
            DialogWheelFragment.showOperateDialog(getSupportFragmentManager(), bundle, (beginTime, endTime) -> {
                setStoreTime(beginTime, endTime);
            });
        }
    }

    public void setStoreTime(String beginTime, String endTime) {
        View view = getLayoutInflater().inflate(R.layout.item_time, null);
        TextView mTvStartTime = view.findViewById(R.id.tv_start_time);
        TextView mTvEndTime = view.findViewById(R.id.tv_end_time);
        ImageView mIvDelete = view.findViewById(R.id.iv_delete);
        mTvStartTime.setText(beginTime);
        mTvEndTime.setText(endTime);
        mIvDelete.setOnClickListener(v -> {
            int position = llContent.indexOfChild(v);
            llContent.removeView(view);
            sellingTimeList.remove(position);
        });
        llContent.addView(view);
        SellingTime sellingTime = new SellingTime();
        sellingTime.setStart_time(beginTime);
        sellingTime.setEnd_time(endTime);
        sellingTimeList.add(sellingTime);
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (ivSelect.isSelected()){
            ProductLogic.saveSellingTime(new ArrayList<>());
        }else {
            ProductLogic.saveSellingTime(sellingTimeList);
        }
        finish();
    }
}
