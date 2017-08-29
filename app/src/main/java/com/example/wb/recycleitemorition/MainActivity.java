package com.example.wb.recycleitemorition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

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
        s = new ArrayList<>();
        for (int i=0;i<20;i++){
            s.add(i+"嘻嘻嘻");
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ItemOrition orition = new ItemOrition(this);
        recyclerView.addItemDecoration(orition);

        recyclerView.setLayoutManager(manager);

        TestHolder adapter = new TestHolder(this,s);
        recyclerView.setAdapter(adapter);

        HeadView headView = new HeadView(this);

        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.activity_textviewcolor,null));
    }
}
