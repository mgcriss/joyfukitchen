package edu.ayd.joyfukitchen.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by tangtang on 2017/3/29 10:16.
 */

public class TangDBHelper extends OrmLiteSqliteOpenHelper{


    public TangDBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);



    }




    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {



    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
