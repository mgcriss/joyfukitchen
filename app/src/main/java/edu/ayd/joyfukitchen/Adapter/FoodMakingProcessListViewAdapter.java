package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.bean.MenuResult;

/**
 * Created by Administrator on 2017/5/6.
 */
public class FoodMakingProcessListViewAdapter extends BaseAdapter{

    private MenuResult.ResultBean.DataBean data;
    private Context context;
    private LayoutInflater inflater;

    public FoodMakingProcessListViewAdapter(Context context,MenuResult.ResultBean.DataBean data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return data.getSteps().size();
    }

    @Override
    public Object getItem(int i) {
        return data.getSteps().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder item = null;

        if (view == null) {
            item = new ViewHolder();
            view = inflater.inflate(R.layout.item_food_making_process,null);

            item.textView = (TextView) view.findViewById(R.id.item_fmp_text);
            item.imageView = (ImageView) view.findViewById(R.id.item_fmp_img);

            view.setTag(item);
        }else {
            item = (ViewHolder) view.getTag();
        }

        item.textView.setText(data.getSteps().get(i).getStep());
        Picasso.with(context).load(data.getSteps().get(i).getImg()).into(item.imageView);
        return view;
    }

    static class ViewHolder{
        private TextView textView;
        private ImageView imageView;
    }

}
