package edu.ayd.joyfukitchen.dao;


import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import edu.ayd.joyfukitchen.bean.Food;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.dbhelper.DatabaseHelper;

import static android.content.ContentValues.TAG;

/**
 * Created by 萝莉 on 2017/3/29.
 */
public class FoodNutritionDao {
    private Dao<FoodNutrition, Integer> daos;

    private Dao<Food, Integer> foodDao;


    private DatabaseHelper DHelper;

    public FoodNutritionDao(Context mctx) {
        try {
            DHelper = DatabaseHelper.getInstance(mctx);

            daos = DHelper.getDao(FoodNutrition.class);

            foodDao = DHelper.getDao(Food.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询出所有的食材类型
     */
    public List<Food> showAllFoodType() {

        try {
            return (List<Food>) foodDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 查询所有食材
     *
     * @return List<FoodNutrition>
     */
    public List<FoodNutrition> showAllFood() {
        try {
            return daos.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过食材类型id查询出食材
     *
     * @param food_id 食材类型的id
     * @return List<FoodNutrition>
     */
    public List<FoodNutrition> showFoodById(Integer food_id) {
        try {
            List<FoodNutrition> food_id1 = daos.queryForEq("food_id", food_id);
            Log.i(TAG, "showFoodById: FoodNutritionsSize = "+ food_id1.size());
            return food_id1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过食材名字搜索食材，模糊查询
     *
     * @param name
     * @return
     */
    public List<FoodNutrition> showFoodByName(String name) {
        try {
            List<FoodNutrition> foodNutritions = daos.queryBuilder().where().like("name", "%" + name + "%").query();
            return foodNutritions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过食材id得到食材
     *
     * @param id
     * @return FoodNutrition
     */
    public FoodNutrition showFoodByFoodId(Integer id) {
        try {
            return daos.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

