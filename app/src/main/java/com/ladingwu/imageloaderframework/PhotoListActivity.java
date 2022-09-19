package com.ladingwu.imageloaderframework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;

    private PhotoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecycleView=findViewById(R.id.recycleview_id);
        mAdapter=new PhotoAdapter(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setList(PhotoBean.createInstances(new DataUrls().getUrls()));

        mAdapter.notifyDataSetChanged();
    }
}
