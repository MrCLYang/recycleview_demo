package com.example.ycl.recycleview_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> list;
    private RecyclerView rv_show;
    private recycleviewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }
        rv_show = findViewById(R.id.rv_show);
        //设置rv的设置管理器
//        rv_show.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));//纵向排列
//        rv_show.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //横向排列
//        rv_show.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//纵向排列
        //设置网格布局
        rv_show.setLayoutManager(new GridLayoutManager(this,3));
        rv_show.addItemDecoration(new DividerGridViewItemDecoration(this));
        //设置瀑流布局
//        rv_show.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new recycleviewAdapter(list);
        mAdapter.setOnItemClickListener(new recycleviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String data) {
                Toast.makeText(MainActivity.this,"你点击了:"+data,Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnItemLongClickListener(new recycleviewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position, String data) {
                Toast.makeText(MainActivity.this,"你长时间点击了："+data,Toast.LENGTH_SHORT).show();
            }
        });
        rv_show.setItemAnimator(new DefaultItemAnimator());//设置添加或者删除item时候的动画，这里使用默认动画
        rv_show.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gird,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add){
            mAdapter.addItem(2,"我是增加的item");
        }else if(item.getItemId()==R.id.action_delete){

            mAdapter.deleteItem(2);
        }
        return true;
    }
}
