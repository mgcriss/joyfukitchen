package edu.ayd.joyfukitchen.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by 萝莉 on 2017/3/28.
 * 食品类型
 */
public class Food {
    @DatabaseField(id=true)
    private Integer id;      //编号
    @DatabaseField
    private String title;   //类型
    @DatabaseField
    private String alias;   //别名

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
