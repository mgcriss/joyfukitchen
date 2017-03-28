package edu.ayd.joyfukitchen.bean;

/**
 * Created by 萝莉 on 2017/3/28.
 */
public class Food {
    private String id;
    private String title;
    private String alias;
    private String createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Food(String id, String title, String alias, String createtime) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.createtime = createtime;
    }


}
