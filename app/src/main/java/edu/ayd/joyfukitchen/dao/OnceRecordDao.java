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

<<<<<<< HEAD

=======
>>>>>>> dev
    //构造器
    public OnceRecordDao(Context context) {
        try {
            joyFuDBHelper = JoyFuDBHelper.getInstance(context);
            dao = joyFuDBHelper.getDao(OnceRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


<<<<<<< HEAD

=======
>>>>>>> dev

}
