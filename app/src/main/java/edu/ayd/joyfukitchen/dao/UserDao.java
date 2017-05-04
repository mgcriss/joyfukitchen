package edu.ayd.joyfukitchen.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import edu.ayd.joyfukitchen.bean.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserDao {

    private JoyFuDBHelper joyFuDBHelper;
    private Dao<User, Integer> dao;

    //构造器
    public UserDao(Context context) {
        try {
            joyFuDBHelper = JoyFuDBHelper.getInstance(context);
            dao = joyFuDBHelper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个user
     * */
    public Integer addUser(User u){
        try {
            return (Integer) dao.create(u);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("UserDao","添加user错误");
            return null;
        }
    }

    /**
     * 修改
     * 返回受影响行数
     * */
    public Integer updateUser(User u){
        try {
            return (Integer) dao.update(u);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据id查询user
     * @Result 返回查询到的user或者null
     * */
    public User queryUserForId(Integer id){
        try {
           return (User) dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据账号查询用户信息
     * @param email
     * @return
     */
    public User queryUser(String email){
        try {
            return (User) dao.queryForEq("email",email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}

