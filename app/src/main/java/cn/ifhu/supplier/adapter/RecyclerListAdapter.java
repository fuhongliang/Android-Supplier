/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ifhu.supplier.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.model.newbean.data.ProductCatsBean;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.ProductLogic;
import cn.ifhu.supplier.view.ItemTouchHelper.ItemTouchHelperAdapter;
import cn.ifhu.supplier.view.ItemTouchHelper.ItemTouchHelperViewHolder;
import cn.ifhu.supplier.view.ItemTouchHelper.OnStartDragListener;


/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    List<ProductCatsBean.CatsBean> mDataArray = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    public Context context;
    DeleteitemInterface deleteitemInterface;
    public RecyclerListAdapter(Context context, OnStartDragListener dragStartListener) {
        this.context = context;
        mDragStartListener = dragStartListener;
        initkData();
    }

    public void setDeleteitemInterface(DeleteitemInterface deleteitemInterface) {
        this.deleteitemInterface = deleteitemInterface;
    }

    public void initkData() {
        try {
            mDataArray = ProductLogic.getClassList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductCatsBean.CatsBean> getmDataArray() {
        return mDataArray;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.textView.setText(mDataArray.get(position).getName());

        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

        holder.ivTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemMove(holder.getAdapterPosition(),0);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

    }

    public void deleteItem(int position){
        DialogUtils.showConfirmDialog("是否删除此分类","该分类所有商品将会被删除", ((FragmentActivity)context).getSupportFragmentManager(),new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                if (deleteitemInterface != null){
                    deleteitemInterface.deleteItem(mDataArray.get(position).getMch_id(),position);
                }
            }
        });
    }

    public void ItemRemoved(int position){
        mDataArray.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int position) {
        mDataArray.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDataArray, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mDataArray.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView textView;
        public final ImageView handleView;
        public final ImageView ivDelete;
        public final ImageView ivTop;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            ivTop = (ImageView) itemView.findViewById(R.id.iv_top);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public interface DeleteitemInterface{
        void deleteItem(int class_id,int position);
    }
}
