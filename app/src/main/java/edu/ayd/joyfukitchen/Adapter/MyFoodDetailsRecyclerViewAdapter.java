package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.bean.FoodNutrition;

/**
 * Created by Administrator on 2017/5/3.
 */

public class MyFoodDetailsRecyclerViewAdapter extends RecyclerView.Adapter {


    private List<FoodNutrition> datas;
    private Context context;


    public MyFoodDetailsRecyclerViewAdapter(List<FoodNutrition> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_ingrentis_listview, parent, false);
        return new MyFoodDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyFoodDetailsViewHolder myFoodDetailsViewHolder = (MyFoodDetailsViewHolder) holder;
        //设置显示的名字
        myFoodDetailsViewHolder.tv_ck_food_details.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class MyFoodDetailsViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_ck_food_details;
        public ImageView iv_food_right_icon;
        public MyFoodDetailsViewHolder(View view) {
            super(view);
            tv_ck_food_details = (TextView) view.findViewById(R.id.tv_ck_food_name);
            iv_food_right_icon = (ImageView) view.findViewById(R.id.iv_food_right_icon);
        }
    }

}
