package edu.ayd.joyfukitchen.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.util.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/5/5.
 */

public class EditInformationActivity extends BaseActivity {
    private LinearLayout gender_LLay,occupation_LLay,birthday_LLay,height_info_llay,target_info_llay,strength_info_llay;
    private TextView gender_text,occupation_text,birthday_text,keep_txt,height_info_txt,target_info_txt,strength_info_txt;
    private EditText nickName_info_edt;
    private String nickName,sex,birthday,weight,height,target,strength,keep_user_url;
    private  User user;
    private Gson gson;
    private int userId;



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    ToastUtil.show(EditInformationActivity.this,"保存失败！");
                    break;
                case 1:
                    ToastUtil.show(EditInformationActivity.this,"保存成功！");
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setStatusBarTrans();
        //隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.my_info_title);

        init();
    }

    private void init() {
        nickName_info_edt = (EditText) findViewById(R.id.nickName_info_edt);
        keep_txt = (TextView) findViewById(R.id.keep_txt);
        height_info_txt = (TextView) findViewById(R.id.height_info_txt);
        target_info_txt = (TextView) findViewById(R.id.target_info_txt);
        strength_info_txt = (TextView) findViewById(R.id.strength_info_txt);
        gender_LLay = (LinearLayout) findViewById(R.id.gender_LLay);
        height_info_llay = (LinearLayout) findViewById(R.id.height_info_llay);
        strength_info_llay = (LinearLayout) findViewById(R.id.strength_info_llay);
        gender_text = (TextView) findViewById(R.id.gender_text);
        target_info_llay = (LinearLayout) findViewById(R.id.target_info_llay);
        occupation_LLay = (LinearLayout) findViewById(R.id.occupation_LLay);
        occupation_text = (TextView) findViewById(R.id.occupation_text);
        birthday_text = (TextView) findViewById(R.id.birthday_text);
        birthday_LLay = (LinearLayout) findViewById(R.id.birthday_LLay);
        gender_LLay.setOnClickListener(new GenderClickListener());
        occupation_LLay.setOnClickListener(new OccupationClickListener());
        birthday_LLay.setOnClickListener(new Birthday());
        keep_txt.setOnClickListener(new KeepInfoClickListener());
        height_info_llay.setOnClickListener(new HeightClickListener());
        target_info_llay.setOnClickListener(new TargetClickListener());
        strength_info_llay.setOnClickListener(new StrengthClickListener());
        gson = new Gson();

        userId = (int) getIntent().getSerializableExtra("id");

    }

    //日期选择方法
    class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);//年
            int month = c.get(Calendar.MONTH);//月
            int day = c.get(Calendar.DAY_OF_MONTH);//日
            return new DatePickerDialog(getActivity(), this, year, month, day);

        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            birthday_text.setText(year+" - "+(month+1)+" - "+day);
        }
    }

    //日期点击更改
    private class Birthday implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            DatePickerFragment datePicker = new DatePickerFragment();
            datePicker.show(getFragmentManager(),"datePicker");
        }
    }


    //性别
    private class GenderClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            View qinganView=View.inflate(EditInformationActivity.this, R.layout.target_scoll,null);
            final NumberPicker numberPicker= (NumberPicker) qinganView.findViewById(R.id.numberPicker);
            final String strs[]=new String[]{"男","女"};
            numberPicker.setDisplayedValues(strs);
            numberPicker.setMaxValue(strs.length - 1);
            numberPicker.setMinValue(0);
            new AlertDialog.Builder(EditInformationActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                    .setView(qinganView).setNegativeButton("提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(EditInformationActivity.this,strs[  numberPicker.getValue()],Toast.LENGTH_SHORT).show();
                    gender_text.setText(strs[  numberPicker.getValue()]);
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();

        }
    }

    // /体重
    private class OccupationClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            View qinganView=View.inflate(EditInformationActivity.this, R.layout.target_scoll,null);
            final NumberPicker numberPicker= (NumberPicker) qinganView.findViewById(R.id.numberPicker);
            final String[] strs  = new String[61];
            for (int i=0;i<61;i++){
                strs[i] = String.valueOf((int) (35+i)+"kg");
            }
            numberPicker.setDisplayedValues(strs);
            numberPicker.setMaxValue(strs.length - 1);
            numberPicker.setMinValue(0);
            new AlertDialog.Builder(EditInformationActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                    .setView(qinganView).setNegativeButton("提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(EditInformationActivity.this,""+strs[  numberPicker.getValue()],Toast.LENGTH_SHORT).show();
                    occupation_text.setText(strs[  numberPicker.getValue()]);
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();

        }
    }

