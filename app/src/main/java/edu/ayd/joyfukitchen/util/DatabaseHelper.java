package edu.ayd.joyfukitchen.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import edu.ayd.joyfukitchen.bean.Food;
import edu.ayd.joyfukitchen.bean.FoodNutrition;

/**
 * Created by 萝莉 on 2017/3/29.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_Name="newfood.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;
    private String DATABASE_PATH=mContext.getApplicationContext().getFilesDir().getAbsolutePath()+DB_Name;

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
            onCreate(db,connectionSource);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *清除
     */
    public void deleteDB() {
        if (mContext != null) {
            File f = mContext.getDatabasePath(DB_Name);
            if (f.exists()) {
                f.delete();
            } else {
                mContext.deleteDatabase(DB_Name);
            }

            File file = mContext.getDatabasePath(DATABASE_PATH);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
