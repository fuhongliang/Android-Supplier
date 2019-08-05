package cn.ifhu.supplier.activity.notice;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.NoticeAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.bean.NoticeBean;
import cn.ifhu.supplier.model.newbean.post.NoticePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.NoticeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;

/**
 * @author fuhongliang
 */
public class NoticeListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_notice)
    ListView lvNotice;
    NoticeAdapter noticeAdapter;
    List<NoticeBean.MsgBean> noticeBeanList = new ArrayList<>();
    @BindView(R.id.rl_empty)
    FrameLayout mRlEmpty;
    private boolean isLast = false;
    private boolean showNomore = false;
    private final int SCROLL_STOP = 881;
    private final int SCROLL_UP = 882;
    private final int SCROLL_DOWN = 883;
    int scrollDirection = SCROLL_STOP;

    private int mLastTopIndex = 0;
    private int mLastTopPixel = 0;

    private int perPage = 20;
    private int firstPage = 1;
    private int totalNumber = 0;
    private int curNumber = 0;
    private int curPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notification);
        ButterKnife.bind(this);
        tvTitle.setText("消息管理");
        noticeAdapter = new NoticeAdapter(noticeBeanList, this);
        lvNotice.setAdapter(noticeAdapter);
        setListPage();
    }


    public void getData() {
        setLoadingMessageIndicator(true);
        NoticePostBean noticePostBean = new NoticePostBean();
        noticePostBean.setLimit(perPage);
        noticePostBean.setPage(curPage);

        RetrofitAPIManager.create(NoticeService.class).getMsgList(noticePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<NoticeBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<NoticeBean> t) throws Exception {
                if (!t.getData().getMsg().isEmpty() && t.getData().getMsg().size() > 0) {
                    noticeBeanList.addAll(t.getData().getMsg());
                    noticeAdapter.setvouCherBeanList(noticeBeanList);
                    curPage += 1;
                    curNumber = noticeAdapter.getCount();
                } else {
                    totalNumber = curNumber;
                }

                if (curNumber == 0){
                    mRlEmpty.setVisibility(View.VISIBLE);
                }else {
                    mRlEmpty.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 设置列表分页
     */
    private void setListPage() {
        lvNotice.setOnScrollListener(new AbsListView.OnScrollListener() {
            int scrollState;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isLast = (totalItemCount == firstVisibleItem + visibleItemCount);
                scrollDirection = SCROLL_STOP;
                View v = view.getChildAt(0);
                int top = (v == null) ? 0 : v.getTop();
                if (firstVisibleItem > mLastTopIndex) {
                    scrollDirection = SCROLL_UP;
                } else if (firstVisibleItem < mLastTopIndex) {
                    scrollDirection = SCROLL_DOWN;
                } else {
                    if (top < mLastTopPixel) {
                        scrollDirection = SCROLL_UP;
                    } else if (top > mLastTopPixel) {
                        scrollDirection = SCROLL_DOWN;
                    }
                }
                mLastTopIndex = firstVisibleItem;
                mLastTopPixel = top;
                if (!isLast) {
                    showNomore = false;
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
                //当滑动到底部 且 手指离开屏幕时 确定为需要加载分页
                if (isLast && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (curNumber < totalNumber) {
                        getData();
                    } else {
                        if (scrollDirection == SCROLL_UP && !showNomore) {
                            Snackbar.make(view, "没有更多了", Snackbar.LENGTH_SHORT).show();
                            showNomore = true;
                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
