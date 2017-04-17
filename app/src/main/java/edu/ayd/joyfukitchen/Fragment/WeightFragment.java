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
import edu.ayd.joyfukitchen.view.FontSizeAutoAdjustTextView;

/**
 * Created by Administrator on 2017/4/12.
 */

public class WeightFragment extends Fragment {
    private View view;
    private Button btn_next;
    private Context context;
    private FontSizeAutoAdjustTextView ftv_weight_value;
    private FontSizeAutoAdjustTextView ftv_unit;


    public WeightFragment(Context context) {
        super();
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_weight, container, false);
        init();
//        setListener();

        return view;
    }


    /**初始化控件和数据*/
    private void init() {
        ftv_weight_value = (FontSizeAutoAdjustTextView) view.findViewById(R.id.ftv_weight_value);
        ftv_unit = (FontSizeAutoAdjustTextView) view.findViewById(R.id.ftv_unit);
    }
    /**给控件设置监听事件*/
//    private void setListener() {
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtil.show(context, "click next");
//                ftv_weight_value.setText("0000");
//                ftv_unit.setText("毫升");
//            }
//        });
//    }

}
