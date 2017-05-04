package edu.ayd.joyfukitchen.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ayd.joyfukitchen.activity.R;

/**
 * Created by Administrator on 2017/4/12.
 */

public class UserFragment extends Fragment{

    private Context context;

    public UserFragment(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_user, container, false);
        return view;
    }
}
