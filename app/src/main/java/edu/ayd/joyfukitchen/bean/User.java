package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ayd.joyfukitchen.util.DateUtil;

/**
 * Created by Administrator on 2017/3/30.
 */

@DatabaseTable(tableName = "users")
public class User implements Serializable {

    @DatabaseField(id = true)
    private Integer id;
    private String username;                 //账户2017-4-10 新增email字段
    private String nickname;
    private String birth;
    private String sex;
    private float height;
    private float weight;

    private Integer age;

    public Integer getAge() {                  //当前时间减去生日得到年龄

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        String birth = df.format(getBirth());
        return DateUtil.yearDateDiff(birth, date);
    }



    private String target;                  /**目标*/

    private String workStrength;      /**工作强度*/

    private Integer workTimes;

    public Integer getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(Integer workTimes) {
        this.workTimes = workTimes;
    }

    /**工作时间*/





    //// TODO: 2017/3/30 属性待完成

    @ForeignCollectionField(eager = true)
    private ForeignCollection<OnceRecord> onceRecords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getWorkStrength() {
        return workStrength;
    }

    public void setWorkStrength(String workStrength) {
        this.workStrength = workStrength;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birth=" + birth +
                ", sex='" + sex + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                ", target='" + target + '\'' +
                ", workStrength='" + workStrength + '\'' +
                ", workTimes=" + workTimes +
                ", onceRecords=" + onceRecords +
                '}';
    }


}

