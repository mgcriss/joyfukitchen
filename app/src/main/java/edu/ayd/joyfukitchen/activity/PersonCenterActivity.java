package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import edu.ayd.joyfukitchen.bean.User;

/**
 * Created by tangtang on 2017/4/25 09:47.
 */

public class PersonCenterActivity extends BaseActivity {

    private int i;
    User user;
    //目标
    private TextView target_text,weight_text,height_text,strength_text,edi_text,login_nickName;
    private LinearLayout target_LLay,weight_LLay,height_LLay,strength_LLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setStatusBarTrans();
        //隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.layout_persion_center);

        init();
    }

    private void init() {
        target_LLay = (LinearLayout) findViewById(R.id.target_LLay);
        weight_LLay = (LinearLayout) findViewById(R.id.weight_LLay);
        height_LLay = (LinearLayout) findViewById(R.id.height_LLay);
        strength_LLay = (LinearLayout) findViewById(R.id.strength_LLay);
        target_text =(TextView) findViewById(R.id.target_txt);
        weight_text = (TextView) findViewById(R.id.weight_text);
        height_text = (TextView) findViewById(R.id.height_text);
        strength_text = (TextView) findViewById(R.id.strength_text);
        edi_text = (TextView) findViewById(R.id.edi_text);
        login_nickName = (TextView) findViewById(R.id.login_nickName);

        edi_text.setOnClickListener(new NextClickListener());

        user = (User) getIntent().getSerializableExtra("user");

        DecimalFormat fnum = new DecimalFormat("#.##");
        weight_text.setText(String.valueOf(fnum.format(user.getWeight())));
        height_text.setText(String.valueOf(fnum.format(user.getHeight())));
        target_text.setText(String.valueOf(user.getTarget()));
        strength_text.setText(String.valueOf(user.getWorkStrength()));
        login_nickName.setText(String.valueOf(user.getNickname()));



    }

    /*点击编辑按钮，进入编辑页面*/
    private class NextClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent =new  Intent(PersonCenterActivity.this,EditInformationActivity.class);
            intent.putExtra("id",user.getId());
            startActivity(intent);
        }
    }

}
