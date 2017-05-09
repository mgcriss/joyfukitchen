package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ayd.joyfukitchen.activity.R;

/**
 * Created by Administrator on 2017/4/24.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    //数据源
    private List<String> data = new ArrayList<String>();
    //context
    private Context context;
    //view
    private View view;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_rv_weight, parent, false);
        }
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder viewHolder = (MyHolder) holder;
        //TODO
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    //构造器
    public MyRecyclerViewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    //MyHolder
    static class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_top;
        TextView tv_bottom;

        public MyHolder(View itemView) {
            super(itemView);
            tv_top = (TextView) itemView.findViewById(R.id.hlv_top);
            tv_bottom = (TextView) itemView.findViewById(R.id.hlv_bottom);
        }
    }

}
