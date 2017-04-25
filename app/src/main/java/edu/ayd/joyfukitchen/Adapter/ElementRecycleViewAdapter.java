package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.bean.FoodElement;

/**
 * Created by Administrator on 2017/4/25.
 * 适配器，用于给首页的第一个元素含量recycleView适配数据
 */

public class ElementRecycleViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<FoodElement> elementList;

    public ElementRecycleViewAdapter() {

    }

    /**
     * 构造器
     * @param context: context
     *         elementList: FoodElement集合用来装元素名和元素含量(包括单位)
     * */
    public ElementRecycleViewAdapter(Context context, List<FoodElement> elementList) {
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
        myElementViewHolder.tv_element_name.setText(elementList.get(position).getElementName());
        myElementViewHolder.tv_element_value.setText(elementList.get(position).getElementValue());
    }

    @Override
    public int getItemCount() {
        return elementList.size();
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
