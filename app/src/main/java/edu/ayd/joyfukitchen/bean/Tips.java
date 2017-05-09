package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by tangtang on 2017/4/13 11:00.
 *
 * 温馨小贴士实体类
 */

public class Tips {

    @DatabaseField
    private Integer tipId;
    @DatabaseField
    private String tipCount;


    public Integer getTipId() {
        return tipId;
    }

    public void setTipId(Integer tipId) {
        this.tipId = tipId;
    }

    public String getTipCount() {
        return tipCount;
    }

    public void setTipCount(String tipCount) {
        this.tipCount = tipCount;
    }



}
