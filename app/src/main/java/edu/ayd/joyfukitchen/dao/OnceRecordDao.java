package edu.ayd.joyfukitchen.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import edu.ayd.joyfukitchen.bean.OnceRecord;

/**
 * Created by Administrator on 2017/3/30.
    一次记录的dao
 */

public class OnceRecordDao {

    private edu.ayd.joyfukitchen.dao.JoyFuDBHelper joyFuDBHelper;
    private Dao<OnceRecord, Integer> dao;

    //构造器
    public OnceRecordDao(Context context) {
        try {
            joyFuDBHelper = edu.ayd.joyfukitchen.dao.JoyFuDBHelper.getInstance(context);
            dao = joyFuDBHelper.getDao(OnceRecord.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
