package edu.ayd.joyfukitchen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import edu.ayd.joyfukitchen.Adapter.FoodClassRecycleViewAdapter;

/**
 * Created by Administrator on 2017/4/28.
 */

public class FoodClassIficationActivity extends BaseActivity {

    private RecyclerView rv_food_class;
    private static int REQUEST_CODE = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setStatusBarTrans();

        setContentView(R.layout.layout_food_ingrendients);
        init();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FoodClassRecycleViewAdapter foodClassRecycleViewAdapter = new FoodClassRecycleViewAdapter(this);
        //设置子项点击事件（跳转到详情页面）
        foodClassRecycleViewAdapter.setOnItemClickListener(new FoodClassRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        rv_food_class.setAdapter(foodClassRecycleViewAdapter);
        rv_food_class.setLayoutManager(gridLayoutManager);

    }

    private void init() {
        rv_food_class = (RecyclerView) findViewById(R.id.rv_food_class);
    }
}
