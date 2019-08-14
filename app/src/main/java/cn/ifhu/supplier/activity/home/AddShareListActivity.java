package cn.ifhu.supplier.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.ShareListAdapter;
import cn.ifhu.supplier.base.BaseActivity;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.ShareListDataBean;
import cn.ifhu.supplier.model.newbean.post.ShareListPostBean;
import cn.ifhu.supplier.model.newbean.post.SharePostBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.HomeService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.ToastHelper;

public class AddShareListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_share)
    ListView lvShare;
    ShareListAdapter shareListAdapter;
    private List<ShareListDataBean.ShareBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        tvTitle.setText("请选择团长");
        setShareList(1);
        lvShare.setAdapter((ListAdapter) shareListAdapter);
        shareListAdapter = new ShareListAdapter(mDatas, this, new ShareListAdapter.OnclickButton() {
            @Override
            public void addShare(int position) {
                /**
                 * 添加团长接口
                 */
                setLoadingMessageIndicator(true);
                SharePostBean sharePostBean = new SharePostBean();
                sharePostBean.setShare_id(mDatas.get(position).getId() + "");
                RetrofitAPIManager.create(HomeService.class).addShare(sharePostBean)
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

            @Override
            public void deleteShare(int position) {

            }
        },0);
    }

    /**
     * 团长列表接口
     * @param pages
     */
    public void setShareList(int pages) {
        setLoadingMessageIndicator(true);
        ShareListPostBean shareListPostBean = new ShareListPostBean();
        shareListPostBean.setType(1);
        shareListPostBean.setLimit(10);
        shareListPostBean.setPage(pages);
        RetrofitAPIManager.create(HomeService.class).listShare(shareListPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ShareListDataBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ShareListDataBean> t) throws Exception {
                if (pages == 1){
                    mDatas.clear();
                    mDatas.addAll(t.getData().getList());
                    shareListAdapter.setData(mDatas);
                }else {}
                mDatas.addAll(t.getData().getList());
                shareListAdapter.appendList(t.getData().getList());
            }
        });

    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

}
