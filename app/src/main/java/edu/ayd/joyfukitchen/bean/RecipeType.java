package edu.ayd.joyfukitchen.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class RecipeType {

    //种类id
    private String parentId;
    //种类名
    private String name;
    //子分类
    private List<ListBean> list;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * name : 家常菜
         * parentId : 10001
         */
        //菜谱子类id
        private String id;
        //菜谱子类名
        private String name;
        //菜谱父类名
        private String parentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }
}
