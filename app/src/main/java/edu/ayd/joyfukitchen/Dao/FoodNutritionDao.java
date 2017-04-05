package edu.ayd.joyfukitchen.dao;


import android.content.Context;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import edu.ayd.joyfukitchen.bean.Food;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.dbhelper.DatabaseHelper;

/**
 * Created by 萝莉 on 2017/3/29.
 */
public class FoodNutritionDao {
    private Context mctx;
    private Dao<FoodNutrition,Integer> daos;

    private Dao<Food, Integer> foodDao;


    private DatabaseHelper DHelper;

    public FoodNutritionDao(Context mctx) {
        this.mctx = mctx;
        try
        {
            DHelper = DatabaseHelper.getHelper(mctx);
            daos = DHelper.getDao(FoodNutrition.class);

            foodDao = DHelper.getDao(Food.class);

    } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**查询出所有的食材类型*/
    public List<Food> showAllFoodType(){

        try {
             return (List<Food>) foodDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }

    }





}
