package edu.ayd.joyfukitchen.bean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class FoodElement {
    private String elementName;
    private String elementValue;

    @Override
    public String toString() {
        return "FoodElement{" +
                "elementName='" + elementName + '\'' +
                ", elementValue='" + elementValue + '\'' +
                '}';
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementValue() {
        return elementValue;
    }

    public void setElementValue(String elementValue) {
        this.elementValue = elementValue;
    }
}
