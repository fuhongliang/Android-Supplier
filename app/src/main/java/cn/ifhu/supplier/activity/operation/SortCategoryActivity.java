package cn.ifhu.supplier.activity.operation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.adapter.RecyclerListAdapter;
import cn.ifhu.supplier.fragments.operation.SortCategoryListFragment;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.GsonUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.ItemTouchHelper.OnStartDragListener;
import cn.ifhu.supplier.view.ItemTouchHelper.SimpleItemTouchHelperCallback;

/**
 * @author fuhongliang
 */
public class SortCategoryActivity extends BaseActivity  implements OnStartDragListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    SortCategoryListFragment fragment;

    @BindView(R.id.recycler_list)
    RecyclerView recyclerView;


    private ItemTouchHelper mItemTouchHelper;
    RecyclerListAdapter adapter = new RecyclerListAdapter(this,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_category);
        ButterKnife.bind(this);
        tvTitle.setText("排序/批量操作");
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setDeleteitemInterface(new RecyclerListAdapter.DeleteitemInterface() {
            @Override
            public void deleteItem(int class_id, int position) {
                deleteGoodsClass(class_id,position);
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
        setLoadingMessageIndicator(true);
        List<Integer> classIds = new ArrayList<>();
        for (ProductCatsBean.CatsBean classListBean : getmDataArray()) {
            classIds.add(classListBean.getMch_id());
        }

        RetrofitAPIManager.create(OperationService.class).sortGoodsClass(GsonUtils.convertObject2Json(classIds), MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<ProductCatsBean.CatsBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<ProductCatsBean.CatsBean>> t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                ProductLogic.saveClass(t.getData());
                finish();
            }
        });
    }

    public void deleteGoodsClass(int class_id,int position){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delGoodsClass(class_id, MchInfoLogic.getMchId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<Object>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<Object>> t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                adapter.ItemRemoved(position);
                ProductLogic.saveClass(getmDataArray());
            }
        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public List<ProductCatsBean.CatsBean> getmDataArray() {
        return adapter.getmDataArray();
    }

}
