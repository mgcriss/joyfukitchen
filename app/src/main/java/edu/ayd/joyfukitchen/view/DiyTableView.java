package edu.ayd.joyfukitchen.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.util.EmptyUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/4/25.
 */

public class DiyTableView extends LinearLayout {

    private TextView tab_top;
    private TextView tab_bottom;

    private static final int[] mAttr = {R.attr.topTex, R.attr.bottomTex};
    private static final int TOP_TEX = 0;
    private static final int BOTTOM_TEX = 1;


    public DiyTableView(Context context) {
        this(context, null);
    }

    public DiyTableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }





    public DiyTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, mAttr);

        String topValue = ta.getString(TOP_TEX);
        setTopText(topValue);

        String bottomValue = ta.getString(BOTTOM_TEX);
        setBottomText(bottomValue);
    }


    public void initView(Context context) {
        View view = View.inflate(context, R.layout.table_layout, this);
        tab_top = (TextView) view.findViewById(R.id.tab_top);
        tab_bottom = (TextView) view.findViewById(R.id.tab_bottom);
    }


    /**
     * 给上面的textView设置值
     */
    public void setTopText(String text) {
        if (EmptyUtils.isNotEmpty(tab_top)) {
            tab_top.setText(text);
            Log.i(TAG, "setTopText: topValue = " + text);

        } else {
            Log.i(TAG, "setTopText: tab_top is null");
        }
    }

    /**
     * 给下面的textView设置值
     */
    public void setBottomText(String text) {
        if (EmptyUtils.isNotEmpty(tab_top)) {
            tab_bottom.setText(text);
            Log.i(TAG, "setBottomText: topValue = " + text);

        } else {
            Log.i(TAG, "setTopText: tab_bottom is null");
        }
    }
}
