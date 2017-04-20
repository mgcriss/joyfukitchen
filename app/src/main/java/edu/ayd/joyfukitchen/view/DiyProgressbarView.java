package edu.ayd.joyfukitchen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.util.EmptyUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/4/18.
 */

public class DiyProgressbarView extends RelativeLayout {

    private TextView tv_element_name;
    private ProgressBar progressBar_top;
    private ProgressBar progressBar_bottom;

    //总的完成量
    private String currentTopValue;
    //总量
    private String maxTopValue;
    //当前占比
    private String currentBottomValue;
    private String maxBottomValue;

    private DecimalFormat format = new DecimalFormat("####.00");


    public DiyProgressbarView(Context context) {
        super(context);
        initView(context);
    }

    public DiyProgressbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
//        currentTopValue = attrs.getAttributeValue("http://schemas.android.com/apk/res/edu.ayd.joyfukitchen.view.DiyProgressbarView", "currentTopValue");
//        maxTopValue = attrs.getAttributeValue("http://schemas.android.com/apk/res/edu.ayd.joyfukitchen.view.DiyProgressbarView", "maxTopValue");
//        currentBottomValue = attrs.getAttributeValue("http://schemas.android.com/apk/res/edu.ayd.joyfukitchen.view.DiyProgressbarView", "currentBottomValue");
//        maxBottomValue = attrs.getAttributeValue("http://schemas.android.com/apk/res/edu.ayd.joyfukitchen.view.DiyProgressbarView", "maxBottomValue");
    }

    public DiyProgressbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    /**
     * 初始化
     */
    private void initView(Context context) {
        View.inflate(context, R.layout.layout_index_below, this);
        tv_element_name = (TextView) findViewById(R.id.tv_element_name);
        progressBar_top = (ProgressBar) findViewById(R.id.progressBar_top);
        progressBar_bottom = (ProgressBar) findViewById(R.id.progressBar_bottom);
    }


    /**
     * 设置上面的progressBar的当前值
     * */
    public void setTopProgress(String text){
        if(EmptyUtils.isNotEmpty(text)) {
            Log.i(TAG, "setTopProgress: text = "+text);
            Integer v = 0;
            try{
               v = Integer.parseInt(text);
            }catch(Exception e){
                Log.e(TAG, "setTopProgress: ", e );
            }
            progressBar_top.setProgress(v);
        }
    }

    /**
     * 设置下面的progressBar的当前值
     * */
    public void setBottomProgress(String text){
        if(EmptyUtils.isNotEmpty(text)) {
            Log.i(TAG, "setBottomProgress: text = "+text);
            Integer v = 0;
            try{
                v = Integer.parseInt(text);
            }catch(Exception e){
                Log.e(TAG, "setTopProgress: ", e );
            }
            progressBar_bottom.setProgress(v);
        }
    }

    /**
     * 设置上面的progerssBar的最大值
     * */
    public void setTopMax(String text) {
        if(EmptyUtils.isNotEmpty(text)) {
            Log.i(TAG, "setTopMax: text = "+text);
            Integer v = 0;
            try{
                v = Integer.parseInt(text);
            }catch(Exception e){
                Log.e(TAG, "setTopProgress: ", e );
            }
            progressBar_top.setMax(v);
        }
    }


    /**
     * 设置下面的progerssBar的最大值
     * */
    public void setBottomMax(String text) {
        if(EmptyUtils.isNotEmpty(text)) {
            Log.i(TAG, "setBottomMax: text = "+text);
            Integer v = 0;
            try{
                v = Integer.parseInt(text);
            }catch(Exception e){
                Log.e(TAG, "setTopProgress: ", e );
            }
            progressBar_bottom.setMax(v);
        }
    }

    /**
     * 设置左边显示的值
     * */
    public void setText(String text){
        if(EmptyUtils.isNotEmpty(text)) {
            tv_element_name.setText(text);
        }
    }

}
