package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.ayd.joyfukitchen.Adapter.FoodClassRecycleViewAdapter;
import edu.ayd.joyfukitchen.util.ToastUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/4/28.
 */

public class FoodClassIficationActivity extends BaseActivity {

    //view
    private RecyclerView rv_food_class;
    private EditText et_search;

    //Constant
    public static final int REQUEST_RESULT = 0;
    public static final int REQUEST_NORESULT = 1;

    private String weight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setStatusBarTrans();
        //隐藏标题栏
        getSupportActionBar().hide();

        setContentView(R.layout.layout_food_ingrendients);

        Intent intent = getIntent();
        weight = intent.getStringExtra("weight");

        init();
        setListener();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        FoodClassRecycleViewAdapter foodClassRecycleViewAdapter = new FoodClassRecycleViewAdapter(this);

        //设置RecyclerView子项点击事件（跳转到详情页面）
        foodClassRecycleViewAdapter.setOnItemClickListener(new FoodClassRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tv_food_title = (TextView) view.findViewById(R.id.food_title);
                String title = (String) tv_food_title.getText();
                Integer id = (Integer) tv_food_title.getTag();
                ToastUtil.show(FoodClassIficationActivity.this, "click "+title);
                Intent intent = new Intent(FoodClassIficationActivity.this, CheckIngredientsActivity.class);
                intent.putExtra(CheckIngredientsActivity.REQUESTCODE, id);
                intent.putExtra("weight", weight);
                FoodClassIficationActivity.this.startActivity(intent);
                finish();
            }
        });
        rv_food_class.setAdapter(foodClassRecycleViewAdapter);
        rv_food_class.setLayoutManager(gridLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    //初始化
    private void init() {
        rv_food_class = (RecyclerView) findViewById(R.id.rv_food_class);
        et_search = (EditText) findViewById(R.id.et_search);
    }


    //设置各种监听事件
    private void setListener() {

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Log.i(TAG, "onFocusChange: click et_search");
                    Intent intent = new Intent(FoodClassIficationActivity.this, CheckIngredientsActivity.class);
                    intent.putExtra("weight", weight);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }












}
