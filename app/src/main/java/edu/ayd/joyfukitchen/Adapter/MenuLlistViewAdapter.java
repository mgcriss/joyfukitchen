package edu.ayd.joyfukitchen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.bean.MenuResult;

/**
 * Created by Administrator on 2017/5/4.
 */
public class MenuLlistViewAdapter  extends BaseAdapter{

    private List<MenuResult.ResultBean.DataBean> data;
    private Context context;
    private LayoutInflater inflater;


    public MenuLlistViewAdapter(Context context, List<MenuResult.ResultBean.DataBean> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder{

        private ImageView ml_imgage;
        private TextView ml_title,ml_detailed;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if(convertView == null){
            item = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_menu_list, null);

            item.ml_imgage = (ImageView) convertView.findViewById(R.id.ml_imgage);
            item.ml_title = (TextView) convertView.findViewById(R.id.ml_title);
            item.ml_detailed = (TextView) convertView.findViewById(R.id.ml_detailed);

            convertView.setTag(item);
        }else{
            item = (ViewHolder) convertView.getTag();
        }

        item.ml_title.setText(data.get(position).getTitle());
        item.ml_detailed.setText(data.get(position).getIngredients());
        Picasso.with(context).load(data.get(position).getAlbums().get(0)).into( item.ml_imgage);
        return convertView;
    }


}
