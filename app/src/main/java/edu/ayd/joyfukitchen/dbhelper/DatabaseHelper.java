package edu.ayd.joyfukitchen.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

import static android.content.ContentValues.TAG;

/**
 * Created by 萝莉 on 2017/3/29.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_Name = "newfood.db";
    private static final int DATABASE_VERSION = 1;
    private static Context mContext;

    private static String DATABASE_PATH;

    private static DatabaseHelper instance;
    private Map<String, Dao> daos = new HashMap<String, Dao>();


    /**
     * 将db文件移动到手机中，并创建并打开
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, DATABASE_VERSION);
        mContext = context;

        try {
            Context applicationContext = mContext.getApplicationContext();
            File filesDir = applicationContext.getFilesDir();
            String absolutePath = filesDir.getAbsolutePath();
            DATABASE_PATH = absolutePath + "/" + DB_Name;
            Log.i(TAG, "DatabaseHelper: databasePath = " + DATABASE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(DATABASE_PATH);
        FileOutputStream out = null;
        InputStream in = null;
        if (!file.exists()) {
            try {
                out = new FileOutputStream(file);
                in = mContext.getAssets().open("newfood.db");
                byte[] buffer = new byte[1024];
                int readBytes = -1;
                while ((readBytes = in.read(buffer)) != -1)
                    out.write(buffer, 0, readBytes);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null && out != null) {
                        in.close();
                        out.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
            Log.i(TAG, "DatabaseHelper: db打开成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
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
     *
     * @param db
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.clearTable(connectionSource, Food.class);
            TableUtils.clearTable(connectionSource, FoodNutrition.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当应用程序升级时，它会被调用，并且它具有较高的
     * 版本号。这允许您调整各种数据以匹配
     * 新版本号。
     *
     * @param db
     * @param connectionSource
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Food.class, true);
            TableUtils.dropTable(connectionSource, FoodNutrition.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    /**
     * Dao，用作增删改查
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {

        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            return daos.get(className);
        }

        Dao dao = null;
        try {
            dao = super.getDao(clazz);
            daos.put(className, dao);
            return dao;

        } catch (Exception e) {
            e.printStackTrace();
            return dao;
        }

    }


    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
