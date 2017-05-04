package edu.ayd.joyfukitchen.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Created by tangtang on 2017/4/25 09:47.
 */

public class PersonCenterActivity extends BaseActivity {
    private EditText edt_target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_persion_center);

        edt_target =(EditText) findViewById(R.id.target_edt);

        edt_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View qinganView=View.inflate(PersonCenterActivity.this, R.layout.target_scoll,null);
                final NumberPicker numberPicker= (NumberPicker) qinganView.findViewById(R.id.numberPicker);
                final String strs[]=new String[]{"减脂","增肌","维持体重"};
                numberPicker.setDisplayedValues(strs);
                numberPicker.setMaxValue(strs.length - 1);
                numberPicker.setMinValue(0);
                new AlertDialog.Builder(PersonCenterActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                        .setView(qinganView).setNegativeButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(PersonCenterActivity.this,strs[  numberPicker.getValue()],Toast.LENGTH_SHORT).show();
                        edt_target.setText(strs[  numberPicker.getValue()]);
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

            }
        });





    }




}
