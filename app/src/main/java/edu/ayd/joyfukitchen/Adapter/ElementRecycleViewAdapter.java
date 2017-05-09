package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.bean.FoodNutritrion_sub;

/**
 * Created by Administrator on 2017/4/25.
 * 适配器，用于给首页的第一个元素含量recycleView适配数据
 */

public class ElementRecycleViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<FoodNutritrion_sub> elementList;


    /**
     * 构造器
     * @param context: context
     *         elementList: FoodElement集合用来装元素名和元素含量(包括单位)
     * */
    public ElementRecycleViewAdapter(Context context, List<FoodNutritrion_sub> elementList) {
        this.context = context;
        this.elementList = elementList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_element, null);
        return new MyElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyElementViewHolder myElementViewHolder = (MyElementViewHolder) holder;
        FoodNutritrion_sub foodNutritrion_sub = elementList.get(position);
        int anInt = 0;
        try {
            Field field = R.string.class.getField(foodNutritrion_sub.getName());
            anInt = field.getInt(new R.string());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        myElementViewHolder.tv_element_name.setText(context.getResources().getString(anInt));
        myElementViewHolder.tv_element_value.setText(foodNutritrion_sub.getCurHanLiang()+foodNutritrion_sub.getUnitName());
    }

    @Override
    public int getItemCount() {
        return elementList == null ? 0 :elementList.size();
    }


    //holder
    static class MyElementViewHolder extends RecyclerView.ViewHolder{

        TextView tv_element_name;
        TextView tv_element_value;

        public MyElementViewHolder(View view) {
            super(view);
            tv_element_name = (TextView) view.findViewById(R.id.tv_element_name);
            tv_element_value = (TextView) view.findViewById(R.id.tv_element_value);
        }
    }


}
