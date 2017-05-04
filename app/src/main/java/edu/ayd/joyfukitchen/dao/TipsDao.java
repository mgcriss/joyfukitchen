package edu.ayd.joyfukitchen.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import edu.ayd.joyfukitchen.bean.Tips;
import edu.ayd.joyfukitchen.dbhelper.DatabaseHelper;

/**
 * Created by tangtang on 2017/4/13 11:09.
 *
 * 主页温馨小贴士
 */

public class TipsDao {

    private Dao<Tips,Integer> daos;

    private DatabaseHelper DHelper;

    public TipsDao(Context mctx) {
        try
        {
            DHelper = DatabaseHelper.getHelper(mctx);
            daos =DHelper.getDao(Tips.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 根据小贴士随机 id得到小贴士内容
     * @return Tips
     */
    private Tips showOneTip(){

        int tipId = (int)(Math.random()*100+1);

        try {
            return daos.queryForId(tipId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
