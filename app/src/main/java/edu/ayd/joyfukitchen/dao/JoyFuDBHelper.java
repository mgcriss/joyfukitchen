package edu.ayd.joyfukitchen.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.ayd.joyfukitchen.bean.OnceRecord;
import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.bean.WeightRecord;

/**
 * Created by Administrator on 2017/3/30.
 */

public class JoyFuDBHelper extends OrmLiteSqliteOpenHelper{

    private static final String databaseName = "joyfu.db";
    private static final Integer databaseVersion = 1;

    private static JoyFuDBHelper joyFuDBHelper;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    //构造器.单例
    private JoyFuDBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }
    public static JoyFuDBHelper getInstance(Context context){
        if(joyFuDBHelper == null){
            joyFuDBHelper = new JoyFuDBHelper(context);
        }
        return joyFuDBHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try{
            TableUtils.createTable(connectionSource, OnceRecord.class);    /* 创建OnceRecord表*/
            TableUtils.createTable(connectionSource, User.class);           /*创建User表*/
            TableUtils.createTable(connectionSource, WeightRecord.class);   /*创建WeightRecord表*/
        }catch(SQLException e){
            Log.e("JoyFuDBHelper","oncreate创建表失败");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新表
        try{
            TableUtils.dropTable(connectionSource, OnceRecord.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, WeightRecord.class, true);
            onCreate(database,connectionSource);
        }catch(SQLException e){
            Log.e("JoyFuDBHelper","onUpdate删除表失败");
        }
    }


    /**
     * 通过类来获得指定的Dao
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }


    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}

