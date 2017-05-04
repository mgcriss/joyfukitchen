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
import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.service.UserService;
import edu.ayd.joyfukitchen.util.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tangtang on 2017/4/26 19:56.
 */

public class RegistActivity extends BaseActivity {

    private EditText nickName_edt;
    private EditText regist_password_edt;
    private EditText regist_email_edt;
    private Button regist_next_btn;

    private User user;
    private String nickName;
    private String password;
    private String email;
    private UserService userService;
    private String login_url="";
    private Gson gson;

    private Integer json;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch(msg.what){
               case 0:
                   ToastUtil.show(RegistActivity.this,"用户已存在！！！");
                   break;
               case 1:
                   Intent intent = new Intent();
                   intent.setClass(RegistActivity.this,CheckCodeActivity.class);
                   RegistActivity.this.startActivity(intent);
                   break;
                default:
                    Log.i("出错","出现问题");
                    break;
                }
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_regist);
        init();


    }



    private void init() {
        nickName_edt=(EditText) findViewById(R.id.nickName_edt);
        regist_password_edt=(EditText) findViewById(R.id.regist_password_edt);
        regist_email_edt=(EditText) findViewById(R.id.regist_email_edt);
        regist_next_btn=(Button) findViewById(R.id.regist_next_btn);
        regist_next_btn.setOnClickListener(new NextClickListener());
        user=new User();
        userService=new UserService();
        gson=new Gson();

        }




    /**
     * 单击下一步按钮
     */
    private class NextClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            nickName=nickName_edt.getText().toString();
            password=regist_password_edt.getText().toString();
            email=regist_email_edt.getText().toString();

            user.setNickname(nickName);
            user.setUsername(email);
            String str_user=gson.toJson(user);
            regist(str_user, password);

        }

        private void regist(String users, String password) {
            login_url="http://www.chedles.xyz/joyfulkitchen/recipe/queryOneUser.do";
            Log.i("run", "regist: users = "+users+"psd = "+password);
            final OkHttpClient okHttpClient=new OkHttpClient();
            FormBody body=new FormBody.Builder()
                    .add("user", users)
                    .add("password", password)
                    .build();



            //创建一个请求对象
            final Request request=new Request.Builder()
                    .url(login_url)
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
                                    json=gson.fromJson(response.body().string(), Integer.TYPE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                handler.sendEmptyMessage(json);
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
