package edu.ayd.joyfukitchen.dao;

import android.content.Context;
import android.content.Intent;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import edu.ayd.joyfukitchen.bean.Food;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.bean.OnceRecord;

/**
 * Created by Administrator on 2017/3/30.
    一次记录的dao
 */

public class OnceRecordDao {

    private JoyFuDBHelper joyFuDBHelper;
    private Dao<OnceRecord, Integer> dao;

    private Dao<Food, Integer> foodDao;

    //构造器
    public OnceRecordDao(Context context) {
        try {
            joyFuDBHelper = JoyFuDBHelper.getInstance(context);
            dao = joyFuDBHelper.getDao(OnceRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**显示所有食材类型*/
    public List<Food> showAllFoodType(){
        List<OnceRecord> onceRecords = dao.queryForAll();

        return List<Food>;

    }

    /*列出该食材类型的所有食材*/

    public List<FoodNutrition> showAllFoodNutrition(Integer id){
        List<OnceRecord> query = dao.query();

    }

    /*搜索食材，模糊查询，并将关键字高亮*/

    public



}
