package cn.ifhu.supplier.fragments.goods;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.operation.AddOrEditCategoryActivity;
import cn.ifhu.supplier.activity.operation.AddProductOneActivity;
import cn.ifhu.supplier.activity.operation.ManageCategoryActivity;
import cn.ifhu.supplier.adapter.CategoryAdapter;
import cn.ifhu.supplier.adapter.ProductAdapter;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.model.bean.BaseEntity;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.model.newbean.data.ProductListBean;
import cn.ifhu.supplier.model.newbean.post.BasePostBean;
import cn.ifhu.supplier.model.newbean.post.GoodsListPostBean;
import cn.ifhu.supplier.model.newbean.post.GoodsShelvesBean;
import cn.ifhu.supplier.net.BaseObserver;
import cn.ifhu.supplier.net.OperationService;
import cn.ifhu.supplier.net.RetrofitAPIManager;
import cn.ifhu.supplier.net.SchedulerUtils;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.utils.ToastHelper;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

import static cn.ifhu.supplier.utils.Constants.DATA;
import static cn.ifhu.supplier.utils.MchInfoLogic.getMchAccessToken;

/**
 * @author tony
 */
public class GoodsFragment extends BaseFragment {

    @BindView(R.id.lv_category)
    ListView lvCategory;
    @BindView(R.id.lv_product)
    ListView lvProduct;
    List<ProductCatsBean.CatsBean> mDataArray = new ArrayList<>();
    List<ProductListBean.GoodsBean> mProductArray = new ArrayList<>();
    CategoryAdapter mCategoryAdapter;
    ProductAdapter mProductAdapter;
    public int mCatId = 0;
    //分类商品的ID
    public int classPosition = 0;

    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //当前商品分类的位置

    public static GoodsFragment newInstance() {
        return new GoodsFragment();
    }


    public GoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.neworder_empty, null);
        lvProduct.setEmptyView(emptyView);
        mCategoryAdapter = new CategoryAdapter(mDataArray, getActivity(), position -> {
            mCatId = mDataArray.get(position).getId();
            classPosition = position;
            mCategoryAdapter.notifyDataSetChanged();
            getProductListData(mCatId, true);
        });
        lvCategory.setAdapter(mCategoryAdapter);

        mProductAdapter = new ProductAdapter(mProductArray, getActivity());
        mProductAdapter.setOnClickItem(new ProductAdapter.onClickItem() {
            @Override
            public void changeState(int position) {
                int operation = mProductArray.get(position).getStatus();
                DialogUtils.showConfirmDialog("温馨提示", "是否确认对该商品" + (operation == 0 ? "上架?" : "下架?"),
                        getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok() {
                                changeProductState(position);
                            }
                        });
            }

            @Override
            public void editProduct(int position) {
                goToEditProduct(position, classPosition);
            }

            @Override
            public void deleteProduct(int position) {
                DialogUtils.showConfirmDialog("温馨提示", "是否确定删除该商品？", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {
                    }

                    @Override
                    public void ok() {
                        delProduct(position);
                    }
                });
            }
        });
        lvProduct.setAdapter(mProductAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        //设置主标题Title
        mToolbar.setTitle("商品管理");
        mToolbar.setTitleTextColor(Color.WHITE);
        //设置menu
        mToolbar.inflateMenu(R.menu.goods_menu_toolbar);
        //设置NavigationIcon点击响应
        mToolbar.setNavigationOnClickListener(view -> {
        });

        //设置menu点击监听
        mToolbar.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            switch (id) {
                case R.id.menu_add_cate:
                    startActivity(new Intent(getActivity(), AddOrEditCategoryActivity.class));
                    break;
                case R.id.menu_add_goods:
                    startActivity( new Intent(getActivity(), AddProductOneActivity.class));
                    break;
                case R.id.menu_cate_manage:
                    Intent intent = new Intent(getActivity(), ManageCategoryActivity.class);
                    intent.putExtra("DATA",mCatId);
                    startActivity(intent);
                    break;
              default:
                  break;

            }
            return false;
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCatsData();
    }

    /**
     * 获取商品分类数据
     */
    public void getCatsData() {
        BasePostBean basePostBean = new BasePostBean();
        basePostBean.setAccess_token(getMchAccessToken());
        basePostBean.setMch_id(MchInfoLogic.getMchId());
        RetrofitAPIManager.create(OperationService.class).catsList(basePostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductCatsBean>(true) {

            @Override
            protected void onApiComplete() {
            }

            @Override
            protected void onSuccees(BaseEntity<ProductCatsBean> t) throws Exception {
                if (t.getData().getCats() == null || t.getData().getCats().size() == 0) {
                    DialogUtils.showConfirmDialog("温馨提示", "您的店铺还没有商品分类 \n 是否新建商品分类？", "否", "是", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void ok() {
                            startActivity(new Intent(getActivity(), AddOrEditCategoryActivity.class));
                        }
                    });
                } else {
                    mDataArray = t.getData().getCats();
                    mCategoryAdapter.setmDataList(mDataArray);
                    if (mDataArray != null && mDataArray.size() > 0) {
                        mCatId = mDataArray.get(classPosition).getId();
                        getProductListData(mCatId, false);
                    }
                    ProductLogic.saveClass(mDataArray);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }


    /**
     * 获取指定分类的商品列表数据
     *
     * @param catsId 分类ID
     */
    public void getProductListData(int catsId, boolean showLoading) {
        if (showLoading) {
            setLoadingMessageIndicator(true);
        }
        GoodsListPostBean goodsListPostBean = new GoodsListPostBean();
        goodsListPostBean.setCat_id(catsId);
        RetrofitAPIManager.create(OperationService.class).goodsList(goodsListPostBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductListBean>(true) {

            @Override
            protected void onApiComplete() {
                if (showLoading) {
                    setLoadingMessageIndicator(false);
                }
            }

            @Override
            protected void onSuccees(BaseEntity<ProductListBean> t) throws Exception {
                mProductArray = t.getData().getGoods();
                mProductAdapter.setmDataList(mProductArray);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    /**
     * 上下架商品
     *
     * @param position 操作商品的索引
     */
    public void changeProductState(int position) {
        setLoadingMessageIndicator(true);
        GoodsShelvesBean goodsShelvesBean = new GoodsShelvesBean();
        goodsShelvesBean.setGoods_id(mProductArray.get(position).getGoods_id());
        goodsShelvesBean.setStatus(mProductArray.get(position).getStatus() == 0 ? 1 : 0);
        RetrofitAPIManager.create(OperationService.class).goodsShelves(goodsShelvesBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                mProductAdapter.changeProductState(position);
            }
        });
    }

    /**
     * 删除商品
     *
     * @param position 删除商品的索引
     */
    public void delProduct(int position) {
        setLoadingMessageIndicator(true);
        GoodsShelvesBean goodsShelvesBean = new GoodsShelvesBean();
        goodsShelvesBean.setGoods_id(mProductArray.get(position).getGoods_id());
        RetrofitAPIManager.create(OperationService.class).deleteGoods(goodsShelvesBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                mProductArray.remove(position);
                mProductAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 编辑商品
     *
     * @param position         商品列表索引
     * @param curClassPosition
     */
    public void goToEditProduct(int position, int curClassPosition) {
        Intent intent = new Intent(getActivity(), AddProductOneActivity.class);
        intent.putExtra(DATA, 1);
        intent.putExtra("edit_goods_id", mProductArray.get(position).getGoods_id());
        startActivity(intent);
    }
}
