package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import edu.ayd.joyfukitchen.constants.Sex;
import edu.ayd.joyfukitchen.constants.Target;
import edu.ayd.joyfukitchen.constants.WorkStrength;

/**
 * Created by Administrator on 2017/3/30.
 */

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(id = true)
    private Integer id;

    private String nickname;
    private Date birth;
    private Sex sex;
    private float height;
    private float weight;

    private Target target;                  /**目标*/

    private WorkStrength workStrength;      /**工作强度*/

    
    //// TODO: 2017/3/30 属性待完成 

    @ForeignCollectionField(eager = true)
    private ForeignCollection<OnceRecord> onceRecords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ForeignCollection<OnceRecord> getOnceRecords() {
        return onceRecords;
    }

    public void setOnceRecords(ForeignCollection<OnceRecord> onceRecords) {
        this.onceRecords = onceRecords;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public WorkStrength getWorkStrength() {
        return workStrength;
    }

    public void setWorkStrength(WorkStrength workStrength) {
        this.workStrength = workStrength;
    }
}
