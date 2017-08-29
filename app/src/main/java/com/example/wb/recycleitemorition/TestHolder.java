package com.example.wb.recycleitemorition;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itemorition.HFViewAdapter;

import java.util.List;

/**
 * Created by Zhangchen on 2017/8/29.
 */

public class TestHolder extends HFViewAdapter{
    private Context context;
    private List<String> data;
    protected TestHolder(Context c, List s) {
        super(c, s);
        this.context = c;
        this.data = s;
    }
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_textviewcolor, parent, false);
        return new MyHolder(layout);
    }
    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.text.setText(data.get(position));
    }
    @Override
    protected int getItemCounts() {
        return data.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{
        TextView text;
        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
