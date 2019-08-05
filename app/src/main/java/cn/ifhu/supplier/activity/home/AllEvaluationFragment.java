package cn.ifhu.supplier.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.AllEvaluationAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.AllEvaluationDataBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationDeletePostBean;
import cn.ifhu.supplier.model.newbean.post.AllEvaluationPostBean;
import cn.ifhu.supplier.model.newbean.post.CommentReplyBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OrderService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.supplier.view.dialog.nicedialog.ReplyDialog;

/**
 * 全部评价Fragment
 */

public class AllEvaluationFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.recycler_list)
    UltimateRecyclerView recyclerList;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.rl_empty)
    FrameLayout rlEmpty;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;

    AllEvaluationAdapter newEvaluationAdapter;

    private int currentPages = 1;

    public static AllEvaluationFragment newInstance() {
        return new AllEvaluationFragment();
    }

    public AllEvaluationFragment() {
        // Required empty public constructor
    }

    private List<AllEvaluationDataBean.CommentBean> mDatas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerList.setLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.load_more_layout, null, false));
        recyclerList.reenableLoadmore();
        recyclerList.setOnLoadMoreListener((itemsCount, maxLastVisiblePosition) -> {
            // TODO: 2019-07-02 请求处理
            if (newEvaluationAdapter.getLoadingState()) {
                getAllEvaluations(++currentPages);
            }
        });
        newEvaluationAdapter = new AllEvaluationAdapter(mDatas, getActivity(), new AllEvaluationAdapter.OnclickButton() {
            @Override
            public void delete(int position) {

                DialogUtils.showConfirmDialog("温馨提示", "是否删除该评价", "取消", "确定", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        deleteEvaluation(position,1);
                    }
                });

            }

            @Override
            public void hide(int position) {
                int hide = mDatas.get(position).getIs_hide();
                DialogUtils.showConfirmDialog("温馨提示", hide == 1 ? "是否隐藏该评价" : "是否显示该评价", "取消", "确定", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                            deleteEvaluation(position,0);
                    }
                });

            }

            @Override
            public void reply(int position) {
                DialogUtils.showReplyDialog("评价回复", "取消", "确定", getActivity().getSupportFragmentManager(), new ReplyDialog.ButtonOnclick() {

                    @Override
                    public void ok(String content) {
                        /**
                         * 评价回复接口
                         */
                        layoutSwipeRefresh.setRefreshing(true);
                        CommentReplyBean commentReplyBean = new CommentReplyBean();
                        commentReplyBean.setId(mDatas.get(position).getId() + "");
                        commentReplyBean.setContent(content);
                        RetrofitAPIManager.create(OrderService.class).commentReply(commentReplyBean)
                                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                            @Override
                            protected void onApiComplete() {
                                layoutSwipeRefresh.setRefreshing(false);
                            }

                            @Override
                            protected void onSuccees(BaseEntity t) throws Exception {
                                getAllEvaluations(1);
                                ToastHelper.makeText(t.getMessage()).show();
                            }

                        });
                    }
                });
            }
        });
        newEvaluationAdapter.setRecyclerObject(recyclerList);
        recyclerList.setAdapter(newEvaluationAdapter);
        setRefreshLayout();
        getAllEvaluations(1);
    }

    /**
     * 评价删除或隐藏接口
     * @param index
     * @param isDelete
     */
    public void deleteEvaluation(int index,int isDelete) {
        layoutSwipeRefresh.setRefreshing(true);
        AllEvaluationDeletePostBean allEvaluationDeletePostBean = new AllEvaluationDeletePostBean();
        if (isDelete == 1){
            allEvaluationDeletePostBean.setDelete(1);
        }else {
            allEvaluationDeletePostBean.setHide(mDatas.get(index).getIs_hide()==1 ? 0 : 1);
        }
        allEvaluationDeletePostBean.setId(mDatas.get(index).getId() + "");
        RetrofitAPIManager.create(OrderService.class).commentHod(allEvaluationDeletePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                getAllEvaluations(1);
                ToastHelper.makeText(t.getMessage()).show();
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        layoutSwipeRefresh.setOnRefreshListener(() -> getAllEvaluations(1));
    }

    /**
     * 评价列表接口、分页
     * @param pages
     */
    public void getAllEvaluations(int pages) {
        layoutSwipeRefresh.setRefreshing(true);
        AllEvaluationPostBean allEvaluationPostBean = new AllEvaluationPostBean();
        allEvaluationPostBean.setLimit(20);
        allEvaluationPostBean.setPage(pages + "");
        RetrofitAPIManager.create(OrderService.class).getCommentList(allEvaluationPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<AllEvaluationDataBean>(true) {

            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<AllEvaluationDataBean> t) throws Exception {


                if (t.getData() == null || t.getData().getComment().isEmpty()) {
                    newEvaluationAdapter.setLoadingState(true);
                    if (mDatas.size() != 0 && pages == 1) {
                        mDatas.clear();
                        newEvaluationAdapter.updateData(mDatas);
                    }
                } else {
                    if (pages == 1) {
                        mDatas.clear();
                        mDatas.addAll(t.getData().getComment());
                        newEvaluationAdapter.updateData(mDatas);
                        currentPages = 1;
                    } else {
                        mDatas.addAll(t.getData().getComment());
                        newEvaluationAdapter.insert(t.getData().getComment());
                    }
                    if (t.getData().getComment().size() < 20) {
                        newEvaluationAdapter.setLoadingState(true);
                    } else {
                        newEvaluationAdapter.setLoadingState(false);
                    }
                    currentPages = currentPages + 1;
                }
                updateEmptyView();

            }

            @Override
            protected void onAPIError() {
                super.onAPIError();
                Logger.d("onAPIError: ");
            }

            @Override
            protected void onCodeError(BaseEntity<AllEvaluationDataBean> t) throws Exception {
                super.onCodeError(t);
                Logger.d("t.code"+t.code);
            }
        });

    }

    public void updateEmptyView() {
        if (newEvaluationAdapter.getItemCount() > 0) {
//            llEmpty.setVisibility(View.GONE);
        } else {
//            llEmpty.setVisibility(View.VISIBLE);
        }
    }


}
