package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 * 一次称取的记录，一次可能有多条，一道菜的记录
 * 在称取的时候，以次来作为计算单位
 */

@DatabaseTable(tableName = "day_record")
public class OnceRecord implements Serializable{

    @DatabaseField(id = true)
    private Integer id;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<WeightRecord> weightRecords;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField
    private Date recordTime;

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ForeignCollection<WeightRecord> getWeightRecords() {
        return weightRecords;
    }

    public void setWeightRecords(ForeignCollection<WeightRecord> weightRecords) {
        this.weightRecords = weightRecords;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OnceRecord{" +
                "id=" + id +
                ", weightRecords=" + weightRecords +
                ", user=" + user +
                ", recordTime=" + recordTime +
                '}';
    }
}
