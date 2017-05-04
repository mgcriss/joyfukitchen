package edu.ayd.joyfukitchen.service;

import android.content.Context;

import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.dao.UserDao;

/**
 * Created by Administrator on 2017/3/31.
 */

public class UserService {


    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context=context;
    }

    private UserDao userDao = new UserDao(context);



    /**注册
     * 增加一个user
     * */
    public Integer addUser(User u){

        return userDao.addUser(u);
    }


    /**
     * 编辑用户
     * 返回受影响行数
     * */
    public Integer updateUser(User u){
       return userDao.updateUser(u);
    }

    /**
     * 根据id查询user     (查询用户)
     * @Result 返回查询到的user或者null
     * */
    public User queryUserForId(Integer id){
        return userDao.queryUserForId(id);
    }


    /**
     * 根据账号查询用户信息
     * @param email
     * @return
     */
    public User queryUser(String email){
        return userDao.queryUser(email);
    }




}
