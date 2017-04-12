package edu.ayd.joyfukitchen.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.util.ToastUtil;

/**
 * Created by Administrator on 2017/4/12.
 */

public class WeightFragment extends Fragment {
    private View view;
    private Button btn_next;
    private Context context;


    public WeightFragment(Context context) {
        super();
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_weight, container, false);
        init();
        setListener();
        return view;
    }


    /**初始化控件和数据*/
    private void init() {
        btn_next = (Button) view.findViewById(R.id.btn_next);

    }
    /**给控件设置监听事件*/
    private void setListener() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show(context, "click next");
            }
        });
    }

}
