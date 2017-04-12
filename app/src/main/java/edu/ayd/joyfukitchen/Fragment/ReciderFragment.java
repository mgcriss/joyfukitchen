package edu.ayd.joyfukitchen.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ayd.joyfukitchen.activity.R;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ReciderFragment extends Fragment{



    private Context context;

    public ReciderFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recider, container, false);
        return view;
    }
}
