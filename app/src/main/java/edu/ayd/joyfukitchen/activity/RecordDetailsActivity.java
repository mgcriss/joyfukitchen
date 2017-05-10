package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ayd.joyfukitchen.Adapter.MyFoodNutritionAdapter;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.bean.FoodNutritrion_sub;
import edu.ayd.joyfukitchen.bean.OnceRecord;
import edu.ayd.joyfukitchen.dao.FoodNutritionDao;
import edu.ayd.joyfukitchen.util.WeightUtil;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RecordDetailsActivity extends BaseActivity {

    private static final String TAG = "RecordDetailsActivity";
    //view
    private TextView fooddetails_tv_show_weight;
    private TextView haosheng;
    private TextView angsi;
    private TextView bang;
    private TextView liang;
    private RecyclerView fooddetails_rv;
    private ImageView haspre_header_pre;
    private Button haspre_header_add;
    private TextView haspre_header_title;


    //data
    private OnceRecord onceRecord;
    private List<FoodNutritrion_sub> foodNutritrionSubs = new ArrayList<FoodNutritrion_sub>();

    //else
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private MyFoodNutritionAdapter myFoodNutritionAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTrans();
        //隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.layout_recorddetails_activity);
        initView();

        Intent intent = getIntent();
        onceRecord = (OnceRecord) intent.getSerializableExtra("data");
        Log.i(TAG, "onCreate: 获取到的传过来的data(OnceRecords)" + data);
        //设置title
        haspre_header_title.setText(onceRecord.getDes());

        //设置显示的重量值
        Float weight = 0f;
        try {
            weight = onceRecord.getWeightRecords().iterator().next().getWeight();
        }catch(Exception e){
            Log.e(TAG, "onCreate: ",e );
        }
        fooddetails_tv_show_weight.setText("当前重量:" + weight + getResources().getString(R.string.unit_g));
        liang.setText(decimalFormat.format(WeightUtil.toLiang(weight)));
        bang.setText(decimalFormat.format(WeightUtil.toBang(weight)));
        angsi.setText(decimalFormat.format(WeightUtil.toAnShi(weight)));
        haosheng.setText(decimalFormat.format(WeightUtil.toHaoSheng(weight, 1f)));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myFoodNutritionAdapter = new MyFoodNutritionAdapter(this, foodNutritrionSubs);
        fooddetails_rv.setLayoutManager(linearLayoutManager);
        fooddetails_rv.setAdapter(myFoodNutritionAdapter);
        Integer foodId = 0;
        try {
            foodId = onceRecord.getWeightRecords().iterator().next().getFoodId();
        }catch(Exception e){
            Log.e(TAG, "onCreate: ", e);
        }
        queryFoodNutritionFromId(foodId, weight);
    }

    /**初始化*/
    private void initView() {
        fooddetails_tv_show_weight = (TextView) findViewById(R.id.fooddetails_tv_show_weight);
        liang = (TextView) findViewById(R.id.liang);
        bang = (TextView) findViewById(R.id.bang);
        angsi = (TextView) findViewById(R.id.angsi);
        haosheng = (TextView) findViewById(R.id.haosheng);
        fooddetails_rv = (RecyclerView) findViewById(R.id.fooddetails_rv);
        haspre_header_pre = (ImageView) findViewById(R.id.haspre_header_pre);
        haspre_header_add = (Button) findViewById(R.id.haspre_header_add);
        haspre_header_title = (TextView) findViewById(R.id.haspre_header_title);
        haspre_header_add.setVisibility(View.GONE);
        haspre_header_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordDetailsActivity.this.finish();
            }
        });
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
                    FoodNutrition foodNutrition = new FoodNutritionDao(RecordDetailsActivity.this).showFoodByFoodId(foodId);
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
                            } catch (Exception e) {
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
