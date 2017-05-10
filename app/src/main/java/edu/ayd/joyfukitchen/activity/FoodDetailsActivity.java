package edu.ayd.joyfukitchen.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ayd.joyfukitchen.Adapter.MyFoodNutritionAdapter;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.bean.FoodNutritrion_sub;
import edu.ayd.joyfukitchen.bean.OnceRecord;
import edu.ayd.joyfukitchen.bean.WeightRecord;
import edu.ayd.joyfukitchen.dao.FoodNutritionDao;
import edu.ayd.joyfukitchen.dao.OnceRecordDao;
import edu.ayd.joyfukitchen.dao.WeightRecordDao;
import edu.ayd.joyfukitchen.util.EmptyUtils;
import edu.ayd.joyfukitchen.util.ToastUtil;

/**
 * Created by Administrator on 2017/5/3.
 */

public class FoodDetailsActivity extends BaseActivity {

    //view
    private RecyclerView fooddetails_rv;
    private TextView tv_title;
    private TextView fooddetails_tv_show_weight;
    private ImageView haspre_header_pre;
    private Button haspre_header_add;

    //adapter
    private MyFoodNutritionAdapter myFoodNutritionAdapter;

    //datas
    private List<FoodNutritrion_sub> foodNutritrionSubs = new ArrayList<FoodNutritrion_sub>();

    private Intent intent;

    public static final int OK = 0;
    public static final int CANCEL = 1;

    private int foodId;
    private Float weight;
    private String title;


    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    //handler
    private final Handler mHandler = new MyHandler(this);


    private class MyHandler extends Handler {

        private final WeakReference<FoodDetailsActivity> mActivity;

        public MyHandler(FoodDetailsActivity activity) {
            mActivity = new WeakReference<FoodDetailsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0: {
                    clickReturn();
                    ToastUtil.show(FoodDetailsActivity.this, "保存成功");
                }
                ;
                break;
                default:
                    ;
                    break;
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        getSupportActionBar().hide();
        setStatusBarTrans();

        setContentView(R.layout.layout_fooddetails_activity);
        init();
        intent = getIntent();
        //用来显示的title
        title = intent.getStringExtra("title");
        //查询的食材的id
        foodId = intent.getIntExtra("foodId", 0);
        weight = intent.getFloatExtra("weight", 0f);
        tv_title.setText(title);
        fooddetails_tv_show_weight.setText(getResources().getString(R.string.current_weight) + weight + getResources().getString(R.string.unit_g));
        queryFoodNutritionFromId(foodId, weight);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK: {
                clickReturn();
            }
            ;
            break;
        }
        return false;
    }

    /**
     * 初始化
     */
    private void init() {
        fooddetails_rv = (RecyclerView) findViewById(R.id.fooddetails_rv);
        tv_title = (TextView) findViewById(R.id.haspre_header_title);
        fooddetails_tv_show_weight = (TextView) findViewById(R.id.fooddetails_tv_show_weight);
        haspre_header_pre = (ImageView) findViewById(R.id.haspre_header_pre);
        haspre_header_add = (Button) findViewById(R.id.haspre_header_add);


        haspre_header_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReturn();
            }
        });

        //添加按钮事件,添加记录
        haspre_header_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                saveRecord(v);
            }
        });


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

    /**
     * 欲返回的事件
     */
    private void clickReturn() {
        if (haspre_header_add.getTag() != null) {
            FoodDetailsActivity.this.setResult(OK, intent);
            FoodDetailsActivity.this.finish();
        } else {
            showDialog2Return();
        }
    }

    /**
     * 添加按钮,添加成功弹框
     */
    private void showDialog2Add() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_title));
        builder.setMessage(getResources().getString(R.string.save_success));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickReturn();
            }
        });
        builder.show();
    }

    /**
     * 未添加的时候返回弹框
     */
    private void showDialog2Return() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_title));
        builder.setMessage(getResources().getString(R.string.dialog_content));
        builder.setNegativeButton(getResources().getString(R.string.will_return), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FoodDetailsActivity.this.setResult(CANCEL, intent);
                FoodDetailsActivity.this.finish();
            }
        });

        builder.setPositiveButton(getResources().getString(R.string.will_save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveRecord(haspre_header_add);

            }
        });
        builder.show();
    }


    /**
     * 保存记录
     */
    private void saveRecord(final View v) {
        Log.i("v.getTag", "saveRecord: v.getTag = " + v.getTag());
        //如果为空则保存
        if (EmptyUtils.isEmpty(v.getTag())) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        OnceRecord onceRecord = new OnceRecord();
                        String dateString = FoodDetailsActivity.this.format.format(new Date());
                        onceRecord.setRecordTime(format.parse(dateString));
                        onceRecord.setUser(((MyApplication) FoodDetailsActivity.this.getApplication()).getUser());
                        onceRecord.setDes(title);
                        OnceRecordDao onceRecordDao = new OnceRecordDao(FoodDetailsActivity.this);
                        onceRecordDao.addOneFoodRecord(onceRecord);


                        WeightRecord weightRecord = new WeightRecord();
                        weightRecord.setFoodId(foodId);
                        weightRecord.setWeight(weight);
                        weightRecord.setWeightingTime(new Date());
                        weightRecord.setOnceRecord(onceRecord);

                        WeightRecordDao weightRecordDao = new WeightRecordDao(FoodDetailsActivity.this);
                        weightRecordDao.insert(weightRecord);

                        v.setTag(0);
                        Message message = mHandler.obtainMessage(0);
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        Log.e("保存记录", "run: error = ", e);
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            //不为空则弹框提示已保存
            showDialog2Add();
        }
    }


}
