package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import edu.ayd.joyfukitchen.Adapter.MyFoodNutritionAdapter;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.bean.FoodNutritrion_sub;
import edu.ayd.joyfukitchen.dao.FoodNutritionDao;

/**
 * Created by Administrator on 2017/5/3.
 */

public class FoodDetailsActivity extends BaseActivity {

    //view
    private RecyclerView fooddetails_rv;
    private TextView tv_title;
    private TextView fooddetails_tv_show_weight;

    //adapter
    private MyFoodNutritionAdapter myFoodNutritionAdapter;

    //datas
    private List<FoodNutritrion_sub> foodNutritrionSubs = new ArrayList<FoodNutritrion_sub>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        getSupportActionBar().hide();
        setStatusBarTrans();

        setContentView(R.layout.layout_fooddetails_activity);
        init();
        Intent intent = getIntent();
        //用来显示的title
        String title = intent.getStringExtra("title");
        //查询的食材的id
        int foodId = intent.getIntExtra("foodId", 0);
        float weight = intent.getFloatExtra("weight", 0f);
        tv_title.setText(title);
        fooddetails_tv_show_weight.setText("当前重量:"+weight+"克");
        queryFoodNutritionFromId(foodId, weight);
    }


    /**
     * 初始化
     */
    private void init() {
        fooddetails_rv = (RecyclerView) findViewById(R.id.fooddetails_rv);
        tv_title = (TextView) findViewById(R.id.haspre_header_title);
        fooddetails_tv_show_weight = (TextView) findViewById(R.id.fooddetails_tv_show_weight);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myFoodNutritionAdapter = new MyFoodNutritionAdapter(this, foodNutritrionSubs);
        fooddetails_rv.setLayoutManager(linearLayoutManager);
        fooddetails_rv.setAdapter(myFoodNutritionAdapter);

    }

    /**
     * 通过id获取数据并显示在页面上
     */
    private void queryFoodNutritionFromId(final int foodId, final float weight) {
        new Thread() {
            @Override
            public void run() {
                try {
                    //获取食材的详细信息（营养含量信息）
                    FoodNutrition foodNutrition = new FoodNutritionDao(FoodDetailsActivity.this).showFoodByFoodId(foodId);
                    Log.i("根据id获取的食材的详细", "run: FoodNutrition = " + foodNutrition);
                    //用来临时存放数据
                    List<FoodNutritrion_sub> datas = new ArrayList<>();
                    Class<? extends FoodNutrition> foodNutritionClass = foodNutrition.getClass();
                    Method[] methods = foodNutritionClass.getMethods();
                    for (Method method : methods) {
                        String methodName = method.getName();
                        Log.i("符合条件的methodName", "run: methodName = " + methodName);
                        //如果该方法以 _unit 结尾，则判断为是一个元素,获取相关的值
                        if (methodName.endsWith("_unit") && methodName.startsWith("get")) {
                            try {
                                FoodNutritrion_sub foodNutritrion_sub = new FoodNutritrion_sub();
                                //调用该方法获取单位
                                String unitName = (String) method.invoke(foodNutrition);
                                Log.i("反射获取的单位", "run: unitName = " + unitName);
                                //字符串截取获取获取数量值的方法
                                String substring = methodName.substring(0, methodName.indexOf("_"));
                                Method method1 = foodNutritionClass.getMethod(substring);
                                Float f = (Float) method1.invoke(foodNutrition);
                                //字符串截取获取元素名
                                String name = methodName.substring(3, methodName.indexOf("_"));
                                //设置值,并添加到list
                                foodNutritrion_sub.setName(name);
                                foodNutritrion_sub.setUnitName(unitName);
                                foodNutritrion_sub.setHanLiang(f);
                                float curHanLiang = foodNutritrion_sub.getHanLiang() * weight / 100;
                                foodNutritrion_sub.setCurHanLiang(curHanLiang);
                                datas.add(foodNutritrion_sub);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }

                    //更新recyclerView显示数据
                    foodNutritrionSubs.clear();
                    foodNutritrionSubs.addAll(datas);
                    myFoodNutritionAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
