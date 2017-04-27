package edu.ayd.joyfukitchen.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ayd.joyfukitchen.activity.R;

/**
 * Created by Administrator on 2017/4/24.
 */

public class DiyDateView extends View {
    //数据
    private List<String> data = new ArrayList<String>();
    //Adapter
    private BaseAdapter baseAdapter;
    //view
    private TextView tv_top;
    private TextView tv_bottom;
    private RecyclerView rv_weight;

    public DiyDateView(Context context) {
        super(context);
    }

    public DiyDateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DiyDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**初始化*/
    public void init(final Context context){
        View view = View.inflate(context, R.layout.layout_diydate, null);
        rv_weight = (RecyclerView) view.findViewById(R.id.rv_weight);
        LinearLayoutManager l = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_weight.setLayoutManager(l);




        rv_weight.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_rv_weight, null);

                return new MyHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyHolder m = (MyHolder)holder;
                m.textView.setText("abc");
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });


    }


    static class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.hlv_top);
        }
    }
}
