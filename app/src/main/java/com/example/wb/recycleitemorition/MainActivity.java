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
        s = new ArrayList<>();
        for (int i=0;i<40;i++){
            s.add(i+"哈哈哈");
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new ItemOrition(this, ItemOrition.VERTICAL,0XFF0ABFFA,0XFFDDDDDD, new ItemOrition.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(s.get(position).charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return s.get(position).substring(0,1).toUpperCase();
            }
        }));
        recyclerView.setLayoutManager(manager);

        MyAdapter adapter = new MyAdapter(s);
        recyclerView.setAdapter(adapter);
    }
}
