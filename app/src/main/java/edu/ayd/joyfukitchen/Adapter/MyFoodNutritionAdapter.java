package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.bean.FoodNutritrion_sub;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/3.
 */

public class MyFoodNutritionAdapter extends RecyclerView.Adapter {

    private Context context;
    //该数据需要整理
    private List<FoodNutritrion_sub> datas;

    public MyFoodNutritionAdapter(Context context, List<FoodNutritrion_sub> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fooddetails_rv, parent, false);
        return new MyFoodNutritionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyFoodNutritionViewHolder myFoodNutritionViewHolder = (MyFoodNutritionViewHolder) holder;
        FoodNutritrion_sub foodNutritrion_sub = datas.get(position);
        int anInt = 0;
        try {
            Field field = R.string.class.getField(foodNutritrion_sub.getName());
            anInt = field.getInt(new R.string());
            Log.i(TAG, "onBindViewHolder: 反射获取的R文件的属性id为 = "+ anInt);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        myFoodNutritionViewHolder.fooddetails_food_name.setText(context.getResources().getString(anInt)+"("+foodNutritrion_sub.getUnitName()+")");
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String format = decimalFormat.format(foodNutritrion_sub.getCurHanLiang());
        myFoodNutritionViewHolder.fooddetails_food_curhanliang.setText(format.toString());
        myFoodNutritionViewHolder.fooddetails_food_hanliang.setText(foodNutritrion_sub.getHanLiang().toString());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public static class MyFoodNutritionViewHolder extends RecyclerView.ViewHolder{

        public TextView fooddetails_food_name;
        public TextView fooddetails_food_curhanliang;
        public TextView fooddetails_food_hanliang;

        public MyFoodNutritionViewHolder(View view) {
            super(view);
            fooddetails_food_name = (TextView) view.findViewById(R.id.fooddetails_food_name);
            fooddetails_food_curhanliang = (TextView) view.findViewById(R.id.fooddetails_food_curhanliang);
            fooddetails_food_hanliang = (TextView) view.findViewById(R.id.fooddetails_food_hanliang);
        }
    }

}
