package com.example.wb.recycleitemorition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.itemorition.ItemOrition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ItemOrition itemOrition = new ItemOrition(this);
        itemOrition.setColor(0xFFDBD6D2);
        recyclerView.addItemDecoration(itemOrition);
        recyclerView.setLayoutManager(manager);

        s = new ArrayList<>();
        for (int i=0;i<20;i++){
            s.add(i+"哈哈哈");
        }
        MyAdapter adapter = new MyAdapter(s);
        recyclerView.setAdapter(adapter);
    }
}
