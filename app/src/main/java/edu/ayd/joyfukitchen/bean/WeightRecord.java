
package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 * 一条称取记录，称取一种食材的记录
 */

@DatabaseTable(tableName = "weight_record")
public class WeightRecord implements Serializable{

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private Integer foodId;
    @DatabaseField
    private Float weight;
    @DatabaseField
    private Date weightingTime;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private OnceRecord onceRecord;

    @Override
    public String toString() {
        return "WeightRecord{" +
                "id=" + id +
                ", foodId=" + foodId +
                ", weight=" + weight +
                ", weightingTime=" + weightingTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Date getWeightingTime() {
        return weightingTime;
    }

    public void setWeightingTime(Date weightingTime) {
        this.weightingTime = weightingTime;
    }

    public OnceRecord getOnceRecord() {
        return onceRecord;
    }

    public void setOnceRecord(OnceRecord onceRecord) {
        this.onceRecord = onceRecord;
    }




}

