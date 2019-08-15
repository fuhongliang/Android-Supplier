package cn.ifhu.supplier.fragments.distribution;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.DistributionOrderAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.base.LoadMoreScrollListener;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.DeliverGoodsDataBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.PaginationPostBean;
import cn.ifhu.supplier.model.newbean.post.SetDeliverPostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.DistributionService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

/**
 * 配货单列表
 */
public class DistributionOrderFragment extends BaseFragment {
    Unbinder unbinder;


    @BindView(R.id.recycler_list_test)
    RecyclerView recyclerList;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.rl_empty)
    FrameLayout rlEmpty;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;

    DistributionOrderAdapter newDistrbutionOrderAdapter;

    public static Fragment newInstance() {
        return new DistributionOrderFragment();
    }

    private List<DeliverGoodsDataBean.ListBean> mDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDeliverGoods(1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newDistrbutionOrderAdapter = new DistributionOrderAdapter(mDatas, getActivity(), new DistributionOrderAdapter.OnclickButton() {
            @Override
            public void TvCallCustomer(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否拨打电话", "取消", "确定", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + mDatas.get(position).getMobile());
                        intent.setData(data);
                        getActivity().startActivity(intent);
                    }
                });
            }

            @Override
            public void IvCallCustomer(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否拨打电话", "取消", "确定", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + mDatas.get(position).getMobile());
                        intent.setData(data);
                        getActivity().startActivity(intent);
                    }
                });
            }

            @Override
            public void setDelivery(int position) {
                /**
                 * 设置发货接口
                 */
                setLoadingMessageIndicator(true);
                SetDeliverPostBean setDeliverPostBean = new SetDeliverPostBean();
                RetrofitAPIManager.create(DistributionService.class).setDeliver(setDeliverPostBean)
                        .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                    @Override
                    protected void onApiComplete() {
                        setLoadingMessageIndicator(false);
                    }

                    @Override
                    protected void onSuccees(BaseEntity t) throws Exception {
                        ToastHelper.makeText(t.getMessage()).show();
                    }

                });
            }
        });
        newDistrbutionOrderAdapter.setLoadMordListener(loadIndex -> {
        });
        recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerList.setAdapter(newDistrbutionOrderAdapter);
        recyclerList.setOnScrollListener(new LoadMoreScrollListener(recyclerList));
        getDeliverGoods(1);
    }

    /**
     * 配货单列表接口、分页
     */
    public void getDeliverGoods(int pages) {
        setLoadingMessageIndicator(true);
        PaginationPostBean  paginationPostBean = new PaginationPostBean();
        paginationPostBean.setLimit(10);
        paginationPostBean.setPage(pages);
        RetrofitAPIManager.create(DistributionService.class).deliverGoods(paginationPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<DeliverGoodsDataBean>(false) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<DeliverGoodsDataBean> t) throws Exception {
                if (pages == 1){
                    mDatas.clear();
                    mDatas.addAll(t.getData().getList());
                    newDistrbutionOrderAdapter.setData(mDatas);
                }else {
                    mDatas.addAll(t.getData().getList());
                    newDistrbutionOrderAdapter.appendList(t.getData().getList());
                }
            }
            @Override
            protected void onAPIError() {
                super.onAPIError();
                Logger.d("onAPIError: ");
            }

            @Override
            protected void onCodeError(BaseEntity<DeliverGoodsDataBean> t) throws Exception {
                super.onCodeError(t);
                Logger.d("t.code" + t.code);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
