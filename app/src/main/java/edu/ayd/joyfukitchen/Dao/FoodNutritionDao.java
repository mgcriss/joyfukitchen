package edu.ayd.joyfukitchen.dao;


import android.content.Context;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.dbhelper.DatabaseHelper;

/**
 * Created by 萝莉 on 2017/3/29.
 */
public class FoodNutritionDao {
    private Context mctx;
    private Dao<FoodNutrition,Integer> daos;
    private DatabaseHelper DHelper;

    public FoodNutritionDao(Context mctx) {
        this.mctx = mctx;
        try
        {
            DHelper = DatabaseHelper.getHelper(mctx);
            daos = DHelper.getDao(FoodNutrition.class);
    } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
