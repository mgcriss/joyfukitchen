package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import edu.ayd.joyfukitchen.util.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tangtang on 2017/4/26 20:28.
 * 输入验证码
 */

public class CheckCodeActivity extends BaseActivity {

    private Button getcode_btn;
    private String code_url="";
    private Integer json;
    private Gson gson;
    private Button regist_next_btn;
    private EditText emailcode_edt;
    private String checkCode;
    private String check_url="";
    private OkHttpClient okHttpClient;
    private int jsonTo;


    private Handler handlerGetCcode = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    ToastUtil.show(CheckCodeActivity.this,"获取验证码失败！！！");
                    break;
                case 1:
                    ToastUtil.show(CheckCodeActivity.this,"验证码已发送，请查看邮箱！！！");
                    break;
                default:
                    Log.i("出错","出现问题");
                    break;
            }
        }
    };


    private Handler handlerCheckCode = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    ToastUtil.show(CheckCodeActivity.this,"验证码输入失败！！！");
                    break;
                case 1:
                    ToastUtil.show(CheckCodeActivity.this,"注册成功！！！");
                    Intent intent = new Intent();
                    intent.setClass(CheckCodeActivity.this,LoginActivity.class);
                    CheckCodeActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_checkcode);
        init();
    }

    private void init() {
        getcode_btn =(Button) findViewById(R.id.getcode_btn);
        emailcode_edt =(EditText) findViewById(R.id.emailcode_edt);
        getcode_btn.setOnClickListener(new GetCodeClickListener());
        regist_next_btn =(Button) findViewById(R.id.regist_next_btn);
        regist_next_btn.setOnClickListener(new NextWorkClickListener());
        gson = new Gson();
        okHttpClient=new OkHttpClient();
    }


    private class GetCodeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            code_url="http://www.chedles.xyz/joyfulkitchen/recipe/sendVerificationCode.do";

            final Request request=new Request.Builder()
                    .url(code_url)
                    .build();

            //发送请求获取响应
            new Thread(){
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

                                   json=gson.fromJson(response.body().string(), Integer.TYPE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(json>0){
                                    handlerGetCcode.sendEmptyMessage(1);
                                }else{
                                    handlerGetCcode.sendEmptyMessage(0);
                                }
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }


    /**
     * 点击下一步，判断验证码是否输入正确，跳到登录页面
     */
    private class NextWorkClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            checkCode = emailcode_edt.getText().toString();
            check_url = "http://www.chedles.xyz/joyfulkitchen/recipe/judgmentVerificationCode.do";

            FormBody body=new FormBody.Builder()
                    .add("Verification", checkCode )
                    .build();

            //创建一个请求对象
            final Request request=new Request.Builder()
                    .url(check_url)
                    .post(body)
                    .build();
            //发送请求获取响应
            new Thread(){
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
                                    jsonTo = Integer.parseInt(response.body().string());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(jsonTo>0)
                                    handlerCheckCode.sendEmptyMessage(1);
                                else
                                    handlerCheckCode.sendEmptyMessage(0);
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }





}
