package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.field.DatabaseField;
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


}
