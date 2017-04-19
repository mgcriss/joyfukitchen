package edu.ayd.joyfukitchen.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.util.EmptyUtils;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ReciderFragment extends Fragment{



    private Context context;
    private View view;

    public ReciderFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(EmptyUtils.isEmpty(view)) {
            view = inflater.inflate(R.layout.layout_recider, container, false);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("a", "onDestroyView: ReciderFragment DestoryView");
        ((ViewGroup)view.getParent()).removeView(view);
    }
}
