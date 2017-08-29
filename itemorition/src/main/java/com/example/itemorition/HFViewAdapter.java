package com.example.itemorition;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by Zhangchen on 2017/8/29.
 */

public abstract class HFViewAdapter extends RecyclerView.Adapter{

    private Context c;
    private List data;
    private RecyclerView mRecyclerView;
    private int TYPE_HEADVIEW = 1001;
    private int TYPE_FOOTVIEW = 2002;
    private int TYPE_NORMAL = 3003;
    private View headView;
    private View footView;

    protected HFViewAdapter(Context c,List s) {
        this.c = c;
        this.data = s;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADVIEW){
            return new MyViewHolder(headView);
        }else if (viewType == TYPE_FOOTVIEW){
            return new MyViewHolder(footView);
        }else {
            return onCreateViewHolde(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)){
            if (haveHeaderView()) position--;
            onBindViewHolde(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        int count = getItemCounts();
        if (footView != null) {
            count++;
        }
        if (headView != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)){
            return TYPE_HEADVIEW;
        }else if (isFooterView(position)){
            return TYPE_FOOTVIEW;
        }else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            headView = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }
    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            footView = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ? ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType);

    protected abstract void onBindViewHolde(RecyclerView.ViewHolder holder, int position);

    protected abstract int getItemCounts();

    private boolean haveHeaderView() {
        return headView != null;
    }

    public boolean haveFooterView() {
        return footView != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