/*身高*/

    private class HeightClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            View qinganView=View.inflate(EditInformationActivity.this, R.layout.target_scoll,null);
            final NumberPicker numberPicker= (NumberPicker) qinganView.findViewById(R.id.numberPicker);

            ///final String strs[]=new String[]{"120以下","121cm","122cm","123cm","124cm","125cm","126cm","127cm","128cm"};
            final String[] strs  = new String[81];
            for (int i=0;i<81;i++){
                strs[i] = String.valueOf((int) (120+i)+"cm");
            }
            numberPicker.setDisplayedValues(strs);
            numberPicker.setMaxValue(strs.length - 1);
            numberPicker.setMinValue(0);
            new AlertDialog.Builder(EditInformationActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                    .setView(qinganView).setNegativeButton("提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(EditInformationActivity.this,""+strs[  numberPicker.getValue()],Toast.LENGTH_SHORT).show();
                    height_info_txt.setText(strs[  numberPicker.getValue()]);
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();


        }
    }
/*目标*/

    private class TargetClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            View qinganView=View.inflate(EditInformationActivity.this, R.layout.target_scoll,null);
            final NumberPicker numberPicker= (NumberPicker) qinganView.findViewById(R.id.numberPicker);
            final String strs[]=new String[]{"减脂","增肌","维持体重"};
            numberPicker.setDisplayedValues(strs);
            numberPicker.setMaxValue(strs.length - 1);
            numberPicker.setMinValue(0);
            new AlertDialog.Builder(EditInformationActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                    .setView(qinganView).setNegativeButton("提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(EditInformationActivity.this,strs[  numberPicker.getValue()],Toast.LENGTH_SHORT).show();
                    target_info_txt.setText(strs[  numberPicker.getValue()]);
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();

        }
    }


    //工作强度
    private class StrengthClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            View qinganView=View.inflate(EditInformationActivity.this, R.layout.target_scoll,null);
            final NumberPicker numberPicker= (NumberPicker) qinganView.findViewById(R.id.numberPicker);
            final String strs[]=new String[]{"脑力劳动","中度体力劳动","较体力劳动"};
            numberPicker.setDisplayedValues(strs);
            numberPicker.setMaxValue(strs.length - 1);
            numberPicker.setMinValue(0);
            new AlertDialog.Builder(EditInformationActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                    .setView(qinganView).setNegativeButton("提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(EditInformationActivity.this,strs[  numberPicker.getValue()],Toast.LENGTH_SHORT).show();
                    strength_info_txt.setText(strs[  numberPicker.getValue()]);
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();

        }
    }



/*点击保存按钮,保存用户信息*/

    private class KeepInfoClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            nickName = nickName_info_edt.getText().toString();
            sex  = gender_text.getText().toString();
            birthday = birthday_text.getText().toString();
            weight = occupation_text.getText().toString();
            height = height_info_txt.getText().toString();
            target = target_info_txt.getText().toString();
            strength = strength_info_txt.getText().toString();

            user = new User();
            user.setNickname(nickName);
            user.setSex(sex);
            user.setBirth(birthday);
            user.setWeight(Float.parseFloat(weight));
            user.setHeight(Float.parseFloat(height));
            user.setTarget(target);
            user.setWorkStrength(strength);

            keep_user_url = "http://www.chedles.cn/joyfulkitchen/recipe/updateUserData.do?";
            final OkHttpClient okHttpClient=new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("users", String.valueOf(user))
                    .build();

            //创建一个请求对象
            final Request request = new Request.Builder()
                    .url(keep_user_url)
                    .post(body)
                    .build();
            //发送请求获取响应

            new Thread() {
                @Override
                public void run() {
                    try {
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("IOException", "IO异常");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    user = gson.fromJson(response.body().string(), User.class);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (user != null) {
                                    handler.sendEmptyMessage(1);
                                } else
                                    handler.sendEmptyMessage(0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();


        }
    }


/*性别字符串转枚举*//*

    public Sex getStatusName(String sex){
        if(sex.equals("男"))
            return Enum.valueOf(Sex.class, String.valueOf(MALE));
        else
            return Enum.valueOf(Sex.class, String.valueOf(FEMALE));
       */
/* MALE = "男";
        FEMALE = "女";*//*

    }
    */
/*目标字符串转枚举*//*

    public Target getTargetEnum(String target){
        if(target.equals("减脂"))
            return Enum.valueOf(Target.class, String.valueOf(HIIT));
        else if(target.equals("增肌"))
            return Enum.valueOf(Target.class, String.valueOf(MUSCLE));
        else
            return Enum.valueOf(Target.class, String.valueOf(BALANCE));
    }
    */
/*工作强度转枚举*//*

    public WorkStrength getWorkStrengthEnum(String strength){
        if(strength.equals("脑力劳动"))
            return Enum.valueOf(WorkStrength.class, String.valueOf(HEADWORK));
        else if(strength.equals("中度体力劳动"))
            return Enum.valueOf(WorkStrength.class, String.valueOf(MIDDLEWORK));
        else
            return Enum.valueOf(WorkStrength.class, String.valueOf(HIGHWORK));
    }


/*字符串转日期*/

/*    public Date stringToDate(String birthday){
        SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd");
        *//*     2017 - 10 - 9*//*

        try {
            return format.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

}

