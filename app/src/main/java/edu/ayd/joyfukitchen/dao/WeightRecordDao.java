package edu.ayd.joyfukitchen.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import edu.ayd.joyfukitchen.bean.WeightRecord;

/**
 * Created by Administrator on 2017/3/30.
 */

public class WeightRecordDao {

    private JoyFuDBHelper joyFuDBHelper;
    private Dao<WeightRecord, Integer> dao;

    public WeightRecordDao(Context context) {
        try {
            joyFuDBHelper = JoyFuDBHelper.getInstance(context);
            dao = joyFuDBHelper.getDao(WeightRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增
     * @Param weightRecord : 需要保存的weightRecord
     * @Return 返回保存成功的weightRecord的id
     * */
    public Integer insert(WeightRecord weightRecord){
        Integer id = null;
        try{
           id = dao.create(weightRecord);
        }catch(SQLException e){
            e.printStackTrace();
            Log.e("WeightRecordDao","保存WeightRecord出错");
        }
        return id;
    }

    /**
     * 根据id删除
     * @Param weightRecord : 需要删除的weightRecordId
     * @Return 返回删除成功的weightRecord的条数
     * */
    public Integer delete(Integer weightRecordId){
        Integer id = null;
        try{
            id = dao.deleteById(weightRecordId);
        }catch(SQLException e){
            e.printStackTrace();
            Log.e("WeightRecordDao","删除WeightRecord出错");
        }
        return id;
    }

    /**
     * 更新
     * @Param weightRecord : 需要更新的weightRecord
     * @Return 返回更新成功的weightRecord的条数
     * */
    public Integer update(WeightRecord weightRecord){
        Integer id = null;
        try{
            id = dao.update(weightRecord);
        }catch(SQLException e){
            e.printStackTrace();
            Log.e("WeightRecordDao","更新WeightRecord出错");
        }
        return id;
    }



    /**
     * 根据OnceRecord id
     * 查询用户该次的称量记录
     * */
    public List<WeightRecord> getAllWeightRecord(Integer onceRecordId){
        List<WeightRecord> weightRecords = null;
        try {
            weightRecords = dao.queryForEq("onceRecord", "onceRecordId");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("WeightRecordDao","查询用户记录失败");
        }
        return weightRecords;
    }
}
