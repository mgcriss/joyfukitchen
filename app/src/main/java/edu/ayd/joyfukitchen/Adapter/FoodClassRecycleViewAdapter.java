package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ayd.joyfukitchen.activity.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/4/28.
 */

public class FoodClassRecycleViewAdapter extends RecyclerView.Adapter implements View.OnClickListener{


    private Context context;
    private View view;

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    //默认的数据，应该把关系存入数据库
    private int[] icons = new int[]{
            R.mipmap.icon_tools_food_list_gulei, R.mipmap.icon_tools_food_list_shuleidianfen
            , R.mipmap.icon_tools_food_list_gandou, R.mipmap.icon_tools_food_list_shucai
            , R.mipmap.icon_tools_food_list_junzaolei, R.mipmap.icon_tools_food_list_shuiguolei
            , R.mipmap.icon_tools_food_list_jianguozhongzi, R.mipmap.icon_tools_food_list_churoulei
            , R.mipmap.icon_tools_food_list_qinroulei, R.mipmap.icon_tools_food_list_rulei
            , R.mipmap.icon_tools_food_list_danlei, R.mipmap.icon_tools_food_list_yuxiabeilei
            , R.mipmap.icon_tools_food_list_yinyouershipin, R.mipmap.icon_tools_food_list_xiaochitianbing
            , R.mipmap.icon_tools_food_list_sushishipin, R.mipmap.icon_tools_food_list_yinliao
            , R.mipmap.icon_tools_food_list_jiulei, R.mipmap.icon_tools_food_list_tangmijian
            , R.mipmap.icon_tools_food_list_youzhi, R.mipmap.icon_tools_food_list_tiaoweipin
    };
    private String[] titles = new String[]{
            "谷类", "薯类，淀粉", "干豆类", "蔬菜类", "菌藻类", "水果类"
            , "坚果、种子", "畜肉类", "禽肉类", "乳类", "蛋类", "鱼虾蟹贝"
            , "婴幼儿食品", "小吃、甜点", "速食食品", "饮料", "酒类"
            , "糖、蜜饯", "油脂", "调味品"
    };






    public FoodClassRecycleViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = LayoutInflater.from(context).inflate(R.layout.item_rv_food_class,null);

        //将创建的View注册点击事件
        view.setOnClickListener(this);

        return new FoodClassRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoodClassRecycleViewHolder myHolder = (FoodClassRecycleViewHolder) holder;
        Log.i(TAG, "onBindViewHolder: position = " + position);
        myHolder.food_icon.setImageDrawable(context.getResources().getDrawable(icons[position]));
        myHolder.food_title.setText(titles[position]);
        myHolder.food_title.setTag(position+1);

        //将position保存在itemView的Tag中，以便点击时进行获取
        myHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return icons.length;
    }



    //ItemView的点击事件
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    /**设置item点击事件*/
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



    static class FoodClassRecycleViewHolder extends RecyclerView.ViewHolder {

        public ImageView food_icon;
        public TextView food_title;


        public FoodClassRecycleViewHolder(View view) {
            super(view);
            food_icon = (ImageView) view.findViewById(R.id.food_icon);
            food_title = (TextView) view.findViewById(R.id.food_title);
        }
    }





}
