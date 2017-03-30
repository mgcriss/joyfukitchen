package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import edu.ayd.joyfukitchen.constants.Sex;

/**
 * Created by Administrator on 2017/3/30.
 */

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField
    private Date birth;
    @DatabaseField
    private Sex sex;
    
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
}
