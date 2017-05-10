package edu.ayd.joyfukitchen.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import edu.ayd.joyfukitchen.bean.Food;
import edu.ayd.joyfukitchen.dbhelper.DatabaseHelper;

/**
 * Created by 萝莉 on 2017/4/5.
 */
public class FoodDao {
    private Dao<Food,Integer> daos;

    private DatabaseHelper DHelper;

    public FoodDao(Context mctx) {
        try
        {
            DHelper = DatabaseHelper.getInstance(mctx);
            daos = DHelper.getDao(Food.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 查询所有类型
     * @return  List<Food>
     */
    public List<Food> showAllFood(){
        try {
            return daos.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Food> showFoodByName(String title){
        try {
            return (List<Food>) daos.queryBuilder().where().like("title","%"+title+"%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
