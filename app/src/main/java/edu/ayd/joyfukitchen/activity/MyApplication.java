package edu.ayd.joyfukitchen.activity;

import android.app.Application;

import edu.ayd.joyfukitchen.bean.User;

/**
 * Created by Administrator on 2017/4/11.
 */

public class MyApplication extends Application {



    public User user;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
