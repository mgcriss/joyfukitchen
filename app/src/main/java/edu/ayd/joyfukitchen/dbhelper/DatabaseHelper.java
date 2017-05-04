package edu.ayd.joyfukitchen.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.ayd.joyfukitchen.bean.Food;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.bean.Tips;

/**
 * Created by 萝莉 on 2017/3/29.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_Name="newfood.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;
    private String DATABASE_PATH=mContext.getApplicationContext().getFilesDir().getAbsolutePath()+DB_Name;
    private static DatabaseHelper instance;
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    /**
     * 将db文件移动到手机中，并创建并打开
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, DATABASE_VERSION);
        mContext = context;
        String dbPath= mContext.getApplicationContext().getFilesDir().getAbsolutePath()+DB_Name;
        File file=new File(dbPath);
        if (file.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbPath);
                InputStream in = mContext.getAssets().open("newfood.db");
                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1)
                    out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
        onCreate(db);
        db.close();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * 创建表
     * @param db
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
       try {
           TableUtils.clearTable(connectionSource,Food.class);
           TableUtils.clearTable(connectionSource,FoodNutrition.class);
           TableUtils.clearTable(connectionSource,Tips.class);
       }catch (SQLException e){
           e.printStackTrace();
       }
    }

    /**
     *当应用程序升级时，它会被调用，并且它具有较高的
     *版本号。这允许您调整各种数据以匹配
     *新版本号。
     * @param db
     * @param connectionSource
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,Food.class,true);
            TableUtils.dropTable(connectionSource,FoodNutrition.class,true);
            TableUtils.dropTable(connectionSource,Tips.class,true);
            onCreate(db,connectionSource);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        context = context.getApplicationContext();
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    /**
     *Dao，用作增删改查
     */
    public synchronized Dao getDao(Class clazz) throws SQLException
    {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className))
        {
            dao = daos.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }



    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet())
        {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
