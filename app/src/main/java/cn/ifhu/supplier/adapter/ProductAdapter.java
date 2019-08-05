package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.newbean.data.ProductListBean;
import cn.ifhu.supplier.utils.Constants;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;

/**
 * @author fuhongliang
 */
public class ProductAdapter extends BaseAdapter {
    public List<ProductListBean.GoodsBean> mDataList;
    public Context mContext;
    onClickItem onClickItem;

    /**
     * 重新设置商品列表
     *
     * @param mDataList 商品列表数据
     */
    public void setmDataList(List<ProductListBean.GoodsBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public ProductAdapter(List<ProductListBean.GoodsBean> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    /**
     * 更新商品状态
     * @param position
     */
    public void changeProductState(int position){
        ProductListBean.GoodsBean goodsListBean = mDataList.get(position);
        goodsListBean.setStatus(goodsListBean.getStatus() == 0 ? 1:0);
        // TODO: 2019-06-25 添加商品数据接口完成时，待测试此部分
        // mDataList.get(position).getGoods().get(position).setGoods_state(goodsListBean.getGoods_state() == 0?1:0);
        notifyDataSetChanged();
    }

    public void setOnClickItem(ProductAdapter.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ProductListBean.GoodsBean goodsBean = mDataList.get(position);
        viewHolder.tvPrice.setText(Constants.unit + goodsBean.getPrice());
        viewHolder.tvOriginalPrice.setText(Constants.unit + goodsBean.getOriginal_price());
        viewHolder.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);//设置中划线并加清晰
        viewHolder.tvName.setText(goodsBean.getName());
        viewHolder.tvReserve.setText("当前库存：" + goodsBean.getGoods_num());
        if (mDataList.get(position).getStatus() == 0) {
            viewHolder.tvChangeState.setText("上架");
            viewHolder.tvStateTip.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvChangeState.setText("下架");
            viewHolder.tvStateTip.setVisibility(View.INVISIBLE);
        }
      viewHolder.ivProductImage.load(goodsBean.getCover_pic(),R.drawable.bnt_photo_moren);
        if (onClickItem != null){
            viewHolder.tvChangeState.setOnClickListener(v -> onClickItem.changeState(position));

            viewHolder.tvEdit.setOnClickListener(v -> onClickItem.editProduct(position));

            viewHolder.tvDelete.setOnClickListener(v -> onClickItem.deleteProduct(position));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_product_image)
        GlideImageView ivProductImage;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_original_price)
        TextView tvOriginalPrice;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_reserve)
        TextView tvReserve;
        @BindView(R.id.tv_change_state)
        TextView tvChangeState;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_state_tip)
        TextView tvStateTip;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickItem{
        void changeState(int position);
        void editProduct(int position);
        void deleteProduct(int position);
    }

}
