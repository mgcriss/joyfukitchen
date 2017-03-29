package edu.ayd.joyfukitchen.Dao;


import android.content.Context;

/**
 * Created by 萝莉 on 2017/3/29.
 */
public class FoodNutritionDao {

    //表文件示newfood.db
    private String DB_Name = "newfood.db";
    //获取一个上下文
    private Context mContext;

    public FoodNutritionDao(Context mContext) {
        this.mContext=mContext;
    }

    
}
