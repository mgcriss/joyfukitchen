package edu.ayd.joyfukitchen.util;

/**
 * Created by Administrator on 2017/3/28.
 */

public class WeightUtil {

    /**转安士*/
    public static Float toAnShi(Float weight){
        return weight*0.03527396f;
    }

    /**转毫升
     * @param p 密度
     * */
    public static Float toHaoSheng(Float weight, Float p){
        return weight*p;
    }
    /**转两*/
    public static Float toLiang(Float weight){
        return weight*0.02f;
    }
    /**转磅*/
    public static Float toBang(Float weight){
        return weight*0.00220462f;
    }

}
