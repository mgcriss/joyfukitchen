package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/30.
 * 一次称取的记录，一次可能有多条
 */

@DatabaseTable(tableName = "day_record")
public class DayRecord implements Serializable{

}
