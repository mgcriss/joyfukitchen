package edu.ayd.joyfukitchen.bean;

import java.util.Date;

/**
 * Created by 萝莉 on 2017/3/28.
 * 食品类型
 */
public class Food {
    private Integer id;      //编号
    private String title;   //类型
    private String alias;   //别名
    private Date createtime;//创建时间

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", alias='" + alias + '\'' +
                ", createtime=" + createtime +
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
