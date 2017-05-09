package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.bean.OnceRecord;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/8.
 */

public class MyRecordsRecyclerViewAdapter extends Adapter {
    private Context context;
    private List<OnceRecord> onceRecords ;

    public MyRecordsRecyclerViewAdapter(Context context, List<OnceRecord> onceRecords) {
        this.context = context;
        this.onceRecords = onceRecords;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_element, null);
        return new MyRecordsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyRecordsRecyclerViewHolder myRecordsHolder = (MyRecordsRecyclerViewHolder) holder;

        OnceRecord onceRecord = null;
        try {
            onceRecord = onceRecords.get(position);
        }catch(Exception e){
            Log.e(TAG, "onBindViewHolder: onceRecords.get(position) = ", e );
        }

        if(onceRecord.getDes() != null){
            myRecordsHolder.tv_element_name.setText(onceRecord.getDes());
        }
        try {
            myRecordsHolder.tv_element_value.setText(onceRecord.getWeightRecords().iterator().next().getWeight().toString() + context.getResources().getString(R.string.unit_g));
        } catch(Exception e){
            Log.e(TAG, "onBindViewHolder: ", e);
        }
    }

    @Override
    public int getItemCount() {
        return onceRecords == null ? 0 :onceRecords.size();
    }


    //holder
    static class MyRecordsRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView tv_element_name;
        TextView tv_element_value;

        public MyRecordsRecyclerViewHolder(View view) {
            super(view);
            tv_element_name = (TextView) view.findViewById(R.id.tv_element_name);
            tv_element_value = (TextView) view.findViewById(R.id.tv_element_value);
        }
    }
}
