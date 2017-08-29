package com.example.wb.recycleitemorition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Zhangchen on 2017/8/29.
 */

public class HeadView {
    private Context context;private Button button;private TextView textView;
    public HeadView(Context context) {
        this.context = context;
    }
    public View createView(){
        LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView = layout.inflate(R.layout.headview,null); //头部视图
        headFindViewById(headView);//获得头部控件
        headOnClick();//头部控件监听
        return headView;
    }
    private void headFindViewById(View headView) {
        button = (Button) headView.findViewById(R.id.headview_but);
        textView = (TextView) headView.findViewById(R.id.head_tv);
    }
    private void headOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"头部点击事件",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
