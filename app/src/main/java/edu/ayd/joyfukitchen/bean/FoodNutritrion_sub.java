package edu.ayd.joyfukitchen.bean;

/**
 * Created by Administrator on 2017/5/3.
 */

public class FoodNutritrion_sub {
    //元素名
    private String name;
    //单位
    private String unitName;
    //100g含量
    private Float hanLiang;
    //含量
    private Float curHanLiang;


    @Override
    public String toString() {
        return "FoodNutritrion_sub{" +
                "name='" + name + '\'' +
                ", unitName='" + unitName + '\'' +
                ", hanLiang=" + hanLiang +
                ", curHanLiang=" + curHanLiang +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Float getHanLiang() {
        return hanLiang;
    }

    public void setHanLiang(Float hanLiang) {
        this.hanLiang = hanLiang;
    }

    public Float getCurHanLiang() {
        return curHanLiang;
    }

    public void setCurHanLiang(Float curHanLiang) {
        this.curHanLiang = curHanLiang;
    }
}
